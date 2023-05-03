package com.atech.empsearch.util

enum class QueryType(val value: String) {
    ADDRESS("address:"), DEP("dep:"), EMAIL("email:"), NAME("name:"), PHONE("phone:"), SSN("ssn:"), ALL(
        ""
    )
}
