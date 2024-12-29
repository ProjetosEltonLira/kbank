package com.portifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import java.net.URI

class WalletNotFoundException : KBankException {

    private var detalhe : String

    constructor(detalhe: String) : super(detalhe){
        this.detalhe = detalhe
    }

    override fun toProblemDetail(): ProblemDetail {
        return ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY).apply {
            title = "Wallet not found"
            detail = detalhe
            instance = (URI.create("/wallets/{walletId}/deposit"))
        }
    }
}