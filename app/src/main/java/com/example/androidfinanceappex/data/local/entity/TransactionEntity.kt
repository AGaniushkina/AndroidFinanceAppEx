package com.example.androidfinanceappex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidfinanceappex.domain.enums.TransactionCategory
import com.example.androidfinanceappex.domain.enums.TransactionType

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var categoryId: Int,
    var name: String,
    val amount: Double,
    var type: TransactionType,
    var category: TransactionCategory,
    val createdAt: Long,
    val imagePath: String? = null,
)