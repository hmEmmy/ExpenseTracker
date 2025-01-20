package me.emmy.project.manager;

import me.emmy.project.records.TransactionRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Emmy
 * @project ExpenseTracker
 * @date 20/01/2025 - 21:49
 */
public class TransactionManager {
    public final List<TransactionRecord> transactions;
    public final Scanner scanner;
    
    public TransactionManager() {
        this.transactions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addTransaction() {
        System.out.print("Enter description: ");
        String description = this.scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = parseAmount(this.scanner.nextLine());
        if (amount < 0) return;

        System.out.print("Enter category (e.g., Food, Rent, Entertainment): ");
        String category = this.scanner.nextLine();

        System.out.print("Is this an income? (yes/no): ");
        boolean isIncome = this.scanner.nextLine().equalsIgnoreCase("yes");

        this.transactions.add(new TransactionRecord(description, amount, category, isIncome));
        System.out.println("Transaction added successfully!");
    }

    public void viewSummary() {
        double totalIncome = 0;
        double totalExpenses = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (TransactionRecord transactionRecord : this.transactions) {
            if (transactionRecord.isIncome()) {
                totalIncome += transactionRecord.amount();
            } else {
                totalExpenses += transactionRecord.amount();
                categoryTotals.merge(transactionRecord.category(), transactionRecord.amount(), Double::sum);
            }
        }

        Arrays.asList(
                "\n📊 Summary:",
                "Total Income: $" + totalIncome,
                "Total Expenses: $" + totalExpenses,
                "Net Savings: $" + (totalIncome - totalExpenses),
                "\nExpenses by Category:"
        ).forEach(System.out::println);

        categoryTotals.forEach((category, total) -> System.out.println("- " + category + ": $" + total));
    }

    public void searchTransactions() {
        System.out.print("Enter a keyword or category to search: ");
        String query = this.scanner.nextLine().toLowerCase();

        System.out.println("\n🔍 Search Results:");
        this.transactions.stream().filter(transactionRecord -> transactionRecord.description().toLowerCase().contains(query)
                        || transactionRecord.category().toLowerCase().contains(query)).forEach(System.out::println);
    }

    public void exportToCsv() {
        System.out.print("Enter file name (without extension): ");
        String fileName = this.scanner.nextLine();

        try (FileWriter writer = new FileWriter(fileName + ".csv")) {
            writer.write("Description,Amount,Category,Type\n");
            for (TransactionRecord transactionRecord : this.transactions) {
                writer.write(transactionRecord.toCsv() + "\n");
            }
            System.out.println("Transactions exported to " + fileName + ".csv successfully!");
        } catch (IOException e) {
            System.out.println("Failed to export transactions: " + e.getMessage());
        }
    }

    /**
     * Parses the input string to a double value.
     *
     * @param input the input string to parse
     * @return the parsed double value, or -1 if the input is invalid
     */
    public double parseAmount(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Transaction canceled.");
            return -1;
        }
    }
}