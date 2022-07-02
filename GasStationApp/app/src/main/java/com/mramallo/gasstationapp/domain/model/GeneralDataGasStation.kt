package com.mramallo.gasstationapp.domain.model

import com.mramallo.gasstationapp.data.model.*

data class GeneralDataGasStation(
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

fun GeneralDataGasStationModel.toDomain() = GeneralDataGasStation(
    id,
    slug,
    address,
    contact,
    social,
    name,
    category,
    shortDescription,
    description,
    openingHours,
    salesPerson,
    startDate,
    logo,
    latitude,
    longitude
)
