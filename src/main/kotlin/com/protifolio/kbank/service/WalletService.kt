package com.protifolio.kbank.service

import com.protifolio.kbank.Repository.WalletRepository
import com.protifolio.kbank.controller.dto.CreateWalletDto
import com.protifolio.kbank.entities.Wallet
import com.protifolio.kbank.exception.DeleteWalletException
import com.protifolio.kbank.exception.WalletDataAlreadyExistsException
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class WalletService (
    private var walletRepository: WalletRepository
) {
    fun createWallet(dto: CreateWalletDto) : Wallet {

        var walletEncontrada = walletRepository.findByCpfOrEmail(dto.cpf,dto.email)

        if(walletEncontrada.isPresent){
            throw WalletDataAlreadyExistsException("Waller Already Exists in DataBase")
        }
        var wallet = Wallet()
        wallet.apply {
            balance = BigDecimal.ZERO
            cpf = dto.cpf
            nome = dto.nome
            email = dto.email
        }

        return walletRepository.save(wallet)
    }

    fun deleteWallet(walledId: UUID): Boolean {

       var wallet =  walletRepository.findById(walledId)

       if(wallet.isPresent){
           if (wallet.get().balance!!.compareTo(BigDecimal.ZERO) != 0) {
               throw DeleteWalletException("The balance is not zero. To delete, please leave yout wallet with a balance zero")
           }
           walletRepository.deleteById(walledId)

       }
       return wallet.isPresent
    }
}