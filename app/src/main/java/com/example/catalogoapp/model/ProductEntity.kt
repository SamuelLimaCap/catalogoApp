package com.example.catalogoapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    foreignKeys = [ForeignKey(entity = CategoryEntity::class,
        parentColumns = arrayOf("category"),
        childColumns = arrayOf("categoryName"),
        onUpdate = CASCADE,
    )]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val price: Float,
    val categoryName: String,
    val imageName: String,
    val unit: String
)