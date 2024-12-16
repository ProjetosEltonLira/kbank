package com.protifolio.kbank.exception

abstract class KBankException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause)
}


