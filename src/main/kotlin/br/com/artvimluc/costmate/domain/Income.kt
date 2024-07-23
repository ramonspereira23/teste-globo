package br.com.artvimluc.costmate.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "INCOME")
class Income (
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "PLAN_MONTH_ID")
    var planMonthId: Long? = null,

    @Column(name = "DESCRIPTION")
    var description: String? = null,

    @Column(name = "INCOME_VALUE")
    var value: BigDecimal? = null,

    @Column(name = "DATE_CREATE")
    var dateCreate: LocalDateTime? = null,

    @Column(name = "DATE_UPDATE")
    var dateUpdate: LocalDateTime? = null
)