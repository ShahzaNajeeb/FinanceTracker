package com.example.finance_tracker.controller;

import com.example.finance_tracker.exception.InvalidExpenseException;
import com.example.finance_tracker.model.Category;
import com.example.finance_tracker.model.Expense;
import com.example.finance_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(
            @RequestParam(required = false) Category category) {
        List<Expense> expenses = (category == null)
                ? expenseService.getAllExpenses()
                : expenseService.getExpensesByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense savedExpense = expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<Category, Double>> getExpenseSummary() {
        Map<Category, Double> summary = expenseService.getExpenseSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Finance Tracker API is running");
    }

    @ExceptionHandler(InvalidExpenseException.class)
    public ResponseEntity<String> handleInvalidExpense(InvalidExpenseException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}