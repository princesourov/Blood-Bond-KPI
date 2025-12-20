package com.epikason.bloodbondkpi.views.dashboard.appDashboard.donerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.databinding.ItemDonerListBinding

class DonerListAdapter(val donerList: List<UserInfo>) : RecyclerView.Adapter<DonerListAdapter.DonerListViewHolder>() {

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

    override fun onBindViewHolder(
        holder: DonerListViewHolder,
        position: Int
    ) {
        donerList[position].let {
            holder.binding.apply {
                tvBloodGroup.text = it.bloodGroup
                tvDonerName.text = "Doner Name: ${it.name}"
                tvEmail.text = "Email: ${it.email}"
                tvmobile.text = "Mobile: ${it.phon}"
                tvGender.text = "Gender: ${it.gender}"
                tvDOB.text = "Date of Birth: ${it.dateOfBirth}"
                tvDepartment.text = "Department: ${it.department}"
                tvSeason.text = "Season: ${it.season}"
                tvRoll.text = "Roll: ${it.roll}"
                tvLastDonate.text = "Last Donate: ${it.lastDonate}"
                tvBio.text = "Bio: ${it.bio}"

            }
        }
    }

    override fun getItemCount(): Int {
        return donerList.size
    }

    class DonerListViewHolder(val binding: ItemDonerListBinding) :
        RecyclerView.ViewHolder(binding.root)
}