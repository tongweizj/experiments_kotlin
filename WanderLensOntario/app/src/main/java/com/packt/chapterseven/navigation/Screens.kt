package com.packt.chapterseven.navigation

sealed class Screens(val route: String) {
    object PetsScreen : Screens("pets")
    object PetDetailsScreen : Screens("petDetails")
    object FavoriteScreen : Screens("favorite")
    object WanderlenScreen : Screens("wanderlen")
    object CityListScreen : Screens("city_list")
}