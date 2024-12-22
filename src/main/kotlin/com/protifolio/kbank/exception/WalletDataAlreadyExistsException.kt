package com.protifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ResponseStatus

//Não é a melhor pratica
//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class WalletDataAlreadyExistsException : KBankException {

    private var detalhe : String

    constructor(detalhe: String) : super(detalhe){
        this.detalhe = detalhe
    }

    override fun toProblemDetail(): ProblemDetail {
        return ProblemDetail.forStatus(422).apply {
            title = "Wallet data already exists"
            detail = detalhe
        }
    }
}