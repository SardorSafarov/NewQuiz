package com.example.newquiz.app

import android.util.Log
import android.view.View

fun D(message:String)
{
    Log.d("sardor", "---->   $message   <-----")
}

fun View.visble(){
    this.visibility = View.VISIBLE
}

fun View.invisble(){
    this.visibility = View.INVISIBLE
}