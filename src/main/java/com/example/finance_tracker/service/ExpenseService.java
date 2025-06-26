package com.example.finance_tracker.service;

import com.example.finance_tracker.model.Category;
import com.example.finance_tracker.model.Expense;
import com.example.finance_tracker.exception.InvalidExpenseException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {


    private final List<Expense> expenses = new ArrayList<>();
    private Long idCounter = 1L;


    public List<Expense> getAllExpenses() {
        return expenses.stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .collect(Collectors.toList());
    }


    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory() == category)
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .collect(Collectors.toList());
    }


    public Expense addExpense(Expense expense) {
        validateExpense(expense);
        expense.setId(idCounter++);
        expense.setDate(LocalDate.now());
        expenses.add(expense);
        return expense;
    }


    public Map<Category, Double> getExpenseSummary() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }


    private void validateExpense(Expense expense) {
        if (expense.getDescription() == null || expense.getDescription().trim().isEmpty()) {
            throw new InvalidExpenseException("Expense description cannot be empty");
        }
        if (expense.getAmount() == null || expense.getAmount() <= 0) {
            throw new InvalidExpenseException("Expense amount must be positive");
        }
        if (expense.getCategory() == null) {
            throw new InvalidExpenseException("Expense category is required");
        }
    }
}
