package com.max.weitong_comp304lab3.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Log

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.koinViewModel


import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.max.weitong_comp304lab3.Screens
import com.max.weitong_comp304lab3.data.City
import com.max.weitong_comp304lab3.data.Weather
import com.max.weitong_comp304lab3.viewModel.CityListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cityListScreen( navController: NavController) {
    val cityListViewModel: CityListViewModel = koinViewModel()
    val cityList:List<City> = cityListViewModel.cityList

    val cityWeatherMap by cityListViewModel.cityWeatherMap.collectAsStateWithLifecycle()
    Log.d("maxLogScreen", "CityListScreen: cityWeatherMap: ${cityWeatherMap.toString()}")

    LaunchedEffect(Unit){
        cityListViewModel.getAllCityWeather()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather") },
            )
        },
        content =  { paddingValues ->
            CityList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navController = navController,
                cityList = cityList,
                cityWeatherMap = cityWeatherMap
            )
        }

    )
}

@Composable
fun CityList(modifier: Modifier = Modifier,navController: NavController,cityList:List<City>,cityWeatherMap:HashMap<Int, Weather> ) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text(text = "cityWeatherMap: ${cityWeatherMap.toString()}", style = MaterialTheme.typography.bodyMedium)
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(cityList,) { city ->
                cityItem(city, onClick = {
                    navController.navigate("${Screens.WeatherScreen.route}/${city.id}")
                })
            }
            // Add more content here
        }
    }
}
@Composable
fun cityItem(city: City, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(100.dp)
            .clickable {
                onClick()
            },

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = " ${city.name}",fontSize = 24.sp, style = MaterialTheme.typography.bodyLarge,modifier = Modifier.weight(1f) )// 左侧 Text 占据剩余空间)
                Text(" check weither",fontSize = 16.sp,color = Color.Gray)
            }
        }
    }
}



