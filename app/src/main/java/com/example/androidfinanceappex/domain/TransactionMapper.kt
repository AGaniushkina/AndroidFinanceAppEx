package com.example.androidfinanceappex.domain

fun com.example.androidfinanceappex.data.local.entity.TransactionEntity.toDomain(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        categoryId = this.categoryId,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category = this.category,
        createdAt = this.createdAt,
        imagePath = this.imagePath,
    )
}

fun TransactionEntity.toLocal(): com.example.androidfinanceappex.data.local.entity.TransactionEntity {
    return com.example.androidfinanceappex.data.local.entity.TransactionEntity(
        id = this.id,
        categoryId = this.categoryId,
        name = this.name,
        amount = this.amount,
        type = this.type,
        category = this.category,
        createdAt = this.createdAt,
        imagePath = this.imagePath,
    )
}