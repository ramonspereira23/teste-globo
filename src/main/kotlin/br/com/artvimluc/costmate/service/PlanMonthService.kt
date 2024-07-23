package br.com.artvimluc.costmate.service

import br.com.artvimluc.costmate.domain.Expense
import br.com.artvimluc.costmate.domain.Income
import br.com.artvimluc.costmate.domain.PlanMonth
import br.com.artvimluc.costmate.dto.PlanMonthDTO
import br.com.artvimluc.costmate.exception.AllReadyExistsException
import br.com.artvimluc.costmate.exception.NotFoundException
import br.com.artvimluc.costmate.repository.ExpenseRepository
import br.com.artvimluc.costmate.repository.IncomeRepository
import br.com.artvimluc.costmate.repository.PlanMonthRepository
import br.com.artvimluc.costmate.util.MessageLocator
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

private const val ALREADY_EXISTS = "plan.month.already.exists"

private const val NOT_FOUND = "plan.month.not.found"

@Service
class PlanMonthService

    @Autowired
    constructor (
        private val planMonthRepository: PlanMonthRepository,
        private val expenseRepository: ExpenseRepository,
        private val incomeRepository: IncomeRepository,
        private val messageLocator: MessageLocator,
    ) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun create(planMonth: PlanMonth): PlanMonth {
        if (exists(planMonth)) {
           throw AllReadyExistsException(String.format(messageLocator.getMessage(ALREADY_EXISTS), planMonth.referenceYear, planMonth.referenceMonth))
        }

        return planMonthRepository.save(planMonth)
    }

    fun createNextPlanMonth(): PlanMonth {
        val lastPlanMonth = planMonthRepository.findLastPlanMonth()

        val (month, year) = getNextReferenceMonthYear(lastPlanMonth)

        var nextPlanMonth = PlanMonth()
        nextPlanMonth.referenceMonth = month
        nextPlanMonth.referenceYear = year

        nextPlanMonth = create(nextPlanMonth)

        val listExpense = expenseRepository.findByPlanMonthId(lastPlanMonth.id)

        // https://stackoverflow.com/questions/34642254/what-java-8-stream-collect-equivalents-are-available-in-the-standard-kotlin-libr
        if (listExpense != null) {
            val newExpense = listExpense
                .filter { it != null && (it.fixed!! ||
                        (it.installment != null && it.totalInstallments != null
                                && it.installment!! <= it.totalInstallments!!)) }
                .map {
                    val newExpense = Expense()

                    if (it != null) {
                        newExpense.planMonthId = nextPlanMonth.id
                        newExpense.creditCardId = it.creditCardId
                        newExpense.description = it.description
                        newExpense.value = it.value
                        newExpense.totalInstallments = it.totalInstallments
                        newExpense.receiptFileUploaded = it.receiptFileUploaded
                        newExpense.receiptFile = it.receiptFile
                        newExpense.refund = it.refund
                        newExpense.percentageRefund = it.percentageRefund
                        newExpense.fixed = it.fixed
                        newExpense.status = it.status
                        newExpense.dueDay = it.dueDay

                        if (it.installment != null)
                            newExpense.installment = it.installment!! + 1

                    }

                    newExpense
                }

            newExpense.forEach { expenseRepository.save(it) }
        }

        return nextPlanMonth
    }

    fun getNextReferenceMonthYear(lastPlanMonth: PlanMonth): IntArray {
        if (lastPlanMonth.referenceMonth == 12) {
            return intArrayOf(1, (lastPlanMonth.referenceYear!! + 1))
        }

        return intArrayOf((lastPlanMonth.referenceMonth!! + 1), lastPlanMonth.referenceYear!!)
    }

    fun update(referenceYear: Int, referenceMonth: Int, planMonth: PlanMonth): PlanMonth {
        val planMonthOnBase = find(referenceYear, referenceMonth)

        planMonth.id = planMonthOnBase.id

        return planMonthRepository.save(planMonth)
    }

    fun delete(referenceYear: Int?, referenceMonth: Int?) {
        planMonthRepository.delete(find(referenceYear, referenceMonth))
    }

    fun exists(planMonth: PlanMonth): Boolean {
        return planMonthRepository.existsByReferenceYearAndReferenceMonth(planMonth.referenceYear, planMonth.referenceMonth)
    }

    fun find(referenceYear: Int?, referenceMonth: Int?) : PlanMonth {
        val planMonth: PlanMonth? = planMonthRepository.findByReferenceYearAndReferenceMonth(referenceYear, referenceMonth)

        return planMonth?: throw NotFoundException(String.format(messageLocator.getMessage(NOT_FOUND), referenceYear, referenceMonth))
    }

    fun findCurrent() : PlanMonth {
        return find(LocalDate.now().year, LocalDate.now().month.value)
    }

    fun findDTO(referenceYear: Int?, referenceMonth: Int?) : PlanMonthDTO {
        return planMonthDTO(find(referenceYear, referenceMonth))
    }

    private fun planMonthDTO(planMonth: PlanMonth): PlanMonthDTO {
        val mapper = ModelMapper()
        val dto: PlanMonthDTO = mapper.map(planMonth, PlanMonthDTO::class.java)

        dto.totalExpenses = expenseRepository.sumValueByPlanMonthId(planMonth.id).orElse(BigDecimal.ZERO)
        dto.listExpense = expenseRepository.findByPlanMonthId(planMonth.id)
        dto.listIncome = incomeRepository.findByPlanMonthId(planMonth.id)

        return dto
    }
}
