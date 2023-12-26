import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Expense {
    String description;
    double amount;
    String category;

    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Amount: $" + amount + ", Category: " + category;
    }
}

class ExpenseTracker {
    private ArrayList<Expense> expenses;
    private Scanner scanner;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addExpense() {
        System.out.print("Enter expense description: ");
        String description = scanner.nextLine();

        double amount = 0;
        boolean validAmount = false;
        while (!validAmount) {
            try {
                System.out.print("Enter expense amount: $");
                amount = Double.parseDouble(scanner.nextLine());
                validAmount = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a numeric value.");
            }
        }

        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();

        Expense expense = new Expense(description, amount, category);
        expenses.add(expense);

        System.out.println("Expense added successfully!");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("Expense List:");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    public void viewExpenseSummaries() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            double totalExpenses = 0;
            System.out.println("Expense Summaries:");

            for (Expense expense : expenses) {
                totalExpenses += expense.amount;
            }

            System.out.println("Total Expenses: $" + totalExpenses);
        }
    }

    public void saveExpensesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("expenses.dat"))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    public void loadExpensesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("expenses.dat"))) {
            expenses = (ArrayList<Expense>) ois.readObject();
            System.out.println("Expenses loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        int choice;

        do {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Expense Summaries");
            System.out.println("4. Save Expenses to File");
            System.out.println("5. Load Expenses from File");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(expenseTracker.scanner.nextLine());

                switch (choice) {
                    case 1:
                        expenseTracker.addExpense();
                        break;
                    case 2:
                        expenseTracker.viewExpenses();
                        break;
                    case 3:
                        expenseTracker.viewExpenseSummaries();
                        break;
                    case 4:
                        expenseTracker.saveExpensesToFile();
                        break;
                    case 5:
                        expenseTracker.loadExpensesFromFile();
                        break;
                    case 0:
                        System.out.println("Exiting Expense Tracker. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }

        } while (choice != 0);
    }
}
