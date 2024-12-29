package com.portifolio.kbank.exception

import org.springframework.http.ProblemDetail
import java.net.URI

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
            instance = (URI.create("/wallets"))
        }
    }
}