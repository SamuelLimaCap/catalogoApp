package com.example.catalogoapp.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    foreignKeys = [ForeignKey(entity = CategoryEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId"),
    )]
)
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val price: Float,
    val categoryId: Long,
    val imageLocation: String,
    val unit: String
)