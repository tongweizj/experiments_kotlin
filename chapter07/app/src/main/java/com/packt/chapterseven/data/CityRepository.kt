package com.packt.chapterseven.data

import kotlinx.serialization.InternalSerializationApi

interface CityRepository {
    @OptIn(InternalSerializationApi::class)
    fun getCity(): List<City>

}