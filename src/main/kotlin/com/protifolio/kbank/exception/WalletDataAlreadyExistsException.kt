package com.protifolio.kbank.exception

class WalletDataAlreadyExistsException : KBankException {

    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause)
}