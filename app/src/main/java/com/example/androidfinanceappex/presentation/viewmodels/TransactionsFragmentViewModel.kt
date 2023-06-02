package com.example.androidfinanceappex.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinanceappex.data.local.FinanceManagerDatabase
import com.example.androidfinanceappex.domain.TransactionEntity
import com.example.androidfinanceappex.domain.enums.TransactionCategory
import com.example.androidfinanceappex.domain.enums.TransactionType
import com.example.androidfinanceappex.domain.toLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsFragmentViewModel @Inject constructor(
    private val db: FinanceManagerDatabase
) : ViewModel() {

    val transactions = db.transactionDao.getAllTransactions()

    fun insertTransaction(
        name: String,
        amount: Double,
        type: TransactionType,
        category: TransactionCategory,
        imagePath: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val transaction = TransactionEntity(
                id = 0,
                categoryId = 0,
                name = name,
                amount = amount,
                type = type,
                category = category,
                createdAt = System.currentTimeMillis(),
                imagePath = imagePath,
            )
            val id = db.transactionDao.insertTransaction(transaction.toLocal())
        }
    }


}