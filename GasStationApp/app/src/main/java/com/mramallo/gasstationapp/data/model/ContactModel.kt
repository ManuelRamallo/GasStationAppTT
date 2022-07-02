package com.mramallo.gasstationapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactModel(
    val email: String,
    val phone: String
)
