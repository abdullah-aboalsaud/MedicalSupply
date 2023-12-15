package com.example.medicalsupply.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.medicalsupply.models.ModelProduct

@Database(entities = [ModelProduct::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private lateinit var productDatabase: ProductDatabase

        fun initDatabase(context: Context) {
            productDatabase = Room.databaseBuilder(
                context,
                ProductDatabase::class.java, "product_db")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getProductDatabase():ProductDatabase{
            return productDatabase
        }
    }

}