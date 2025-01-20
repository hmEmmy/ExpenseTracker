package me.emmy.project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Emmy
 * @project ExpenseTracker
 * @date 20/01/2025 - 21:41
 */
public class ExpenseTracker {
    private static final List<Transaction> transactions = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🌟 Welcome to ExpenseTracker made by Emmy 🌟");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Summary");
            System.out.println("3. Search Transactions");
            System.out.println("4. Export Transactions to CSV");
            System.out.println("5. Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addTransaction();
                case "2" -> viewSummary();
                case "3" -> searchTransactions();
                case "4" -> exportToCsv();
                case "5" -> {
                    System.out.println("Goodbye! Thanks for using ExpenseTracker.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTransaction() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = parseAmount(scanner.nextLine());
        if (amount < 0) return;

        System.out.print("Enter category (e.g., Food, Rent, Entertainment): ");
        String category = scanner.nextLine();

        System.out.print("Is this an income? (yes/no): ");
        boolean isIncome = scanner.nextLine().equalsIgnoreCase("yes");

        transactions.add(new Transaction(description, amount, category, isIncome));
        System.out.println("Transaction added successfully!");
    }

    private static void viewSummary() {
        double totalIncome = 0;
        double totalExpenses = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.isIncome()) {
                totalIncome += transaction.amount();
            } else {
                totalExpenses += transaction.amount();
                categoryTotals.merge(transaction.category(), transaction.amount(), Double::sum);
            }
        }

        System.out.println("\n📊 Summary:");
        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpenses);
        System.out.println("Net Savings: $" + (totalIncome - totalExpenses));

        System.out.println("\nExpenses by Category:");
        categoryTotals.forEach((category, total) -> System.out.println("- " + category + ": $" + total));
    }

    private static void searchTransactions() {
        System.out.print("Enter a keyword or category to search: ");
        String query = scanner.nextLine().toLowerCase();

        System.out.println("\n🔍 Search Results:");
        transactions.stream()
                .filter(transaction -> transaction.description().toLowerCase().contains(query) ||
                        transaction.category().toLowerCase().contains(query))
                .forEach(System.out::println);
    }

    private static void exportToCsv() {
        System.out.print("Enter file name (without extension): ");
        String fileName = scanner.nextLine();

        try (FileWriter writer = new FileWriter(fileName + ".csv")) {
            writer.write("Description,Amount,Category,Type\n");
            for (Transaction transaction : transactions) {
                writer.write(transaction.toCsv() + "\n");
            }
            System.out.println("Transactions exported to " + fileName + ".csv successfully!");
        } catch (IOException e) {
            System.out.println("Failed to export transactions: " + e.getMessage());
        }
    }

    private static double parseAmount(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Transaction canceled.");
            return -1;
        }
    }

    public record Transaction(String description, double amount, String category, boolean isIncome) {
        public String toCsv() {
            return String.join(",", description, String.valueOf(amount), category, isIncome ? "Income" : "Expense");
        }

        @Override
        public String toString() {
            return (isIncome ? "[Income] " : "[Expense] ") + description + " - $" + amount + " (" + category + ")";
        }
    }
}