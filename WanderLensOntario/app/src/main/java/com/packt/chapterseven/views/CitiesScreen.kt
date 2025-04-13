package com.packt.chapterseven.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.packt.chapterseven.data.City
import com.packt.chapterseven.navigation.ContentType
import com.packt.chapterseven.viewmodel.CitiesViewModel
import kotlinx.serialization.InternalSerializationApi
import org.koin.androidx.compose.koinViewModel

@OptIn(InternalSerializationApi::class)
@Composable
fun CitiesScreen(
    onCityClicked: (City) -> Unit,
    contentType: ContentType,
) {
    val citiesViewModel: CitiesViewModel = koinViewModel()
    val citiesUIState by citiesViewModel.citiesUIState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = citiesUIState.isLoading
        ) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(
            visible = citiesUIState.cityList.isNotEmpty()
        ) {
            if (contentType == ContentType.List) {
                CityList(
                    onCityClicked = onCityClicked,
                    cityList = citiesUIState.cityList,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onFavoriteClicked = {
            citiesViewModel.updatePet(it)
        }
                )
            }

        }
        AnimatedVisibility(
            visible = citiesUIState.error != null
        ) {
            Text(text = citiesUIState.error ?: "")
        }
    }
}
