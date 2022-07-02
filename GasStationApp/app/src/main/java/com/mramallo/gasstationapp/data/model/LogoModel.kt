package com.mramallo.gasstationapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LogoModel(
    val format: String,
    val url: String
)
