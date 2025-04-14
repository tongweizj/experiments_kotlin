package com.max.weitong_comp304lab3_ex1.data

interface CityRepository {
    suspend fun getCity(): List<City>
}


//class UserRepository {
//    private val users = listOf(
//        User(1, "John Doe", 30),
//        User(2, "Jane Doe", 28),
//        User(3, "Sam Smith", 25)
//    )
//
//    fun getUsers(): Flow<List<User>> {
//        return flowOf(users)
//    }
//}
