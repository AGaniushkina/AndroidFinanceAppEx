package com.example.androidfinanceappex.domain

import com.example.androidfinanceappex.domain.enums.TransactionCategory
import com.example.androidfinanceappex.domain.enums.TransactionType
import java.util.*

data class TransactionEntity(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val amount: Double,
    val type: TransactionType,
    val category: TransactionCategory,
    val createdAt: Long,
    val imagePath: String? = null,
)