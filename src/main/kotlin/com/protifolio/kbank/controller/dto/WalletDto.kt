package com.protifolio.kbank.controller.dto

import java.math.BigDecimal
import java.util.UUID

data class WalletDto(
    val walletId : UUID,
    val cpf: String,
    val name: String,
    val email: String,
    val balance: BigDecimal
)
