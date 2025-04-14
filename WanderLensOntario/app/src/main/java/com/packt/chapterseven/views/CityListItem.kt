package com.packt.chapterseven.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.packt.chapterseven.R
import com.packt.chapterseven.data.City
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi


@OptIn(ExperimentalLayoutApi::class, InternalSerializationApi::class)
@Composable
fun CityListItem(
    city: City,
    onCityClicked: (City) -> Unit,
    onFavoriteClicked: (City) -> Unit
) {
    // 使用 rememberCoroutineScope 避免阻塞UI线程
    val scope = rememberCoroutineScope()
    // 创建景点城市名称到资源ID的映射
    val cityImageMap = mapOf(
        "thousand islands" to R.drawable.t1000islands,
        "niagara falls" to R.drawable.niagarafalls,
        "cn tower" to R.drawable.cntower,
        "parliament hill" to R.drawable.parliamenthill,
        "algonquin provincial park" to R.drawable.algonquin,
        "blue mountain resort" to R.drawable.bluemountainresort,
        "royal ontario museum" to R.drawable.rom,
        "niagara on the lake" to R.drawable.niagaraonthelake,
        "bruce peninsula national park" to R.drawable.brucepeninsulanationalpark,
        "canada wonderland" to R.drawable.wonderland,
        "toronto islands" to R.drawable.torontoislands,
        "ripley aquarium of canada" to R.drawable.aquarium,
        "sault ste marie canal" to R.drawable.saultstemariecanal,
        "sleeping giant" to R.drawable.sleepinggiant
    )
    val imageRes = cityImageMap[city.name.lowercase()] ?: R.drawable.default_city
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
//                    onCityClicked(city)
                    scope.launch {
                        // 先立即导航，再处理其他逻辑
                        onCityClicked(city)
                    }
                }
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "${city.name} MarkLand",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth
            )

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp)
                    .height(40.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = city.name, fontSize = 24.sp )
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
        }
    }
}
