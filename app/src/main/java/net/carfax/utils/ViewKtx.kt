package net.carfax.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import net.carfax.R
import net.carfax.app.CarFaxApp


/*
  *
  * method to show snack bar
  * */

fun View.showSnackBar(
    message: String?
) {
    message?.let {

        Snackbar.make(
            this,
            it,
            Snackbar.LENGTH_LONG
        ).show()
    } ?: run {
        Snackbar.make(
            this,
            CarFaxApp.getInstance().getString(R.string.error_something_went_wrong),
            Snackbar.LENGTH_LONG
        ).show()
    }
}