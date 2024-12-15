package com.protifolio.kbank.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "tb_wallets")
data class Wallet(

    @Id
    @Column(name = "wallet_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val walletId: UUID,

    @Column(name = "cpf", unique = true)
    val cpf: String,

    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "name")
    val nome: String,

    @Column(name = "balance")
    val balance: BigDecimal
)
