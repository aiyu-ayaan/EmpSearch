package com.atech.empsearch.data.model

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil



@Keep
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

@Keep
data class Address(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
)

@Keep
data class Name(
    val first: String,
    val last: String
)

@Keep
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