package com.protifolio.kbank.controller

import com.protifolio.kbank.controller.dto.CreateWalletDto
import com.protifolio.kbank.controller.dto.DepositMoneyDto
import com.protifolio.kbank.controller.dto.TransferMoneyDto
import com.protifolio.kbank.service.TransferService
import com.protifolio.kbank.service.WalletService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

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