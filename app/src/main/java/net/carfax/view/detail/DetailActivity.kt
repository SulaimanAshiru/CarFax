package net.carfax.view.detail

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import net.carfax.R
import net.carfax.databinding.ActivityDetailBinding
import net.carfax.utils.DexterPermissions
import net.carfax.utils.showSnackBar
import net.carfax.api.model.AssignmentModel

class DetailActivity : AppCompatActivity() {
    lateinit var detail: AssignmentModel.ListingObject
    lateinit var viewBinding:ActivityDetailBinding
    private val permissions = arrayListOf(Manifest.permission.CALL_PHONE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityDetailBinding.inflate(layoutInflater).apply {
            detail= intent.getSerializableExtra("detail") as AssignmentModel.ListingObject
            detail.apply {
                Glide.with(this@DetailActivity)
                    .load(images.firstPhoto.medium)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivDetailCarImage)
                tvCarMakeDetails.text="$year $make $model $trim"
                tvCarMileageDetails.text="$ $listPrice | ${mileage}k mi"
                tvLocationCarDetail.text= dealer.address
                tvExtColorCarDetail.text=exteriorColor
                tvIntColorCarDetail.text=interiorColor
                tvDriveTypeCarDetail.text=drivetype
                tvTransmissionCarDetail.text= transmission
                tvEngineCarDetail.text= engine
                tvFuelCarDetail.text=fuel
                btCallDealerDetails.setOnClickListener {


                    DexterPermissions.askPermission(
                        this@DetailActivity,
                        permissions,
                        object : DexterPermissions.PermissionCallback {
                            override fun granted() {
                                val intent = Intent(Intent.ACTION_CALL)
                                intent.data = Uri.parse("tel:${dealer.phone}")
                                startActivity(intent)
                            }

                            override fun denied() {
                                viewBinding.root.showSnackBar(resources.getString(R.string.permission_message))
                            }

                            override fun error(error: String) {

                            }
                        })
                }
            }
        }
        setContentView(viewBinding.root)
    }
}