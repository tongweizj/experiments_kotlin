package com.packt.chapterseven.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.packt.chapterseven.data.Cat
import com.packt.chapterseven.data.City
import com.packt.chapterseven.navigation.ContentType




@Composable
fun CityScreenContent(
    modifier: Modifier,
    onCityClicked: (City) -> Unit,
    contentType: ContentType,
    petsUIState: PetsUIState
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = petsUIState.isLoading
        ) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(
            visible = petsUIState.cityList.isNotEmpty()
        ) {
            if (contentType == ContentType.List) {
                CityList(
                    onPetClicked = onCityClicked,
                    cityList = petsUIState.cityList,

                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }
        AnimatedVisibility(
            visible = petsUIState.error != null
        ) {
            Text(text = petsUIState.error ?: "")
        }
    }
}