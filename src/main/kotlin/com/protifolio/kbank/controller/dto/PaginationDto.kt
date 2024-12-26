package com.protifolio.kbank.controller.dto

data class PaginationDto(
    val page :Int,
    val pageSize: Int,
    val totalElement: Long,
    val totalPages: Int
)