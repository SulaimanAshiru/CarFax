package net.carfax.view.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.carfax.R
import net.carfax.api.model.AssignmentModel
import net.carfax.databinding.LayoutCarsCellBinding


class AssignmentAdapter(
    private var dataList: ArrayList<AssignmentModel.ListingObject>,
    var callbacks:AssignmentCallbacks
) :
    RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    interface AssignmentCallbacks {
        fun call(number:String)
        fun moveToDetails(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        return AssignmentViewHolder(
            LayoutCarsCellBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val data = dataList[position]
        holder.viewBinding.apply {
            data.apply {
                ivCarImage.apply {
                    Glide.with(context).load(images.firstPhoto.medium)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder).into(this)
                }
                tvCarYearMake.text="$year $make $model $trim"
                tvCarPriceMileage.text="$ $listPrice | ${mileage}k mi"
                tvCarLocation.text= dealer.address
                tvCarCallDealer.setOnClickListener {
                    callbacks.call(number = dealer.phone)
                }
                llMoveToDetails.setOnClickListener{
                    callbacks.moveToDetails(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class AssignmentViewHolder(val viewBinding: LayoutCarsCellBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}