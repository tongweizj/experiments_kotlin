package com.max.weitong_comp304lab3_ex1

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.max.weitong_comp304lab3_ex1.data.City
import com.max.weitong_comp304lab3_ex1.view.CityListScreen
import com.max.weitong_comp304lab3_ex1.view.WeatherScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

enum class Screens(val route: String) {
    CityListScreen("city_list"),
    WeatherScreen("weather")
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.CityListScreen.route
    ) {
        composable(Screens.CityListScreen.route) {backStackEntry ->
            CityListScreen(navController)
        }
        composable(
            route= "${Screens.WeatherScreen.route}/{cityId}",
            arguments = listOf(navArgument("cityId") { type = NavType.StringType })
        ){ backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")
            cityId?.let {
                WeatherScreen(navController, it)
            }
    }
    }
}