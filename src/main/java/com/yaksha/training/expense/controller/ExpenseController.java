package com.yaksha.training.expense.controller;

import com.yaksha.training.expense.entity.Expense;
import com.yaksha.training.expense.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("expense", new Expense());
        } else {
            model.addAttribute("expense", expenseService.getExpenseById(id));
        }
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(@Valid Expense expense, BindingResult result) {
        if (result.hasErrors()) return "form";
        expenseService.submitExpense(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/expenses")
    public String getExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getExpenses());
        return "expenselist";
    }
}
