package com.protifolio.kbank.controller.dto

data class StatementsDto (

    var wallet: WalletDto,
    val statement: List<StatementsItemDto>,
    val pagination: PaginationDto
)
