package com.epikason.bloodbondkpi.views.dashboard.userDashboard.requestList

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.epikason.bloodbondkpi.core.Nodes
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.ItemBloodRequestBinding
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class BloodRequestAdapter(
    private val requestBloodList: MutableList<BloodRequest>,
    private val onListEmpty: () -> Unit
) : RecyclerView.Adapter<BloodRequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RequestViewHolder {

        val binding = ItemBloodRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestViewHolder(binding)
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
            tvMobile.text = "Mobile: ${item.number}"

            btnDelete.setOnClickListener {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Delete Request")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes") { _, _ ->
                        deleteRequest(
                            holder.adapterPosition,
                            item.documentId,
                            holder
                        )
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = requestBloodList.size

    private fun deleteRequest(
        position: Int,
        documentId: String,
        holder: RequestViewHolder
    ) {
        FirebaseFirestore.getInstance()
            .collection(Nodes.BLOOD_REQUEST)
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                requestBloodList.removeAt(position)
                notifyItemRemoved(position)

                if (requestBloodList.isEmpty()) {
                    onListEmpty()
                }

                Toast.makeText(
                    holder.itemView.context,
                    "Request deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    class RequestViewHolder(val binding: ItemBloodRequestBinding) :
        RecyclerView.ViewHolder(binding.root)
}

