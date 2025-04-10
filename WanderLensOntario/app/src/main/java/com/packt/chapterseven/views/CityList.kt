package com.packt.chapterseven.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.packt.chapterseven.R
import com.packt.chapterseven.data.City
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun CityList(
    onPetClicked: (City) -> Unit,
    cityList: List<City>,
    modifier: Modifier,
    onFavoriteClicked: (City) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(cityList) { city ->
            PetListItem(
                city = city,
                onPetClicked = onPetClicked,
                onFavoriteClicked = onFavoriteClicked
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, InternalSerializationApi::class)
@Composable
fun PetListItem(
    city: City,
    onPetClicked: (City) -> Unit,
    onFavoriteClicked: (City) -> Unit
) {
    // 创建城市名称到资源ID的映射
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
                    onPetClicked(city)
                }
        ) {
//            AsyncImage(
//                model = "https://cataas.com/cat/${city.id}",
//                contentDescription = "Cute cat",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp),
//                contentScale = ContentScale.FillWidth
//            )
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
