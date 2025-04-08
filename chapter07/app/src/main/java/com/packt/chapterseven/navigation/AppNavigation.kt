package com.packt.chapterseven.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.packt.chapterseven.views.FavoritePetsScreen
import com.packt.chapterseven.views.CitiesScreen
import com.packt.chapterseven.views.WeatherDetailsScreen
import kotlinx.serialization.InternalSerializationApi


@OptIn(InternalSerializationApi::class)
@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.CityListScreen.route
    ) {
        //cityList
        composable(Screens.CityListScreen.route) {
            CitiesScreen(
                onPetClicked = { city ->
                    navHostController.navigate(
                        "${Screens.WeatherScreen.route}/${city.id}"
                    )
                },
                contentType = contentType
            )
        }
        //WeatherScreen

        composable(
            route= "${Screens.WeatherScreen.route}/{cityId}",
            arguments = listOf(
                navArgument("cityId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")
            cityId?.let {
                WeatherDetailsScreen(
                    onBackPressed = {
                        navHostController.popBackStack()
                    },
                    cityID= cityId
                )
            }

        }

        composable(Screens.FavoritePetsScreen.route) {
            FavoritePetsScreen(
                onPetClicked = { city ->
                    navHostController.navigate(
                        "${Screens.WeatherScreen.route}/${city.id}"
                    )

                },
            )
        }
    }
}