package net.carfax.utils

import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

object DexterPermissions {

    interface PermissionCallback {
        fun granted()
        fun denied()
        fun error(error: String)
    }

    fun askPermission(
        context: Context,
        permission: List<String>,
        permissionCallback: PermissionCallback
    ) {
        Dexter.withContext(context).withPermissions(permission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            permissionCallback.granted()
                        }
                        if (report.isAnyPermissionPermanentlyDenied) {
                            permissionCallback.denied()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                permissionCallback.error(it.name)
            }.onSameThread()
            .check()
    }

}