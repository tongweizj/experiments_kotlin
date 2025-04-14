package com.max.weitong_comp304lab3_ex1.view
import android.util.Log
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.max.weitong_comp304lab3_ex1.data.City
import com.max.weitong_comp304lab3_ex1.viewModel.CityViewModel
import com.max.weitong_comp304lab3_ex1.viewModel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(navController: NavController, cityId: String) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
//    val weatherViewModel: WeatherViewModel = remember { WeatherViewModel() }
    val cityViewModel: CityViewModel = koinViewModel()
    val city = cityViewModel.getCity(cityId)
    // 当 id 变化时，重新加载天气数据
    LaunchedEffect(cityId) {
        Log.d("maxLog", "LaunchedEffect：${cityId}")
        weatherViewModel.updateWeatherByCity(city)
    }
    val weatherUIState by weatherViewModel.weatherUIState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->  Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            when {
                weatherUIState.isLoading -> CircularProgressIndicator()
                weatherUIState.error != null -> Text("错误: ${weatherUIState.error}", color = MaterialTheme.colorScheme.error)
                else -> {
                    Text("Temperature: ${weatherUIState.currentWeather?.temperature_2m ?: "N/A"}°C")
                    Text("Wind Speed: ${weatherUIState.currentWeather?.wind_speed_10m ?: "N/A"} km/h")
                }
            }
        } }
    )
}
//
//@Composable
//fun WeatherDetailsContent(
//    modifier: Modifier = Modifier,
//    city:City,
//    weatherUIState: WeatherUIState,
//    weatherViewModel: WeatherViewModel
//) {
//    val viewModelScope = rememberCoroutineScope() // Access the coroutine scope
//// 在组合时调用 fetchCurrentWeather()
//    Column(
//        modifier = modifier.padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "City: ${city.name}")
//        Text(text = "Current Weather: ${weatherUIState.currentWeather}")
//        if (weatherUIState.isLoading){
//            Text(text = "loading...")
//        }
//        if (weatherUIState.error != null){
//            Text(text = weatherUIState.error)
//        }
////
////        AnimatedVisibility(
////            visible = weatherUIState.isLoading
////        ) {
////            CircularProgressIndicator()
////        }
//
////        AnimatedVisibility(
////            visible = weatherUIState.error == null
////        ) {
//            // 显示天气信息
////            weatherUIState.currentWeather.let { weather ->
////                Row {
////                Text(text = "Temperature: ${weather.temperature_2m}°C")
////                Text(text = "Wind Speed: ${weather.wind_speed_10m} km/h")}
////            }
////
////        }
////        AnimatedVisibility(
////            visible = weatherUIState.error != null
////        ) {
////            Text(text = weatherUIState.error ?: "")
////        }
//
//
//
//        Spacer(modifier = Modifier.height(16.dp))
////        Button(onClick = {
////            Log.d("maxLog", "Button Clicked - About to call getWeather")
////            Log.d("maxLog", "Button Clicked - City data: $city") //log the city
////
////            weatherViewModel.updateWeatherByCity(city)
////
////            Log.d("maxLog", "Button Clicked - Finished calling getWeather")
////        }) {
////            Text("Get Weather")
////        }
//
//
//        Button(onClick = {
//            Log.d("maxLog", "Button Clicked - About to call updateWeatherByCity")
//            Log.d("maxLog", "Button Clicked - City data: $city") //log the city
//            viewModelScope.launch {
//                weatherViewModel.updateWeatherByCity(city)
//            }
//            Log.d("maxLog", "Button Clicked - Finished calling updateWeatherByCity")
//        }) {
//            Text("Get Weather")
//        }
//    }
//
//}