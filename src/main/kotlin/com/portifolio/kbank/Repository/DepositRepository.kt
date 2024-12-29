package com.portifolio.kbank.Repository

import com.portifolio.kbank.entities.Deposit
import com.portifolio.kbank.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DepositRepository : JpaRepository<Deposit, UUID> {

}