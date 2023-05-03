package com.atech.empsearch.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atech.empsearch.data.model.DiffUtilEmpResponseItem
import com.atech.empsearch.data.model.EmpResponseItem
import com.atech.empsearch.databinding.RowEmployeeBinding
import com.atech.empsearch.util.convertUTCDate
import com.atech.empsearch.util.openDialer
import com.atech.empsearch.util.openMail

class EmpAdapter() :
    ListAdapter<EmpResponseItem, EmpAdapter.EmpViewHolder>(DiffUtilEmpResponseItem()) {

    inner class EmpViewHolder(
        private val binding: RowEmployeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(emp: EmpResponseItem) {
            binding.apply {
                val context = binding.root.context
                textViewName.text = String.format("%s %s", emp.name.first, emp.name.last)
                textViewEmail.apply {
                    text = emp.email
                    setOnClickListener {
                        context.openMail(emp.email)
                    }
                }
                textViewDepartment.text = emp.department
                textViewPhone.apply {
                    text = String.format("%s . %s", emp.phones[0].type, emp.phones[0].number)
                    setOnClickListener {
                        context.openDialer(emp.phones[0].number)
                    }
                }
                textViewJoinDate.text = emp.hiredOn.convertUTCDate("MM-yyyy")
                textViewSsn.text = String.format("SSN : %s", emp.ssn)
                textViewGender.text = emp.gender
                textViewAddress.text = emp.fullAddress
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder =
        EmpViewHolder(
            RowEmployeeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}