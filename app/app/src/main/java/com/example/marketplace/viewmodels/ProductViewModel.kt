package com.example.marketplace.viewmodels
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.marketplace.MyApplication
import com.example.marketplace.repository.Repository


class ProductViewModel(private val repository: Repository) : ViewModel() {

    var code : String = ""

    init{
        Log.d("xxx", "ProductViewModel - Token: ${MyApplication.token}")
    }

    suspend fun deleteProduct() {
        try {
            val result =
                repository.removeProduct(MyApplication.token, MyApplication.product_id)
            code = result.message
            Log.d("xxx", "ProductViewModel - #remove:  ${code}")
        }catch(e: Exception){
            Log.d("xxx", "ProductViewModel - #remove: ${e.toString()}")
        }
    }
}

