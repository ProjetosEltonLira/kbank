package com.portifolio.kbank.controller.dto

import jakarta.validation.constraints.DecimalMin
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.util.UUID

data class TransferMoneyDto (

    @field:NotNull
    var sender: UUID,

    @field:NotNull
    var receiver: UUID,

    @field:NotNull
    @field:DecimalMin("0.01")
    var value: BigDecimal
)
