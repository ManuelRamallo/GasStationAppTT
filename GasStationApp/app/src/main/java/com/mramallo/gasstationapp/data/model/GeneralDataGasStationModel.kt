package com.mramallo.gasstationapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GeneralDataGasStationModel (
    val id: String?,
    val slug: String?,
    val address: AddressModel?,
    val contact: ContactModel?,
    val social: SocialModel?,
    val name: String?,
    val category: String?,
    val shortDescription: String?,
    val description: String?,
    val openingHours: String?,
    val salesPerson: SalesPersonModel?,
    val startDate: String?,
    val logo: LogoModel?,
    val latitude: Float?,
    val longitude: Float?
)

