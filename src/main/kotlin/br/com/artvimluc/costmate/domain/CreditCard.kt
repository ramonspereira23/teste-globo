package br.com.artvimluc.costmate.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "CREDIT_CARD")
class CreditCard (
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "NAME")
    var name: String? = null,

    @Column(name = "DESCRIPTION")
    var description: String? = null,

    @Column(name = "DAY_DUE_DATE")
    var dayDueDate: Int? = null,

    @Column(name = "DAYS_TO_CLOSE")
    var daysToClose: Int? = null,

    @Column(name = "DATE_CREATE")
    var dateCreate: LocalDateTime? = null,

    @Column(name = "DATE_UPDATE")
    var dateUpdate: LocalDateTime? = null
)