package com.example.medicalsupply.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicalsupply.models.ModelProduct
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(productList: List<ModelProduct>):Completable

    @Query("select * from ModelProduct")
     fun getAllProducts(): Single<List<ModelProduct>>

}