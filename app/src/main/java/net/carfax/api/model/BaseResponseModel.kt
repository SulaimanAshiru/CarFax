package net.carfax.api.model

import com.google.gson.annotations.SerializedName

open class BaseResponseModel(

) {

    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("success_code")
    val successCode: Int? = null

    @SerializedName("error")
    val error: String? = null

    @SerializedName("error_code")
    val errorCode: Int? = null

}

