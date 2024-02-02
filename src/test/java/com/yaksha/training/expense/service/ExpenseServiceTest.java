package com.yaksha.training.expense.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.yaksha.expense.entity.Expense;
import com.yaksha.expense.repository.ExpenseRepository;
import com.yaksha.expense.service.ExpenseService;

import java.util.List;
import java.util.Optional;

import static com.yaksha.training.expense.utils.MasterData.asJsonString;
import static com.yaksha.training.expense.utils.MasterData.getExpense;
import static com.yaksha.training.expense.utils.MasterData.getExpenseList;
import static com.yaksha.training.expense.utils.MasterData.randomStringWithSize;
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

    @Test
    public void testGetExpensesWithNulKeys() throws Exception {
        List<Expense> expenses = getExpenseList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Expense> expected = new PageImpl<>(expenses);
        when(expenseRepository.findExpenseByDescYearAndMonth(null, null, null, pageable)).thenReturn(expected);
        Page<Expense> actual = expenseService.getExpenses(null, null, null, pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual.getContent()))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testGetGradesWithKeys() throws Exception {
        String keyword = randomStringWithSize(1);
        List<Expense> expenses = getExpenseList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Expense> expected = new PageImpl<>(expenses);
        when(expenseRepository.findExpenseByDescYearAndMonth(keyword, keyword, keyword, pageable)).thenReturn(expected);
        Page<Expense> actual = expenseService.getExpenses(keyword, keyword, keyword, pageable);
        yakshaAssert(currentTest(),
                (asJsonString(expected.getContent()).equals(asJsonString(actual.getContent()))
                        ? "true"
                        : "false"),
                businessTestFile);
    }

    @Test
    public void testDeleteExpense() throws Exception {
        Expense actual = getExpense();
        boolean expected = expenseService.deleteExpenseById(actual.getId());
        yakshaAssert(currentTest(), expected ? true : false, businessTestFile);
    }

    @Test
    public void testTotalExpense() throws Exception {
        when(expenseRepository.getSumOfAmount(null, null, null)).thenReturn(10);
        Integer expected = expenseService.totalExpense(null, null, null);
        yakshaAssert(currentTest(), expected == 10 ? true : false, businessTestFile);
    }


}
