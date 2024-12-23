package com.protifolio.kbank.controller.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.br.CPF

data class CreateWalletDto (

    @field:CPF
    @field:NotBlank(message = "CPF cannot be blank")
    val cpf: String,

    @field:Email(message = "Email must be valid")
    @field:NotBlank(message = "Email cannot be blank")
    val email: String,

    @field:NotBlank(message = "Name cannot be blank")
    val nome: String
)
