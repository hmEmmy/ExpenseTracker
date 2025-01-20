package me.emmy.project;

import me.emmy.project.manager.TransactionManager;

import java.util.*;

/**
 * @author Emmy
 * @project ExpenseTracker
 * @date 20/01/2025 - 21:41
 */
public class ExpenseTracker {
    protected static TransactionManager transactionManager;

    /**
     * Main method to run the ExpenseTracker program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        transactionManager = new TransactionManager();

        System.out.println("🌟 ExpenseTracker made by Emmy 🌟");

        while (true) {
            Arrays.asList(
                    "\nChoose an option:",
                    "1. Add Transaction",
                    "2. View Summary",
                    "3. Search Transactions",
                    "4. Export Transactions to CSV",
                    "5. Exit",
                    "Your choice: "
            ).forEach(System.out::println);

            String choice = transactionManager.scanner.nextLine();

            switch (choice) {
                case "1" -> transactionManager.addTransaction();
                case "2" -> transactionManager.viewSummary();
                case "3" -> transactionManager.searchTransactions();
                case "4" -> transactionManager.exportToCsv();
                case "5" -> {
                    System.out.println("Goodbye! Thanks for using ExpenseTracker.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}