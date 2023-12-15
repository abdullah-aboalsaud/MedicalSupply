package com.example.medicalsupply.data

import android.util.Log

import androidx.lifecycle.MutableLiveData
import com.example.medicalsupply.core.KeyProducts
import com.example.medicalsupply.data.room.ProductDatabase
import com.example.medicalsupply.models.ModelProduct
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Repo {
    private val myRef = FirebaseDatabase.getInstance().getReference(KeyProducts)
    var productList = ArrayList<ModelProduct>()
    private val productDao = ProductDatabase.getProductDatabase().productDao()
    val productLiveData: MutableLiveData<ArrayList<ModelProduct>> = MutableLiveData()

     fun getProducts() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1 in snapshot.children) {
                    val product = snapshot1.getValue(ModelProduct::class.java)
                    productList.add(product!!)
                }
                productLiveData.value = productList
                cacheProducts(productList)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                getProductLocal()
            }
        })



    }

    fun cacheProducts(productList: List<ModelProduct>) {
        productDao.insert(productList)
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }

            })

    }

   fun getProductLocal() {
        productDao.getAllProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ModelProduct>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.e("getProductLocal", e.localizedMessage)
                }

                override fun onSuccess(t: List<ModelProduct>) {
                    productLiveData.value = t as ArrayList<ModelProduct>
                }
            })

    }

}
