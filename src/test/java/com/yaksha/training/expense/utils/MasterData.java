package com.yaksha.training.expense.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yaksha.expense.entity.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MasterData {

    public static Expense getExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setDescription(randomStringWithSize(10));
        expense.setAmount(100);
        expense.setMonth(12);
        expense.setYear(2023);
        return expense;
    }

    public static List<Expense> getExpenseList(int size) {
        Long id = 0L;
        List<Expense> expenses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Expense expense = new Expense();
            expense.setId(1L);
            expense.setDescription(randomStringWithSize(10));
            expense.setAmount(100);
            expense.setMonth(12);
            expense.setYear(2023);
            expenses.add(expense);
        }
        return expenses;
    }


    private static Random rnd = new Random();

    public static String randomStringWithSize(int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        String s = "";
        for (int i = 0; i < size; i++) {
            s = s + (String.valueOf(alphabet.charAt(rnd.nextInt(alphabet.length()))));
        }
        return s;
    }

    public static boolean randomBoolean() {
        String alphabet = "1234567890";
        Random rnd = new Random();
        return rnd.nextInt(alphabet.length()) % 2 == 0;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            final String jsonContent = mapper.writeValueAsString(obj);

            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
