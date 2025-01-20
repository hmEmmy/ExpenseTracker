package me.emmy.project.records;

/**
 * Represents a transaction with a description, amount, category, and whether it is an income or expense.
 *
 * @author Emmy
 * @project ExpenseTracker
 * @date 20/01/2025 - 21:48
 *
 * @param description the description of the transaction
 * @param amount the amount of the transaction
 * @param category the category of the transaction
 * @param isIncome whether the transaction is an income or expense
 */
public record TransactionRecord(String description, double amount, String category, boolean isIncome) {
    public String toCsv() {
        return String.join(",", this.description, String.valueOf(this.amount), this.category, this.isIncome ? "Income" : "Expense");
    }

    @Override
    public String toString() {
        return (this.isIncome ? "[Income] " : "[Expense] ") + this.description + " - $" + this.amount + " (" + this.category + ")";
    }
}