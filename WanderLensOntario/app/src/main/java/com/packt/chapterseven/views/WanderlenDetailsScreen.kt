package com.packt.chapterseven.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.packt.chapterseven.data.City
import com.packt.chapterseven.viewmodel.CitiesViewModel
import kotlinx.serialization.InternalSerializationApi
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.text.style.TextOverflow
@OptIn(ExperimentalMaterial3Api::class, InternalSerializationApi::class)
@Composable
fun WanderlenDetailsScreen(
    onBackPressed: () -> Unit,
    cityID: String
) {
    val citiesViewModel: CitiesViewModel = koinViewModel()
    val citiesUIState by citiesViewModel.citiesUIState.collectAsStateWithLifecycle()
    val city = citiesUIState.cityList[cityID.toInt()]
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black, // 标题文字颜色
                    navigationIconContentColor = Color.Black // 返回图标颜色
                ),
                title = {
                    Text(
                        city.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    )
                },
                actions={Icon(
                        modifier = Modifier
                            .clickable {
                                    citiesViewModel.updatePet(city.copy(isFavorite = !city.isFavorite))
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
            )},

                    scrollBehavior = scrollBehavior,
            )
        },
        content = { paddingValues ->
            PetDetailsScreenContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                city = city,
                onFavoriteClicked = {
                    citiesViewModel.updatePet(it)
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
    onFavoriteClicked: (City) -> Unit
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(1.dp)
        ) {
            MapContent(
                destination = city
            )
        }
    }
}

