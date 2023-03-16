package com.atech.empsearch.data.model

import androidx.recyclerview.widget.DiffUtil



data class EmpResponseItem(
    val address: Address,
    val department: String,
    val dob: String,
    val email: String,
    val gender: String,
    val hiredOn: String,
    val name: Name,
    val password: String,
    val phones: List<Phone>,
    val portrait: String,
    val roles: List<String>,
    val ssn: String,
    val terminatedOn: String,
    val thumbnail: String,
    val username: String
)

data class Address(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
)

data class Name(
    val first: String,
    val last: String
)

data class Phone(
    val number: String,
    val type: String
)

class DiffUtilEmpResponseItem : DiffUtil.ItemCallback<EmpResponseItem>(){
    override fun areItemsTheSame(oldItem: EmpResponseItem, newItem: EmpResponseItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: EmpResponseItem, newItem: EmpResponseItem): Boolean =
        oldItem == newItem

}