package com.epikason.bloodbondkpi.views.dashboard.appDashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.ItemAllpostRequestBinding

class AllBloodRequestAdapter(val requestBloodList: List<BloodRequest>) : RecyclerView.Adapter<AllBloodRequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestViewHolder {

        return RequestViewHolder(
            ItemAllpostRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RequestViewHolder,
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

    class RequestViewHolder(val binding: ItemAllpostRequestBinding) :
        RecyclerView.ViewHolder(binding.root)
}