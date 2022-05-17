package net.carfax.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Context.checkInternetConnection(): Boolean {
    try {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (mobile != null) {
            if (wifi != null) {
                return mobile.isConnected || wifi.isConnected
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}


// function for showing loader
fun Context.showLoader() {
    try {
        LoadingDialog.getLoader().showLoader(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// function for hiding loading dialog
fun Context.hideLoader() {
    try {
        LoadingDialog.getLoader().dismissLoader()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


// function for the boilerplate of loader,i.e showing and hiding the loader
fun Context.loaderManager(visibleFlag: Boolean) {
    when (visibleFlag) {
        true ->
            showLoader()
        false ->
            hideLoader()
    }
}