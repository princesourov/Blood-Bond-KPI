package com.epikason.bloodbondkpi.views.dashboard.appDashboard.donerlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.databinding.ItemDonerListBinding

class DonerListAdapter(
    private val donerList: MutableList<UserInfo>
) : RecyclerView.Adapter<DonerListAdapter.DonerListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonerListViewHolder {
        return DonerListViewHolder(
            ItemDonerListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DonerListViewHolder, position: Int) {
        val it = donerList[position]
        holder.binding.apply {
            tvBloodGroup.text = it.bloodGroup
            tvDonerName.text = "Doner Name: ${it.name}"
            tvEmail.text = "Email: ${it.email}"
            tvMobile.text = it.phon
            tvGender.text = "Gender: ${it.gender}"
            tvDOB.text = "Date of Birth: ${it.dateOfBirth}"
            tvDepartment.text = "Department: ${it.department}"
            tvSeason.text = "Season: ${it.season}"
            tvRoll.text = "Roll: ${it.roll}"
            tvLastDonate.text = "Last Donate: ${it.lastDonate}"
            tvStatus.text = "Status: ${it.status}"

            root.setOnClickListener {
                val donorCall = tvMobile.text.toString()
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:$donorCall".toUri()
                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = donerList.size

    fun updateList(newList: List<UserInfo>) {
        donerList.clear()
        donerList.addAll(newList)
        notifyDataSetChanged()
    }

    class DonerListViewHolder(
        val binding: ItemDonerListBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
