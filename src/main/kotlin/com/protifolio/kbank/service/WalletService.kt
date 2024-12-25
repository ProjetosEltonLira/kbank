package com.protifolio.kbank.service

import com.protifolio.kbank.Repository.DepositRepository
import com.protifolio.kbank.Repository.WalletRepository
import com.protifolio.kbank.controller.dto.CreateWalletDto
import com.protifolio.kbank.controller.dto.DepositMoneyDto
import com.protifolio.kbank.entities.Deposit
import com.protifolio.kbank.entities.Wallet
import com.protifolio.kbank.exception.DeleteWalletException
import com.protifolio.kbank.exception.WalletDataAlreadyExistsException
import com.protifolio.kbank.exception.WalletNotFoundException

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Service
class WalletService (
    private var walletRepository: WalletRepository,
    private var depositRepository: DepositRepository
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



    @Transactional /*garante que todo o código do método será executado dentro de uma transação dentro do banco de dados se um transação der erro, não é dado o commit no banco de dados, faz tudo ou não faz nada*/
    fun depositMoney(walletId: UUID, depositMoneyDto: DepositMoneyDto, ipAddress : String){

        val wallet = walletRepository.findById(walletId)
            .orElseThrow { WalletNotFoundException("There is no wallet with this id: $walletId") }

        var deposit = Deposit(
            depositId = null,
            wallet = wallet,
            depositValue = depositMoneyDto.value,
            depositDateTime = LocalDateTime.now(),
            ipAddress = ipAddress,
        )
        depositRepository.save(deposit)

        wallet.balance = depositMoneyDto.value

        walletRepository.save(wallet)
    }
}