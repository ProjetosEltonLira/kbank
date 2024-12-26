package com.protifolio.kbank.Repository.dto

import java.math.BigDecimal
import java.time.LocalDateTime

//no java essa classe teria os get nos nomes das variaveis e encerrariam o nome com ()
interface StatementView {
    val statementId: String
    val type: String
    val statementValue: BigDecimal
    val walletReceiver: String
    val walletSender: String
    val statementDateTime: LocalDateTime
}