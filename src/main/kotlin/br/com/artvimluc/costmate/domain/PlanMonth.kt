package br.com.artvimluc.costmate.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "PLAN_MONTH")
class PlanMonth (

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "REFERENCE_MONTH")
    var referenceMonth: Int? = null,

    @Column(name = "REFERENCE_YEAR")
    var referenceYear: Int? = null,

    @Column(name = "TOTAL_EXPENSES")
    var totalExpenses: BigDecimal? = null,

    @Column(name = "TOTAL_INCOME")
    var totalIncome: BigDecimal? = null,

    @Column(name = "GOAL")
    var goal: BigDecimal? = null,

    @Column(name = "SURPLUS")
    var surplus: BigDecimal? = null,

    @Column(name = "DATE_CREATE")
    var dateCreate: LocalDateTime? = null,

    @Column(name = "DATE_UPDATE")
    var dateUpdate: LocalDateTime? = null

)