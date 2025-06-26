package com.example.finance_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    private Long id;
    private String description;
    private Double amount;
    private Category category;
    private LocalDate date;
}