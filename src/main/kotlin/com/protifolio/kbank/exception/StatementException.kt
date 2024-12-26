package com.protifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import java.net.URI

class StatementException : KBankException {

    private var detalhe : String

    constructor(detalhe: String) : super(detalhe){
        this.detalhe = detalhe
    }

    override fun toProblemDetail(): ProblemDetail {
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR).apply {
            title = "Invalid statemente scenario"
            detail = detalhe
            instance = (URI.create("/{walletId}/statements"))
        }
    }
}