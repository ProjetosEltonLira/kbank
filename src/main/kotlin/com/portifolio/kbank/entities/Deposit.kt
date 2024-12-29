package com.portifolio.kbank.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tb_deposits")
data class Deposit(

    @Id
    @Column(name = "deposit_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val depositId: UUID?,

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    val wallet: Wallet,

    @Column(name = "deposit_value")
    val depositValue: BigDecimal,

    @Column(name = "deposit_date_time")
    val depositDateTime: LocalDateTime,

    @Column(name = "ip_adress")
    val ipAddress: String
)

