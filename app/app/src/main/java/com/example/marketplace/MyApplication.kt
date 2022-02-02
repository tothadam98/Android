package com.example.marketplace

import android.app.Application

class MyApplication: Application(){
    companion object{
        var token: String =""
        var username: String =""
        var product_id: String =""
    }
}