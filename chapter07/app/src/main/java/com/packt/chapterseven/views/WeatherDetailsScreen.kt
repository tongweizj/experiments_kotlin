package com.packt.chapterseven.views


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.packt.chapterseven.R
import com.packt.chapterseven.data.City
import com.packt.chapterseven.data.Weather
import com.packt.chapterseven.viewmodel.PetsViewModel
import kotlinx.serialization.InternalSerializationApi
import org.koin.androidx.compose.koinViewModel



@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun WeatherDetailsScreen(
    onBackPressed: () -> Unit,
    cityID: String) {
    val petsViewModel: PetsViewModel = koinViewModel()
    val petsUIState by petsViewModel.petsUIState.collectAsStateWithLifecycle()
    val city = petsUIState.cityList[cityID.toInt()]
    val weather = petsUIState.weatherMap[cityID.toInt()]
    Log.d("maxLog", "weatherMap start:${petsUIState.weatherMap.toString()}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Weather Details", color = Color.White)
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
                                contentDescription = "Back",
                                tint=Color.White
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
                city = city,
                weather = weather!!,
                onFavoriteClicked  = {
                    petsViewModel.updatePet(it)
                }
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, InternalSerializationApi::class)
@Composable
fun PetDetailsScreenContent(
    modifier: Modifier,
    city: City,
    weather: Weather,
onFavoriteClicked: (City) -> Unit
) {

    // 创建城市名称到资源ID的映射
    // TODO 重构时，放到viewmodel里面
    val cityImageMap = mapOf(
        "toronto" to R.drawable.toronto,
        "vancouver" to R.drawable.vancouver,
        "calgary" to R.drawable.calgary,
        "saskatoon" to R.drawable.saskatoon,
        "winnipeg" to R.drawable.winnipeg,
        "montreal" to R.drawable.montreal,
        "halifax" to R.drawable.halifax,
        "fredericton" to R.drawable.fredericton
    )
    val imageRes = cityImageMap[city.name.lowercase()] ?: R.drawable.default_city
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "${city.name} MarkLand",
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = city.name, fontSize = 24.sp, fontWeight = FontWeight.Bold )
            Icon(
                modifier = Modifier
                    .clickable {
                        onFavoriteClicked(city.copy(isFavorite = !city.isFavorite))
                    },
                imageVector = if (city.isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Favorite",
                tint = if (city.isFavorite) {
                    Color.Red
                } else {
                    Color.Gray
                },
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Wind Speed", fontSize = 18.sp, color = Color.Gray)
            Row() {
                Text(
                    "${weather.wind_speed_10m} ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700
                )
                Text("km/h", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
            }
        }
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                .height(40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Temperature", fontSize = 18.sp, color = Color.Gray)
            Row() {
                Text(
                    "${weather.temperature_2m} ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700
                )
                Text("°C", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
            }
        }
    }
}

