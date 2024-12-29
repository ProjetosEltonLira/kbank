package com.portifolio.kbank.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class StatementsItemDto(
    val statementId: String,
    val type:String,
    val literal:String,
    val value: BigDecimal,
    val dateTime: LocalDateTime,
    val operation: statementOperation
)