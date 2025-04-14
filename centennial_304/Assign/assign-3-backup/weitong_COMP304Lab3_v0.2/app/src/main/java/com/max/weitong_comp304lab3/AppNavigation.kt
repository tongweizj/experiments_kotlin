package com.max.weitong_comp304lab3

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.max.weitong_comp304lab3.view.weatherScreen
import com.max.weitong_comp304lab3.view.cityListScreen


enum class Screens(val route: String) {
    CityListScreen("city_list"),
    WeatherScreen("weather"),
    FavoriteCityScreen("FavoriteCityScreen")
}
// navController: NavHostController = rememberNavController()

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.CityListScreen.route
    ) {
        //CityListScreen
        composable(Screens.CityListScreen.route) {backStackEntry ->
            cityListScreen(navController)
        }
        //CityListScreen need to fix
//        composable(Screens.FavoriteCityScreen.route) {backStackEntry ->
//            cityListScreen(navController)
//        }
        //WeatherScreen
        composable(
            route= "${Screens.WeatherScreen.route}/{cityId}",
            arguments = listOf(navArgument("cityId") { type = NavType.StringType })
        ){ backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")
            cityId?.let {
                weatherScreen(navController, it)
            }
        }
    }
}