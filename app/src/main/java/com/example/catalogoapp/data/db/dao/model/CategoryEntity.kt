package com.example.catalogoapp.data.db.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class CategoryEntity (
    @PrimaryKey val category: String
)