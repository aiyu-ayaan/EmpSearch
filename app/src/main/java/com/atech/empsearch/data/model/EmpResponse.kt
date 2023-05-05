package com.atech.empsearch.data.model

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.atech.empsearch.util.convertUTCDate
import com.atech.empsearch.util.getShortForm


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
) {
    val fullName: String
        get() = "${name.first} ${name.last}".trim()

    val fullAddress: String
        get() = "${address.street}${if (address.street.isNotBlank()) "," else ""} ${address.city}${if (address.city.isNotBlank()) "," else ""}" +
                " ${address.state}${if (address.state.isNotBlank()) "," else ""} ${address.zip}".trim()

    val fullPhone: String
        get() = phones.joinToString(", ") { it.number }.trim()

    val hiredOnDate: String
        get() = hiredOn.convertUTCDate("mm-yyyy")

    val dobDate: String
        get() = dob.convertUTCDate("dd-mm-yyyy")

    val shortDepartment: String
        get() = department.getShortForm()
}

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

class DiffUtilEmpResponseItem : DiffUtil.ItemCallback<EmpResponseItem>() {
    override fun areItemsTheSame(oldItem: EmpResponseItem, newItem: EmpResponseItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: EmpResponseItem, newItem: EmpResponseItem): Boolean =
        oldItem == newItem

}