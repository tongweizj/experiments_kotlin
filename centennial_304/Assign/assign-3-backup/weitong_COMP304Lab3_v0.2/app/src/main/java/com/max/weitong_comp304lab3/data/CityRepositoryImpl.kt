package com.max.weitong_comp304lab3.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import com.max.weitong_comp304lab3.data.CityEntity
import kotlinx.coroutines.flow.map

class CityRepositoryImpl(private val cityDao: CityDao):CityRepository {

    override suspend fun initCityDB() {
        val cityList = getCity()
        cityList.map { city ->
            insertCity(city)
        }
    }
    override fun getCity(): List<City> {

        //delay(20) // 模拟网络请求
        return listOf(
            City(1, "Toronto", 43.86103683452462, -79.23287065483638 ),
            City(2, "Vancouver", 49.252552096536505, -123.10502410368238),
            City(3, "Calgary", 51.05100152533885, -114.05653795148136),
            City(4, "Saskatoon", 52.13194145777011, -106.63495622942521),
            City(5, "Winnipeg", 49.888323109512, -97.15428850402311),
            City(6, "Montreal",45.50777247053132, -73.62612495783571),
            City(7, "Halifax", 44.652188986681566, -63.609229201539925),
            City(8, "Fredericton", 45.957529552114686, -66.65116587843863),
        )
    }

//    override suspend fun initCityDB(){
//        val cityList : List<City> =  listOf(
//            City(1, "Toronto", 43.86103683452462, -79.23287065483638 ),
//            City(2, "Vancouver", 49.252552096536505, -123.10502410368238),
//            City(3, "Calgary", 51.05100152533885, -114.05653795148136),
//            City(4, "Saskatoon", 52.13194145777011, -106.63495622942521),
//            City(5, "Winnipeg", 49.888323109512, -97.15428850402311),
//            City(6, "Montreal",45.50777247053132, -73.62612495783571),
//            City(7, "Halifax", 44.652188986681566, -63.609229201539925),
//            City(8, "Fredericton", 45.957529552114686, -66.65116587843863),
//        )
//        cityList.map {city->
//            insertCity(city)
//
//        }
//        //delay(20) // 模拟网络请求
//        return listOf(
//            City(1, "Toronto", 43.86103683452462, -79.23287065483638 ),
//            City(2, "Vancouver", 49.252552096536505, -123.10502410368238),
//            City(3, "Calgary", 51.05100152533885, -114.05653795148136),
//            City(4, "Saskatoon", 52.13194145777011, -106.63495622942521),
//            City(5, "Winnipeg", 49.888323109512, -97.15428850402311),
//            City(6, "Montreal",45.50777247053132, -73.62612495783571),
//            City(7, "Halifax", 44.652188986681566, -63.609229201539925),
//            City(8, "Fredericton", 45.957529552114686, -66.65116587843863),
//        )
//    }
    override suspend fun getAllCity():  Flow<List<City>>{
        return cityDao.getAllCity().map { cityCached ->
            cityCached.map { cityEntity ->
                City(
                    id = cityEntity.id,
                    name = cityEntity.name,
                    latitude = cityEntity.latitude,
                    longitude = cityEntity.longitude,
                )
            }
        }
    }

    override suspend fun insertCity(city: City) {
        cityDao.insertCity(CityEntity(
            id = city.id,
            name = city.name,
            latitude = city.latitude,
            longitude = city.longitude,
        ))
    }

    override suspend fun updateCity(city: City) {
        cityDao.updateCity(CityEntity(
            id = city.id,
            name = city.name,
            latitude = city.latitude,
            longitude = city.longitude,
        ))
    }
}