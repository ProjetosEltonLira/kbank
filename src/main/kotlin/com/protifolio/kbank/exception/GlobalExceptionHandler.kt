package com.protifolio.kbank.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import kotlin.streams.toList

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(KBankException::class)
    fun handleKBankException (e : KBankException): ProblemDetail {
        return e.toProblemDetail()
    }

    //Otimo exemplo de como pegar a lista de erros
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ProblemDetail {
        val listInvalidParams = e.bindingResult.fieldErrors.map { fieldError ->
            InvalidParamDto(
                field = fieldError.field,
                reason = fieldError.defaultMessage ?: "Invalid value"
            )
        }

        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value()).apply {
            title = "Invalid Request Parameters"
            detail = "There are invalid fields in the request"
            properties = mapOf("validationErrors" to listInvalidParams) //Passar um Map para dentro do properties
        }
        return problemDetail
    }
}



