package com.packt.chapterseven.navigation

sealed class Screens(val route: String) {
    object PetsScreen : Screens("pets")
    object PetDetailsScreen : Screens("petDetails")
    object FavoritePetsScreen : Screens("favoritePets")
    object WeatherScreen : Screens("weather")
    object CityListScreen : Screens("city_list")
}