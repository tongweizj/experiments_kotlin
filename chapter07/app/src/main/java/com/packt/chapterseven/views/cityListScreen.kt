package com.packt.chapterseven.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.packt.chapterseven.data.City
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun CityList(
    onPetClicked: (City) -> Unit,
    modifier: Modifier = Modifier,
    cityList:List<City>
) {
     LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(cityList) { city ->
                cityListItem(
                    city,
                    onPetClicked = onPetClicked
                        )
            }
            // Add more content here
        }
    }

