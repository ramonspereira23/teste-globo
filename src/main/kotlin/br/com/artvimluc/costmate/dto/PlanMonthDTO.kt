package br.com.artvimluc.costmate.dto

import br.com.artvimluc.costmate.domain.Expense
import br.com.artvimluc.costmate.domain.Income
import java.math.BigDecimal

class PlanMonthDTO (
    var id: Long? = null,
    var referenceMonth: Int? = null,
    var referenceYear: Int? = null,
    var totalExpenses: BigDecimal? = null,
    var totalIncome: BigDecimal? = null,
    var goal: BigDecimal? = null,
    var surplus: BigDecimal? = null,
    var listIncome: List<Income?>? = null,
    var listExpense: List<Expense?>? = null
)