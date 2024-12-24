package com.protifolio.kbank.Repository

import com.protifolio.kbank.entities.Deposit
import com.protifolio.kbank.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DepositRepository : JpaRepository<Deposit, UUID> {

}