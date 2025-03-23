package com.packt.chapterseven.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.packt.chapterseven.data.City
import com.packt.chapterseven.navigation.ContentType
import com.packt.chapterseven.viewmodel.PetsViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun WeatherDetailsScreen(onBackPressed: () -> Unit, cityId: String
) {
    val petsViewModel: PetsViewModel = koinViewModel()
    val petsUIState by petsViewModel.petsUIState.collectAsStateWithLifecycle()

    Log.d("maxLog", "WeatherDetailsScreen:${System.currentTimeMillis()} /cityList: ${petsUIState.weatherMap.toString()}")
    val cityItem: City = petsUIState.cityList[cityId.toInt()]

    LaunchedEffect(cityId) {
        Log.d("maxLog", "LaunchedEffect:${cityId}")
        petsViewModel.getWeather(cityItem.latitude, cityItem.longitude)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pet Details")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                }
            )
        },
        content = { paddingValues ->
            PetDetailsScreenContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                cityId = cityId,
                petsUIState= petsUIState

            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, InternalSerializationApi::class)
@Composable
fun PetDetailsScreenContent(modifier: Modifier, cityId: String, petsUIState: PetsUIState) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = petsUIState.isLoading
        ) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(
            visible = petsUIState.weather.temperature_2m != null
        ) {
            FlowRow(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
            ) {
                Text(text = cityId)
                Text(text = petsUIState.weatherMap.toString())

            }

        }
        AnimatedVisibility(
            visible = petsUIState.error != null
        ) {
            Text(text = petsUIState.error ?: "")
        }

    }
}