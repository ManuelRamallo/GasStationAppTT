package com.mramallo.gasstationapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressModel(
    val street: String,
    val country: String,
    val city: String,
    val zip: String
)