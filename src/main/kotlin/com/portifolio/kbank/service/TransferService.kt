package com.portifolio.kbank.service

import com.portifolio.kbank.Repository.TransferRepository
import com.portifolio.kbank.Repository.WalletRepository
import com.portifolio.kbank.controller.dto.TransferMoneyDto
import com.portifolio.kbank.entities.Transfer
import com.portifolio.kbank.entities.Wallet
import com.portifolio.kbank.exception.TransferException
import com.portifolio.kbank.exception.WalletNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TransferService (
    private val transferRepository: TransferRepository,
    private val walletRepository: WalletRepository
){

    @Transactional
    fun transferMoney(dto: TransferMoneyDto) {

        var receiver = walletRepository.findById(dto.receiver)
            .orElseThrow( { WalletNotFoundException("There is no wallet with this id: ${dto.receiver}") })

        var sender = walletRepository.findById(dto.sender)
            .orElseThrow( { WalletNotFoundException("There is no wallet with this id: ${dto.sender}") })

        if (sender.balance!!.compareTo(dto.value) == -1) {
            throw TransferException("Insufficiente balance.")

        }
        persistTransfer(receiver, sender, dto)
        var transferRealizada = updateWallets(sender, dto, receiver)

        return transferRealizada
    }

    private fun updateWallets(
        sender: Wallet,
        dto: TransferMoneyDto,
        receiver: Wallet
    ) {
        sender.balance = sender.balance!!.subtract(dto.value)
        receiver.balance = receiver.balance!!.add(dto.value)
        walletRepository.save(sender)
        walletRepository.save(receiver)
    }

    private fun persistTransfer(
        receiver: Wallet,
        sender: Wallet,
        dto: TransferMoneyDto
    ): Transfer {
        var transfer = Transfer(
            transferId = null,
            receiver = receiver,
            sender = sender,
            transferValue = dto.value,
            transferDateTime = LocalDateTime.now()
        )

        return transferRepository.save(transfer)
    }

}
