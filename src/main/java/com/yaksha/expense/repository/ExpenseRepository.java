package com.yaksha.expense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yaksha.expense.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = "Select e FROM Expense e "
            + "where (:keyword IS NULL "
            + "OR lower(e.description) like %:keyword%) "
            + "AND (:year IS NULL OR e.year = :year )"
            + "AND (:month IS NULL OR e.month = :month )")
    Page<Expense> findExpenseByDescYearAndMonth(@Param("keyword") String keyword,
                                               @Param("year") String year,
                                               @Param("month") String month,
                                               Pageable pageable);


    @Query(value = "Select SUM(e.amount) FROM Expense e "
            + "where (:keyword IS NULL "
            + "OR lower(e.description) like %:keyword%) "
            + "AND (:year IS NULL OR e.year = :year )"
            + "AND (:month IS NULL OR e.month = :month )")
    Integer getSumOfAmount(@Param("keyword") String keyword,
                           @Param("year") String year,
                           @Param("month") String month);

}
