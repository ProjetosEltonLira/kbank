package com.portifolio.kbank.controller

import com.portifolio.kbank.controller.dto.TransferMoneyDto
import com.portifolio.kbank.service.TransferService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(path = (["/transfers"]))
class TransferController (
    private var transferService: TransferService){

    @PostMapping
    fun transfer (@Valid @RequestBody dto: TransferMoneyDto):ResponseEntity<Void>{

        var transfer = transferService.transferMoney(dto)
        val location = URI.create("/transfer/${transfer}")

        return ResponseEntity.created(location).build()
    }
}