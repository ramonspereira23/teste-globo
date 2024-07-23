package br.com.artvimluc.costmate.service

import br.com.artvimluc.costmate.domain.Expense
import br.com.artvimluc.costmate.exception.BusinessException
import br.com.artvimluc.costmate.exception.NotFoundException
import br.com.artvimluc.costmate.repository.ExpenseRepository
import br.com.artvimluc.costmate.util.MessageLocator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.io.InputStream
import java.math.BigDecimal

private const val NOT_FOUND = "expense.not.found"
private const val EXPENSE_FAIL_CREATE_VALUE = "expense.fail.create.value"

@Service
class ExpenseService

    @Autowired
    constructor (
        private val expenseRepository: ExpenseRepository,
        private val messageLocator: MessageLocator,
        private val creditCardService: CreditCardService,
        private val planMonthService: PlanMonthService,
    ) {

    private val logger = LoggerFactory.getLogger(javaClass)
    fun create(expense: Expense): Expense {
        return expenseRepository.save(expense)
    }

    fun update(id: Long, expense: Expense): Expense {
        val expenseOnBase = find(id)

        expense.id = expenseOnBase.id

        return expenseRepository.save(expense)
    }

    fun delete(id: Long) {
        val expense = find(id)

        expenseRepository.delete(expense)
    }

    fun find(id: Long) : Expense {
        val expense = expenseRepository.findById(id)

        return expense.orElseThrow { throw NotFoundException(
            String.format(
                messageLocator.getMessage(NOT_FOUND),
                id
            )) }
    }

    fun find() : List<Expense> {
        return expenseRepository.findAll()
    }

    fun import() {
        val inputStream = File("src/main/resources/planilha_import.csv").inputStream()

        readCsv(inputStream).forEach { e -> create(e) }
    }

    fun readCsv(inputStream: InputStream): List<Expense> {
        val reader = inputStream.bufferedReader()
        val header = reader.readLine()

        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val list = it.split(',', ignoreCase = false, limit = 11);
                val expense = Expense()

                logger.debug(it)

                expense.description = list[0]

                try {
                    expense.value = BigDecimal(list[1])
                } catch (e: NumberFormatException) {
                    throw BusinessException(String.format(messageLocator.getMessage(EXPENSE_FAIL_CREATE_VALUE), expense.description, e))
                }

                expense.creditCardId = if (list[2].isNotEmpty()) creditCardService.findByName(list[2]).id else null
                expense.tags = list[3]
                expense.fixed = list[4].toBoolean()
                expense.refund = list[5].toBoolean()
                expense.percentageRefund = list[6].toIntOrNull()
                expense.installment = list[7].toIntOrNull()
                expense.totalInstallments = list[8].toIntOrNull()
                expense.planMonthId = planMonthService.find(list[10].toIntOrNull(), list[9].toIntOrNull()).id
                expense.receiptFileUploaded = false

                expense
            }.toList()
    }
}
