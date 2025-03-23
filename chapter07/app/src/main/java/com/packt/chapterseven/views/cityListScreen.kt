package com.packt.chapterseven.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.packt.chapterseven.data.Cat
import com.packt.chapterseven.data.City


//
//@Composable
//fun cityListScreen( navController: NavController) {
//    val cityListViewModel: CityListViewModel = koinViewModel()
//    val cityList:List<City> = cityListViewModel.cityList
//
//    val cityWeatherMap by cityListViewModel.cityWeatherMap.collectAsStateWithLifecycle()
//    Log.d("maxLogScreen", "CityListScreen: cityWeatherMap: ${cityWeatherMap.toString()}")
//
//    LaunchedEffect(Unit){
//        cityListViewModel.getAllCityWeather()
//    }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Weather") },
//            )
//        },
//        content =  { paddingValues ->
//            CityList(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                navController = navController,
//                cityList = cityList,
//                cityWeatherMap = cityWeatherMap
//            )
//        }
//
//    )
//}

@Composable
fun CityList(
    onPetClicked: (City) -> Unit,
    modifier: Modifier = Modifier,
    cityList:List<City>
) {
     LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(cityList) { city ->
                cityItem(
                    city,
                    onPetClicked = onPetClicked
                        )
            }
            // Add more content here
        }
    }

@Composable
fun cityItem(city: City, onPetClicked: (City) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Text(text = " ${city.name.toString()}",fontSize = 24.sp,  color = Color.Gray)// 左侧 Text 占据剩余空间)
                Text(" check weither",fontSize = 16.sp,color = Color.Gray)

        }
    }
}