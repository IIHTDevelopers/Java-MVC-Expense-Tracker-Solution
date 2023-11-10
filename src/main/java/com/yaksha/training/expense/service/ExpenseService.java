package com.yaksha.training.expense.service;

import com.yaksha.training.expense.entity.Expense;
import com.yaksha.training.expense.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Expense expense, Expense existing) {
        expense.setId(existing.getId());
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).get();
    }

    public Expense submitExpense(Expense expense) {
        if (expense.getId() == null) {
            return addExpense(expense);
        } else {
            Optional<Expense> existingExpense = expenseRepository.findById(expense.getId());
            return updateExpense(expense, existingExpense.get());
        }
    }

}
