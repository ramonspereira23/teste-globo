package br.com.artvimluc.costmate.domain

import br.com.artvimluc.costmate.enum.ExpenseStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "EXPENSE")
class Expense (
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "CREDIT_CARD_ID")
    var creditCardId: Long? = null,

    @Column(name = "PLAN_MONTH_ID")
    var planMonthId: Long? = null,

    @Column(name = "DESCRIPTION")
    var description: String? = null,

    @Column(name = "EXPENSE_VALUE")
    var value: BigDecimal? = null,

    @Column(name = "REFUND")
    var refund: Boolean? = null,

    @Column(name = "PERC_REFUND")
    var percentageRefund: Int? = null,

    @Column(name = "FIXED")
    var fixed: Boolean? = null,

    @Column(name = "INSTALLMENT")
    var installment: Int? = null,

    @Column(name = "TOTAL_INSTALLMENTS")
    var totalInstallments: Int? = null,

    @Column(name = "RECEIPT_FILE")
    var receiptFile: String? = null,

    @Column(name = "RECEIPT_FILE_UPLOADED")
    var receiptFileUploaded: Boolean? = null,

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS", length = 255)
    var status: ExpenseStatus? = null,

    @Column(name = "DUE_DAY")
    var dueDay: Int? = null,

    @Column(name = "DATE_CREATE")
    var dateCreate: LocalDateTime? = null,

    @Column(name = "DATE_UPDATE")
    var dateUpdate: LocalDateTime? = null,

    @Column(name = "TAGS")
    var tags: String? = null
) {
    override fun toString(): String {
        return "Expense(id=$id, creditCardId=$creditCardId, planMonthId=$planMonthId, description=$description, " +
                "value=$value, refund=$refund, percentageRefund=$percentageRefund, fixed=$fixed, " +
                "installment=$installment, totalInstallments=$totalInstallments, receiptFile=$receiptFile, " +
                "receiptFileUploaded=$receiptFileUploaded, status=$status, dueDay=$dueDay, dateCreate=$dateCreate, " +
                "dateUpdate=$dateUpdate)"
    }
}

