package com.protifolio.kbank.Repository

import com.protifolio.kbank.Repository.dto.StatementView
import com.protifolio.kbank.entities.Wallet
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface WalletRepository : JpaRepository<Wallet, UUID> {

    companion object {
        const val SQL_STATEMENT = """ 
            Select
              BIN_TO_UUID(transfer_id)        as statement_id,
              "transfer"                      as type,
              transfer_value                  as statement_value,
              BIN_TO_UUID(wallet_receiver_id) as wallet_receiver,
              BIN_TO_UUID(wallet_sender_id)   as wallet_sender,
              transfer_date_time              as statement_date_time
            FROM
                tb_transfers
            WHERE wallet_receiver_id = UUID_TO_BIN(?1) or wallet_sender_id = UUID_TO_BIN(?1)
            UNION ALL
            Select
              BIN_TO_UUID(deposit_id)         as statement_id,
              "deposit"                       as type,
              deposit_value                   as statement_value,
              BIN_TO_UUID(wallet_id)          as wallet_receiver,
              ""                              as wallet_sender,
              deposit_date_time               as statement_date_time
            From tb_deposits

            WHERE wallet_id = UUID_TO_BIN(?1)  
        """

        const val SQL_COUNT_STATEMENT =
            """ 
            select count(*) FROM
                (
                """ + SQL_STATEMENT + """
                ) as total
            """
    }

    @Query(value = SQL_STATEMENT, countQuery = SQL_COUNT_STATEMENT, nativeQuery = true)
    fun findStatement (walletId: String, pageRequest: PageRequest): Page<StatementView>

    fun findByCpfOrEmail(cpf: String, email: String) : Optional<Wallet>
}