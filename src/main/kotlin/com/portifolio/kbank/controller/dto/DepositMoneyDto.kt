package com.portifolio.kbank.controller.dto

import jakarta.validation.constraints.DecimalMin
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

data class DepositMoneyDto (

    @field:NotNull
    @field:DecimalMin("10.00")
    var value: BigDecimal)
