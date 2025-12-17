package com.epikason.bloodbondkpi.views.dashboard.userDashboard.requestList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.ItemBloodRequestBinding

class BloodRequestAdapter(val requestBloodList: List<BloodRequest>) :
    RecyclerView.Adapter<BloodRequestAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        return ProductViewHolder(
            ItemBloodRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        requestBloodList[position].let {
            holder.binding.apply {
                tvPatientName.text = "Patient Name: ${it.pName}"
                tvBloodGroup.text = it.bloodGroup
                tvUnits.text = "Units: ${it.units} Bag"
                tvDate.text = "Donation Date: ${it.date}"
                tvLevel.text = it.eLevel
                tvTime.text = "Donation Time: ${it.time}"
                tvReason.text = it.reason
                tvLocation.text = "Location: ${it.hName}"
                tvMobile.text = "Mobile: ${it.number}"
            }
        }
    }

    override fun getItemCount(): Int {
        return requestBloodList.size
    }

    class ProductViewHolder(val binding: ItemBloodRequestBinding) :
        RecyclerView.ViewHolder(binding.root)
}