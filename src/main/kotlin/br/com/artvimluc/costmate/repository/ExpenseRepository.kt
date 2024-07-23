package br.com.artvimluc.costmate.repository

import br.com.artvimluc.costmate.domain.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
interface ExpenseRepository : JpaRepository<Expense, Long> {
    fun findByPlanMonthId(planMonthId: Long?): List<Expense?>?

    @Query("SELECT sum(e.value) from Expense e where e.planMonthId = :planMonthId")
    fun sumValueByPlanMonthId(planMonthId: Long?): Optional<BigDecimal>

}