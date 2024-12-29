package com.portifolio.kbank.controller

import com.portifolio.kbank.controller.dto.CreateWalletDto
import com.portifolio.kbank.controller.dto.DepositMoneyDto
import com.portifolio.kbank.controller.dto.StatementsDto
import com.portifolio.kbank.service.WalletService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.net.URI
import java.util.UUID

@RestController
@RequestMapping(path = (["/wallets"]))
class WalletController (private var walletService: WalletService){

    @PostMapping
    fun createWallet (@Valid @RequestBody dto: CreateWalletDto):ResponseEntity<Void>{

        var wallet = walletService.createWallet(dto)
        val location = URI.create("/wallets/${wallet.walletId}")

        return ResponseEntity.created(location).build()
    }

    @DeleteMapping(path = ["/{walletId}"])
    fun deleteWallet (@PathVariable("walletId") walledId: UUID):ResponseEntity<Void>{

        var walletDeleted = walletService.deleteWallet(walledId)

        return if (walletDeleted)
            ResponseEntity.noContent().build()
        else
            ResponseEntity.notFound().build()

    }


    @PostMapping(path = ["/{walletId}/deposits"])
    fun depositMoney (@PathVariable("walletId") walletId: UUID,
                      @Valid @RequestBody depositMoneyDto: DepositMoneyDto,
                      servletRequest: HttpServletRequest //é possível acessar os atributos do context.
    ):ResponseEntity<Void>{

         walletService.depositMoney(
            walletId = walletId,
            depositMoneyDto = depositMoneyDto,
            ipAddress = servletRequest.getAttribute("x-user-ip").toString()
        )
        return ResponseEntity.ok().build()
    }

    @GetMapping(path = ["/{walletId}/statements"])
    fun getStatements (@PathVariable("walletId") walletId: UUID,
                       @RequestParam(name = "page", defaultValue = "0") page :Int,
                       @RequestParam(name = "pageSize", defaultValue = "10") pageSize : Int,) :ResponseEntity<StatementsDto>{

        var statementsDto = walletService.getStatements(
            walletId = walletId,
            page = page,
            pageSize = pageSize
        )
        return ResponseEntity.ok(statementsDto)
    }
}