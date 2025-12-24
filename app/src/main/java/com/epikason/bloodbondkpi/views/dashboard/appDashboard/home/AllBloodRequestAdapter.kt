package com.epikason.bloodbondkpi.views.dashboard.appDashboard.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.ItemAllpostRequestBinding

class AllBloodRequestAdapter(private val requestBloodList: List<BloodRequest>)
    : RecyclerView.Adapter<AllBloodRequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RequestViewHolder {

        return RequestViewHolder(
            ItemAllpostRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val item = requestBloodList[position]

        holder.binding.apply {
            tvPatientName.text = "Patient Name: ${item.pName}"
            tvBloodGroup.text = item.bloodGroup
            tvUnits.text = "Units: ${item.units} Bag"
            tvDate.text = "Donation Date: ${item.date}"
            tvLevel.text = item.eLevel
            tvTime.text = "Donation Time: ${item.time}"
            tvReason.text = item.reason
            tvLocation.text = "Location: ${item.hName}"
            tvMobile.text = item.number

            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:${item.number}".toUri()
                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = requestBloodList.size

    class RequestViewHolder(val binding: ItemAllpostRequestBinding) :
        RecyclerView.ViewHolder(binding.root)
}