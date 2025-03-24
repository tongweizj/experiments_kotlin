package com.packt.chapterseven.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun cityListItem(city: City, onPetClicked: (City) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                onPetClicked(city)
            }

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