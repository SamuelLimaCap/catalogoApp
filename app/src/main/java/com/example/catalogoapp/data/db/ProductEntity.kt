package com.example.catalogoapp.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val price: Float,
    @Embedded val category: CategoryEntity,
    val imageLocation: String,
    val unit: String
)