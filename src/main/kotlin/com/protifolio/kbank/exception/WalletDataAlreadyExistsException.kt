package com.protifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

//Não é a melhor pratica
//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class WalletDataAlreadyExistsException : KBankException {

    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause)
}