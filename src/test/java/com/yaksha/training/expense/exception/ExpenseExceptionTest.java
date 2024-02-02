package com.yaksha.training.expense.exception;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import com.yaksha.expense.controller.ExpenseController;
import com.yaksha.expense.entity.Expense;

import static com.yaksha.training.expense.utils.MasterData.getExpense;
import static com.yaksha.training.expense.utils.TestUtils.currentTest;
import static com.yaksha.training.expense.utils.TestUtils.exceptionTestFile;
import static com.yaksha.training.expense.utils.TestUtils.testReport;
import static com.yaksha.training.expense.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ExpenseExceptionTest {

    @InjectMocks
    private ExpenseController expenseController;

    private MockMvc mockMvc;

    BindingResult bindingResult = mock(BindingResult.class);


    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testExceptionSubmitFormDescriptionNull() throws Exception {
        Expense expense = getExpense();
        expense.setDescription(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/handleSubmit")
                .flashAttr("expense", expense)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("form")), exceptionTestFile);
    }

    @Test
    public void testExceptionSubmitFormAmountNull() throws Exception {
        Expense expense = getExpense();
        expense.setAmount(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/handleSubmit")
                .flashAttr("expense", expense)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("form")), exceptionTestFile);
    }

    @Test
    public void testExceptionSubmitFormMonthNull() throws Exception {
        Expense expense = getExpense();
        expense.setMonth(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/handleSubmit")
                .flashAttr("expense", expense)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("form")), exceptionTestFile);
    }

    @Test
    public void testExceptionSubmitFormYearNull() throws Exception {
        Expense expense = getExpense();
        expense.setYear(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        MvcResult result = this.mockMvc.perform(post("/handleSubmit")
                .flashAttr("expense", expense)).andReturn();
        yakshaAssert(currentTest(), (result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("form")), exceptionTestFile);
    }

}
