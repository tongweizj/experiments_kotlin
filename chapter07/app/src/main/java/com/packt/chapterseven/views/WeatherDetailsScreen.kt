package com.packt.chapterseven.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.packt.chapterseven.data.City
import com.packt.chapterseven.data.Weather
import com.packt.chapterseven.R
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
             CardOverlayBox(
                 modifier = Modifier
                     .fillMaxSize()
                     .padding(16.dp),
                 city = petsUIState.cityList[cityId.toInt()],
                 weather = petsUIState.weatherMap[cityId.toInt()]!!
             )
//            FlowRow(
//                modifier = Modifier
//                    .padding(start = 6.dp, end = 6.dp)
//            ) {
//                Text(text = cityId)
//                Text(text = petsUIState.weatherMap.toString())
//
//            }

        }
        AnimatedVisibility(
            visible = petsUIState.error != null
        ) {
            Text(text = petsUIState.error ?: "")
        }

    }
}



@OptIn(InternalSerializationApi::class)
@Composable
fun CardOverlayBox(modifier: Modifier = Modifier,city: City,weather: Weather) {
    val petsViewModel: PetsViewModel = koinViewModel()
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

                // 按钮
                Button(
                    onClick = {
                        // 按钮点击事件处理
                        petsViewModel.toggleFavorite(city)
                    },
                    modifier = Modifier.padding(16.dp) // 添加一些内边距
                ) {
                    Text("点击我") // 按钮文本
                }
            }
        }
    }
}