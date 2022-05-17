package net.carfax.api

data class BaseCallbackState(
    val isLoading:Boolean,
    val success:Boolean?=null,
    val responseCode:Int?=null,
    val message: String? =null,
    var response:Any?= null
)