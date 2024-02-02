package com.yaksha.expense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yaksha.expense.entity.Expense;
import com.yaksha.expense.repository.ExpenseRepository;

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

    public Page<Expense> getExpenses(String desc, String month, String year, Pageable pageable) {
        desc = desc == null || desc.isEmpty() ? null : desc;
        month = month == null || month.isEmpty() ? null : month;
        year = year == null || year.isEmpty() ? null : year;
        return expenseRepository.findExpenseByDescYearAndMonth(desc, year, month, pageable);
    }

    public boolean deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
        return true;
    }

    public Integer totalExpense(String desc, String month, String year) {
        desc = desc == null || desc.isEmpty() ? null : desc;
        month = month == null || month.isEmpty() ? null : month;
        year = year == null || year.isEmpty() ? null : year;
        return expenseRepository.getSumOfAmount(desc, year, month);

    }

}
