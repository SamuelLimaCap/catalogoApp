package com.example.catalogoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catalogoapp.data.db.dao.CatalogoDao
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity


@Database(
    entities = [ProductEntity::class, CategoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCatalogoDao() : CatalogoDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "catalogo_db.db"
            ).build()
    }
}