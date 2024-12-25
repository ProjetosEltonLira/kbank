package com.protifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import java.net.URI

class TransferException : KBankException {

    private var detalhe : String

    constructor(detalhe: String) : super(detalhe){
        this.detalhe = detalhe
    }

    override fun toProblemDetail(): ProblemDetail {
        return ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY).apply {
            title = "Transfer not allowed"
            detail = detalhe
            instance = (URI.create("/transfer"))
        }
    }
}