package com.packt.chapterseven.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.packt.chapterseven.data.City
import com.packt.chapterseven.viewmodel.CitiesViewModel
import kotlinx.serialization.InternalSerializationApi
import org.koin.androidx.compose.koinViewModel

@OptIn(InternalSerializationApi::class)
@Composable
fun FavoriteScreen(
    onCityClicked: (City) -> Unit
) {
    val citiesViewModel: CitiesViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        citiesViewModel.getFavoriteCities()
    }
    val cities by citiesViewModel.favoriteCities.collectAsStateWithLifecycle()

    if (cities.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No favorite cities")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(cities) { pet ->
                CityListItem(
                    city = pet,
                    onCityClicked = onCityClicked,
                    onFavoriteClicked = {
                        citiesViewModel.updatePet(it)
                    }
                )
            }
        }
    }
}