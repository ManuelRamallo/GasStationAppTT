package com.mramallo.gasstationapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SalesPersonModel(
    val email: String,
    val name: String,
)