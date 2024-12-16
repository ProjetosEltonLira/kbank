package com.protifolio.kbank.Repository

import com.protifolio.kbank.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WalletRepository : JpaRepository<Wallet, UUID> {

    abstract fun findByCpfOrEmail(cpf: String, email: String) : Optional<Wallet>
}