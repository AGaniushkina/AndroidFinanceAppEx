package com.example.androidfinanceappex.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidfinanceappex.data.local.entity.TransactionEntity
import com.example.androidfinanceappex.domain.enums.TransactionCategory
import com.example.androidfinanceappex.domain.enums.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("SELECT * FROM TransactionEntity WHERE type = :type")
    fun getTransactionsByType(type: TransactionType): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity WHERE category = :category")
    fun getTransactionsByCategory(category: TransactionCategory): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
}