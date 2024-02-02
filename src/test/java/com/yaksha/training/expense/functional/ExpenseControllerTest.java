package com.yaksha.training.expense.functional;


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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yaksha.expense.controller.ExpenseController;
import com.yaksha.expense.entity.Expense;
import com.yaksha.expense.service.ExpenseService;

import java.util.List;

import static com.yaksha.training.expense.utils.MasterData.getExpense;
import static com.yaksha.training.expense.utils.MasterData.getExpenseList;
import static com.yaksha.training.expense.utils.MasterData.randomStringWithSize;
import static com.yaksha.training.expense.utils.TestUtils.businessTestFile;
import static com.yaksha.training.expense.utils.TestUtils.currentTest;
import static com.yaksha.training.expense.utils.TestUtils.testReport;
import static com.yaksha.training.expense.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(expenseController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @After
    public void afterAll() {
        testReport();
    }

    @Test
    public void testControllerGetForm() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("form"), businessTestFile);
    }

    @Test
    public void testControllerGetFormById() throws Exception {
        Expense expense = getExpense();
        when(expenseService.getExpenseById(expense.getId())).thenReturn(expense);
        MvcResult result = this.mockMvc.perform(get("/")
                .param("id", expense.getId().toString())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("form"), businessTestFile);
    }

    @Test
    public void testControllerGetExpenses() throws Exception {
        List<Expense> expenses = getExpenseList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Expense> expensePage = new PageImpl<>(expenses);
        when(expenseService.getExpenses(null, null, null, pageable)).thenReturn(expensePage);
        MvcResult result = this.mockMvc.perform(get("/expenses")).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("expenselist"), businessTestFile);

    }

    @Test
    public void testControllerSubmitForm() throws Exception {
        Expense expense = getExpense();
        MvcResult result = this.mockMvc.perform(post("/handleSubmit")
                .flashAttr("expense", expense)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/expenses"), businessTestFile);

    }


    @Test
    public void testControllerGetExpensesBySearch() throws Exception {
        String keyword = randomStringWithSize(1);
        List<Expense> expenses = getExpenseList(5);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Expense> expensePage = new PageImpl<>(expenses);
        when(expenseService.getExpenses(keyword, keyword, keyword, pageable)).thenReturn(expensePage);
        MvcResult result = this.mockMvc.perform(get("/search")
                .param("theSearchName", keyword)
                .param("theSearchYear", keyword)
                .param("searchMonth", keyword)).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("expenselist"), businessTestFile);

    }

    @Test
    public void testControllerShowFormForDelete() throws Exception {
        Expense expense = getExpense();
        MvcResult result = this.mockMvc.perform(get("/delete/" + expense.getId())).andReturn();
        yakshaAssert(currentTest(), result.getModelAndView() != null
                && result.getModelAndView().getViewName() != null
                && result.getModelAndView().getViewName().contentEquals("redirect:/expenses"), businessTestFile);
    }

}
