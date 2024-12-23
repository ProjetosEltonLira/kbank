package com.protifolio.kbank.controller

import com.protifolio.kbank.controller.dto.CreateWalletDto
import com.protifolio.kbank.exception.WalletDataAlreadyExistsException
import com.protifolio.kbank.service.WalletService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(path = (["/wallets"]))
class WalletController (private var walletService: WalletService){

    @PostMapping
    fun createWallet (@Valid @RequestBody dto: CreateWalletDto):ResponseEntity<Void>{

        var wallet = walletService.createWallet(dto)
        val location = URI.create("/wallets/${wallet.walletId}")

        return ResponseEntity.created(location).build()
    }
}