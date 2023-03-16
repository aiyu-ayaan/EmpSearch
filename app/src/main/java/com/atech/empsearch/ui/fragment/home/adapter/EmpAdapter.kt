package com.atech.empsearch.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atech.empsearch.data.model.DiffUtilEmpResponseItem
import com.atech.empsearch.data.model.EmpResponseItem
import com.atech.empsearch.databinding.RowEmployeeBinding
import com.atech.empsearch.util.convertUTCDate

class EmpAdapter() :
    ListAdapter<EmpResponseItem, EmpAdapter.EmpViewHolder>(DiffUtilEmpResponseItem()) {

    inner class EmpViewHolder(
        private val binding: RowEmployeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(emp: EmpResponseItem) {
            binding.apply {
                textViewName.text = String.format("%s %s", emp.name.first, emp.name.last)
                textViewEmail.text = emp.email
                textViewDepartment.text = emp.department
                textViewPhone.text = emp.phones[0].number
                textViewJoinDate.text = emp.hiredOn.convertUTCDate()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder =
        EmpViewHolder(
            RowEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}