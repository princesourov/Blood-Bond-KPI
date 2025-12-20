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
    private val requestBloodList: MutableList<BloodRequest>
) : RecyclerView.Adapter<BloodRequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemBloodRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val bloodRequest = requestBloodList[position]

        holder.binding.apply {
            tvPatientName.text = "Patient Name: ${bloodRequest.pName}"
            tvBloodGroup.text = bloodRequest.bloodGroup
            tvUnits.text = "Units: ${bloodRequest.units} Bag"
            tvDate.text = "Donation Date: ${bloodRequest.date}"
            tvLevel.text = bloodRequest.eLevel
            tvTime.text = "Donation Time: ${bloodRequest.time}"
            tvReason.text = bloodRequest.reason
            tvLocation.text = "Location: ${bloodRequest.hName}"
            tvMobile.text = "Mobile: ${bloodRequest.number}"

            // Delete button with confirmation
            btnDelete.setOnClickListener {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Delete Request")
                    .setMessage("Are you sure you want to delete this blood request?")
                    .setPositiveButton("Yes") { _, _ ->
                        deleteRequest(holder.adapterPosition, bloodRequest.documentId, holder)
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = requestBloodList.size
    private fun deleteRequest(position: Int, documentId: String, holder: RequestViewHolder) {
        FirebaseFirestore.getInstance()
            .collection(Nodes.BLOOD_REQUEST)
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                // Remove item from list and notify RecyclerView
                requestBloodList.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(
                    holder.itemView.context,
                    "Blood request deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    holder.itemView.context,
                    "Delete failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    class RequestViewHolder(val binding: ItemBloodRequestBinding) :
        RecyclerView.ViewHolder(binding.root)
}
