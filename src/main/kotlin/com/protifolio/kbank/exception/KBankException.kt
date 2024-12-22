package com.protifolio.kbank.exception

import org.springframework.http.ProblemDetail

abstract class KBankException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause)

    //criou um exceção generica.
    open fun toProblemDetail(): ProblemDetail{
        return ProblemDetail.forStatus(500).apply {
            title = "KBank internal Server Error"
            detail = "Contact JBank support"
        }
    }
}


