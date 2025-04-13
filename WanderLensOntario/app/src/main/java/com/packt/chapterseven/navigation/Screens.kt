package com.packt.chapterseven.navigation

sealed class Screens(val route: String) {
    object FavoriteScreen : Screens("favorite")
    object WanderlenScreen : Screens("wanderlen")
    object CitiesScreen : Screens("cities")
}