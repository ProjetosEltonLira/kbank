package com.portifolio.kbank.Repository

import com.portifolio.kbank.entities.Transfer
import com.portifolio.kbank.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransferRepository : JpaRepository<Transfer, UUID> {


}