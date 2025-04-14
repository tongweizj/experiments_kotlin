package com.max.weitong_comp304lab3.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.max.weitong_comp304lab3.R
import com.max.weitong_comp304lab3.data.City
import com.max.weitong_comp304lab3.data.Weather
import com.max.weitong_comp304lab3.viewModel.CityListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun weatherScreen(navController: NavController, cityId: String) {
    val cityListViewModel: CityListViewModel = koinViewModel()
    var city:City = cityListViewModel.getCity(cityId)
    val cityWeatherMap by cityListViewModel.cityWeatherMap.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        // 背景图
        Image(
            painter = painterResource(id = R.drawable.city_0), // 替换为你的背景图
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.zIndex(-1f) // 将背景图置于底层
        )

        // TopBar
        TopAppBar(title = { Text("页面标题") })

        // 页面内容
        // ... 其他Compose组件 ...
        fier
                    .fillMaxSize()
                    .padding(paddingValues),
                city = city,
                weather = cityWeatherMap[city.id]!!
            )
    }

}

// fun weatherScreen(navController: NavController, cityId: String) {
//     val cityListViewModel: CityListViewModel = koinViewModel()
//     var city:City = cityListViewModel.getCity(cityId)
//     val cityWeatherMap by cityListViewModel.cityWeatherMap.collectAsStateWithLifecycle()
//     Scaffold(
//         topBar = {
//             TopAppBar(
//                 title = { Text("Today Weather") },
//                 navigationIcon = {
//                     IconButton(onClick = { navController.popBackStack() }) {
//                         Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
//                     }
//                 }
//             )
//         },
//         content =  { paddingValues ->
//             CardOverlayBox(
//                 modifier = Modifier
//                     .fillMaxSize()
//                     .padding(paddingValues),
//                 city = city,
//                 weather = cityWeatherMap[city.id]!!
//             )
//         }
//     )
// }

@Composable
fun weatherDetail(modifier: Modifier = Modifier,city: City) {
    Column(
        modifier = modifier
            .padding(16.dp) ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            Text("cityID:${city.latitude} ")
            Text("Wind Speed::${city.longitude}")
        }
        // Add more content here
    }
}

@Composable
fun CardOverlayBox(modifier: Modifier = Modifier,city: City,weather: Weather) {
    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.city_0), // 替换为你的图片资源
            contentScale = ContentScale.Crop // 根据需要调整缩放方式
                     ))
                     {
        // Card 覆盖在 Box 上
                         Card (
                             //colors = CardDefaults.cardColors(containerColor = Color.White),

                             elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                             modifier = Modifier.align(Alignment.Center)
                                 .background(Color.White.copy(alpha = 0.7f)) // 半透明背景
                                 .fillMaxWidth(0.8f) // 填充 80% 的宽度
                                 .fillMaxHeight(0.5f) // 填充 50% 的高度
                         ) {
                             Column(Modifier.padding(16.dp)) {
                                 // Set views in a column

                                 Text("${city.name}", fontSize = 36.sp,fontWeight = FontWeight.W700)
                                 Spacer(modifier = Modifier.height(16.dp))
                                 Row(verticalAlignment = Alignment.CenterVertically) {
                                     Text("${weather.temperature_2m} ",fontSize = 48.sp,fontWeight = FontWeight.W700)
                                     Text(" °C",fontSize = 24.sp,fontWeight = FontWeight.W700,color = Color.Gray)
                                 }

                                 Text("Temperature",fontSize = 24.sp,color = Color.Gray)
                                 Spacer(modifier = Modifier.height(16.dp))
                                 Row(verticalAlignment = Alignment.CenterVertically) {
                                     Text("${weather.wind_speed_10m} ",fontSize = 48.sp,fontWeight = FontWeight.W700)
                                     Text(" km/h",fontSize = 24.sp,fontWeight = FontWeight.W700,color = Color.Gray)
                                 }
                                 Text("Wind Speed", fontSize = 24.sp, color = Color.Gray)

                                 Spacer(modifier = Modifier.height(16.dp))
                             }
                         }
    }
}