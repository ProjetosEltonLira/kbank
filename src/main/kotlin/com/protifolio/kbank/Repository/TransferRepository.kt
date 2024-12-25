package com.protifolio.kbank.Repository

import com.protifolio.kbank.entities.Transfer
import com.protifolio.kbank.entities.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransferRepository : JpaRepository<Transfer, UUID> {


}