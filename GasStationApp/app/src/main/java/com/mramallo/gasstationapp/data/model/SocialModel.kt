package com.mramallo.gasstationapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SocialModel(
    val twitter: String,
    val instagram: String,
    val phone: String
)
