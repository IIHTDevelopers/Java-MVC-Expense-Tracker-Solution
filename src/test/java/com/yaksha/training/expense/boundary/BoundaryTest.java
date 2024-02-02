package com.yaksha.training.expense.boundary;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.yaksha.expense.entity.Expense;

import java.util.Set;

import static com.yaksha.training.expense.utils.MasterData.getExpense;
import static com.yaksha.training.expense.utils.MasterData.randomStringWithSize;
import static com.yaksha.training.expense.utils.TestUtils.boundaryTestFile;
import static com.yaksha.training.expense.utils.TestUtils.currentTest;
import static com.yaksha.training.expense.utils.TestUtils.testReport;
import static com.yaksha.training.expense.utils.TestUtils.yakshaAssert;

@ExtendWith(SpringExtension.class)
public class BoundaryTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testDescriptionNotBlank() throws Exception {
        Expense expense = getExpense();
        expense.setDescription("");
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testDescriptionNotNull() throws Exception {
        Expense expense = getExpense();
        expense.setDescription(null);
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testDescriptionMinThree() throws Exception {
        Expense expense = getExpense();
        expense.setDescription(randomStringWithSize(2));
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testDescriptionMaxFifty() throws Exception {
        Expense expense = getExpense();
        expense.setDescription(randomStringWithSize(51));
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testAmountNotNull() throws Exception {
        Expense expense = getExpense();
        expense.setAmount(null);
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testMonthNotNull() throws Exception {
        Expense expense = getExpense();
        expense.setMonth(null);
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testMonthLessThanOne() throws Exception {
        Expense expense = getExpense();
        expense.setMonth(0);
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testMonthMoreThanTwelve() throws Exception {
        Expense expense = getExpense();
        expense.setMonth(13);
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

    @Test
    public void testYearNotNull() throws Exception {
        Expense expense = getExpense();
        expense.setYear(null);
        Set<ConstraintViolation<Expense>> violations = validator.validate(expense);
        yakshaAssert(currentTest(), !violations.isEmpty() ? true : false, boundaryTestFile);
    }

}
