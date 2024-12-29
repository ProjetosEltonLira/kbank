package com.portifolio.kbank.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "tb_transfers")
data class Transfer(

    @Id
    @Column(name = "transfer_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val transferId: UUID?,

    @ManyToOne
    @JoinColumn(name = "wallet_receiver_id")
    val receiver: Wallet,

    @ManyToOne
    @JoinColumn(name = "wallet_sender_id")
    val sender: Wallet,

    @Column(name = "transfer_value")
    val transferValue: BigDecimal,

    @Column(name = "transfer_date_time")
    val transferDateTime: LocalDateTime


)
