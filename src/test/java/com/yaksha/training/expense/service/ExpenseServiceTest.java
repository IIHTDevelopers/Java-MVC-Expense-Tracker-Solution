package com.yaksha.training.expense.service;

import com.yaksha.training.expense.entity.Expense;
import com.yaksha.training.expense.repository.ExpenseRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.yaksha.training.expense.utils.MasterData.asJsonString;
import static com.yaksha.training.expense.utils.MasterData.getExpense;
import static com.yaksha.training.expense.utils.MasterData.getExpenseList;
import static com.yaksha.training.expense.utils.TestUtils.businessTestFile;
import static com.yaksha.training.expense.utils.TestUtils.currentTest;
import static com.yaksha.training.expense.utils.TestUtils.testReport;
import static com.yaksha.training.expense.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testAddExpense() throws Exception {
        Expense actual = getExpense();
        when(expenseRepository.save(actual)).thenReturn(actual);
        Expense expected = expenseService.addExpense(actual);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testUpdateExpense() throws Exception {
        Expense actual = getExpense();
        Expense existing = getExpense();
        when(expenseRepository.save(actual)).thenReturn(actual);
        Expense expected = expenseService.updateExpense(actual, existing);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testGetExpenses() throws Exception {
        List<Expense> actual = getExpenseList(5);
        when(expenseRepository.findAll()).thenReturn(actual);
        List<Expense> expected = expenseService.getExpenses();
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testGetExpenseById() throws Exception {
        Expense actual = getExpense();
        when(expenseRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        Expense expected = expenseService.getExpenseById(actual.getId());
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testSubmitExpenseIfIdIsNull() throws Exception {
        Expense actual = getExpense();
        actual.setId(null);
        when(expenseRepository.save(actual)).thenReturn(actual);
        Expense expected = expenseService.submitExpense(actual);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testSubmitExpenseIfIdIsNotNull() throws Exception {
        Expense actual = getExpense();
        Expense existing = getExpense();
        when(expenseRepository.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(expenseRepository.save(actual)).thenReturn(actual);
        Expense expected = expenseService.submitExpense(actual);
        yakshaAssert(currentTest(),
                (asJsonString(expected).equals(asJsonString(actual))
                        ? "true"
                        : "false"),
                businessTestFile);
    }
}
