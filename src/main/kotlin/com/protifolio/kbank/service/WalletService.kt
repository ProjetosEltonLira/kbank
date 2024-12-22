package com.protifolio.kbank.service

import com.protifolio.kbank.Repository.WalletRepository
import com.protifolio.kbank.controller.dto.CreateWalletDto
import com.protifolio.kbank.entities.Wallet
import com.protifolio.kbank.exception.WalletDataAlreadyExistsException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class WalletService (
    private var walletRepository: WalletRepository
) {
    fun createWallet(dto: CreateWalletDto) : Wallet {

        var walletEncontrada = walletRepository.findByCpfOrEmail(dto.cpf,dto.email)

        if(walletEncontrada.isPresent){
            throw WalletDataAlreadyExistsException("Waller Already Exists in DataBase")
        }
        var wallet = Wallet() // Inicializando a variável wallet
        wallet.apply {
            balance = BigDecimal.ZERO
            cpf = dto.cpf
            nome = dto.nome
            email = dto.email
        }

        return walletRepository.save(wallet)
    }
}