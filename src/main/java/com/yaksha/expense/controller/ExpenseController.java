package com.yaksha.expense.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yaksha.expense.entity.Expense;
import com.yaksha.expense.service.ExpenseService;

import java.util.List;

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

    @RequestMapping(value = {"/expenses", "/search"})
    public String getExpenses(@RequestParam(value = "theSearchName", required = false) String theSearchName,
                              @RequestParam(value = "searchMonth", required = false) String searchMonth,
                              @RequestParam(value = "theSearchYear", required = false) String theSearchYear,
                              @PageableDefault(size = 5) Pageable pageable,
                              Model model) {
        Page<Expense> expenses = expenseService.getExpenses(theSearchName, searchMonth, theSearchYear, pageable);
        model.addAttribute("expenses", expenses.getContent());
        model.addAttribute("theSearchName", theSearchName != null ? theSearchName : "");
        model.addAttribute("searchMonth", searchMonth != null ? searchMonth : "");
        model.addAttribute("theSearchYear", theSearchYear != null ? theSearchYear : "");
        model.addAttribute("totalExpense", expenseService.totalExpense(theSearchName, searchMonth, theSearchYear));
        model.addAttribute("months", List.of("", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        model.addAttribute("totalPage", expenses.getTotalPages());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("sortBy", pageable.getSort().get().count() != 0 ?
                pageable.getSort().get().findFirst().get().getProperty() + "," + pageable.getSort().get().findFirst().get().getDirection() : "");
        return "expenselist";
    }

    @GetMapping("/delete/{id}")
    public String showFormForDelete(@PathVariable("id") Long id, Model theModel) {
        expenseService.deleteExpenseById(id);
        return "redirect:/expenses";
    }

}
