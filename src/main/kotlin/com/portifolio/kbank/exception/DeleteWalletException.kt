package com.portifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import java.net.URI

class DeleteWalletException : com.portifolio.kbank.exception.KBankException {

    private var detalhe : String

    constructor(detalhe: String) : super(detalhe){
        this.detalhe = detalhe
    }

    override fun toProblemDetail(): ProblemDetail {
        return ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY).apply {
            title = "You cannot delete this wallet"
            detail = detalhe
            instance = (URI.create("/wallets/id"))
        }
    }
}