package com.protifolio.kbank.exception

import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(KBankException::class)
    fun handleKBankException (e : KBankException): ProblemDetail {
        return e.toProblemDetail()
    }
}