package com.packt.chapterseven.data

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi
import kotlin.collections.set

class CityRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val cityDao: CityDao
) :CityRepository{


        //delay(20) // 模拟网络请求
        @OptIn(InternalSerializationApi::class)
        override fun getCity(): List<City> {

            //delay(20) // 模拟网络请求
            return listOf(
                City(0, "Toronto", 43.86103683452462, -79.23287065483638 ),
                City(1, "Vancouver", 49.252552096536505, -123.10502410368238),
                City(2, "Calgary", 51.05100152533885, -114.05653795148136),
                City(3, "Saskatoon", 52.13194145777011, -106.63495622942521),
                City(4, "Winnipeg", 49.888323109512, -97.15428850402311),
                City(5, "Montreal",45.50777247053132, -73.62612495783571),
                City(6, "Halifax", 44.652188986681566, -63.609229201539925),
                City(7, "Fredericton", 45.957529552114686, -66.65116587843863),
            )
        }

    // Form Database
    @OptIn(InternalSerializationApi::class)
    override suspend fun getCityList(): Flow<List<City>> {
        return withContext(dispatcher) {
            cityDao.getCityList()
                .map { cityCached ->
                    cityCached.map { cityEntity ->
                        City(
                            id = cityEntity.id,
                            name = cityEntity.name,
                            latitude = cityEntity.latitude,
                            longitude = cityEntity.longitude,
                            isFavorite = cityEntity.isFavorite
                        )
                    }
                }
        }
    }

//    @OptIn(InternalSerializationApi::class)
//    override suspend fun toggleFavorite(city: City) {
//        city.isFavorite = !city.isFavorite
//        //TODO 同步数据库
//    }


    @OptIn(InternalSerializationApi::class)
    override  suspend fun getFavoriteCities():Flow<List<City>>  {

        return withContext(dispatcher) {
            cityDao.getFavoriteCities()
                .map { citiesCached ->
                    citiesCached.map { cityEntity ->
                        City(
                            id = cityEntity.id,
                            name = cityEntity.name,
                            latitude = cityEntity.latitude,
                            longitude = cityEntity.longitude,
                            isFavorite = cityEntity.isFavorite
                        )
                    }
                }
        }
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun updateCity(city: City) {
        withContext(dispatcher) {
            cityDao.update(CityEntity(
                        id = city.id,
                name = city.name,
                latitude = city.latitude,
                longitude = city.longitude,
                isFavorite = city.isFavorite
            ))
        }
    }


    // populateDatabase 填充数据库
    override suspend fun populateDatabase() {
        withContext(dispatcher) {
        cityDao.insertCity(CityEntity(0, "Niagara Falls", 43.07794038118995, -79.07801266265763,false ))
        cityDao.insertCity(CityEntity(1, "CN Tower", 43.64258946316858, -79.38712117551113,false ))
        cityDao.insertCity(CityEntity(2, "Parliament Hill", 45.42375180376362, -75.70091827358915,false ))
        cityDao.insertCity(CityEntity(3, "Thousand Islands", 44.361391198716085, -76.00435602348027,false ))
        cityDao.insertCity(CityEntity(4, "Algonquin Provincial Park", 45.83727119267289, -78.37914536008243,false ))
        cityDao.insertCity(CityEntity(5, "Blue Mountain Resort", 44.50192814967334, -80.31648728329542,false ))
        cityDao.insertCity(CityEntity(6, "Royal Ontario Museum", 43.66788816859609, -79.39483074667382,false ))
        cityDao.insertCity(CityEntity(7, "Niagara on the Lake", 43.092875649144965, -79.10181633670241,false ))
        cityDao.insertCity(CityEntity(8, "Bruce Peninsula National Park", 45.22019627293353, -81.53072878708778,false ))
        cityDao.insertCity(CityEntity(9, "Canada Wonderland", 43.84250115771652, -79.54126914666733,false ))
        cityDao.insertCity(CityEntity(10, "Toronto Islands", 43.620598839195516, -79.37761566733012,false ))
        cityDao.insertCity(CityEntity(11, "ripley aquarium of canada", 43.64234151640285, -79.3866129313292,false ))
        cityDao.insertCity(CityEntity(12, "Sault Ste Marie Canal", 46.51353071101094, -84.3500919778278,false ))
        cityDao.insertCity(CityEntity(13, "Sleeping Giant", 48.353588159787996, -88.90314351610981,false ))
        Log.d("maxLog", "populateDatabase push data to db!")
        }
    }
}