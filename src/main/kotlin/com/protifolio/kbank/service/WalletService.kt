package com.protifolio.kbank.service

import com.protifolio.kbank.Repository.DepositRepository
import com.protifolio.kbank.Repository.WalletRepository
import com.protifolio.kbank.Repository.dto.StatementView
import com.protifolio.kbank.controller.dto.*
import com.protifolio.kbank.entities.Deposit
import com.protifolio.kbank.entities.Wallet
import com.protifolio.kbank.exception.DeleteWalletException
import com.protifolio.kbank.exception.StatementException
import com.protifolio.kbank.exception.WalletDataAlreadyExistsException
import com.protifolio.kbank.exception.WalletNotFoundException

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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


    fun getStatements(walletId: UUID, page: Int, pageSize: Int): StatementsDto {

        val wallet = walletRepository.findById(walletId)
            .orElseThrow { WalletNotFoundException("There is no wallet with this id: $walletId") }

        var walletDto = toWalletDto(wallet)
        
        var pageRequest = PageRequest.of(page,pageSize , Sort.Direction.DESC, "statement_date_time")
        
        var statements =  walletRepository.findStatement(
            walletId = walletId.toString(),
            pageRequest = pageRequest
        ).map { view -> mapToDto(walletId, view) } //Do resultado da query, nós criamos um map de DTO, dentro do statements tem o DTO mapeado.

        var paginationDto = toPaginationDto(statements)

        return StatementsDto(
            wallet = walletDto,
            statement = statements.content,
            pagination = paginationDto)
    }

    private fun toPaginationDto(statements: Page<StatementsItemDto>) =
        PaginationDto(
            page = statements.number,
            pageSize = statements.size,
            totalElement = statements.totalElements,
            totalPages = statements.totalPages
        )

    private fun toWalletDto(wallet: Wallet) = WalletDto(
        wallet.walletId!!,
        wallet.cpf!!,
        wallet.email!!,
        wallet.nome!!,
        wallet.balance!!
    )

    private fun mapToDto(walletId: UUID, view: StatementView): StatementsItemDto{

        if (view.type.equals("deposit",ignoreCase = true)){
            return mapToDeposit(view)
        }
        if (view.type.equals("transfer",ignoreCase = true)
            && view.walletSender.equals(walletId.toString(),ignoreCase = true)){
                return mapWhenTransferSender(view)
        }
        if (view.type.equals("transfer",ignoreCase = true)
            && view.walletReceiver.equals(walletId.toString(),ignoreCase = true)){
            return mapWhenTransferReceiver(view)
        }

        throw StatementException("Invalid type " + view.type)
    }

    private fun mapToDeposit(view: StatementView): StatementsItemDto {
        return StatementsItemDto(
            statementId = view.statementId,
            type = view.type,
            literal = "money deposit",
            value = view.statementValue,
            dateTime = view.statementDateTime,
            operation = statementOperation.CREDIT
        )
    }

    private fun mapWhenTransferSender(view: StatementView): StatementsItemDto {
        return StatementsItemDto(
            statementId = view.statementId,
            type = view.type,
            literal = "money sent to " +view.walletReceiver,
            value = view.statementValue,
            dateTime = view.statementDateTime,
            operation = statementOperation.DEBIT
        )
    }

    private fun mapWhenTransferReceiver(view: StatementView): StatementsItemDto {
        return StatementsItemDto(
            statementId = view.statementId,
            type = view.type,
            literal = "money receiver from " + view.walletSender,
            value = view.statementValue,
            dateTime = view.statementDateTime,
            operation = statementOperation.CREDIT
        )
    }
}