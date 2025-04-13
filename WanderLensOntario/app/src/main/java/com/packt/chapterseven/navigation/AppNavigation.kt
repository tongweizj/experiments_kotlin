package com.packt.chapterseven.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.packt.chapterseven.views.FavoriteScreen
import com.packt.chapterseven.views.CitiesScreen
import com.packt.chapterseven.views.WanderlensDetailsScreen
import kotlinx.serialization.InternalSerializationApi


@OptIn(InternalSerializationApi::class)
@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.CitiesScreen.route
    ) {
        //cityList
        composable(Screens.CitiesScreen.route) {
            CitiesScreen(
                onCityClicked = { city ->
                    navHostController.navigate(
                        "${Screens.WanderlenScreen.route}/${city.id}"
                    )
                },
                contentType = contentType
            )
        }

        //WanderlenScreen
        composable(
            route= "${Screens.WanderlenScreen.route}/{cityId}",
            arguments = listOf(
                navArgument("cityId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val cityId = backStackEntry.arguments?.getString("cityId")
            cityId?.let {
                WanderlensDetailsScreen(
                    onBackPressed = {
                        navHostController.popBackStack()
                    },
                    cityID= cityId
                )
            }

        }

        composable(Screens.FavoriteScreen.route) {
            FavoriteScreen(
                onCityClicked = { city ->
                    navHostController.navigate(
                        "${Screens.WanderlenScreen.route}/${city.id}"
                    )

                },
            )
        }
    }
}