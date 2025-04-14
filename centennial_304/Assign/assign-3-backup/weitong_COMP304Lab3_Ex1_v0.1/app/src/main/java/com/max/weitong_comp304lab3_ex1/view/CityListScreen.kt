package com.max.weitong_comp304lab3_ex1.view

//import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.max.weitong_comp304lab3_ex1.data.City
import com.max.weitong_comp304lab3_ex1.viewModel.CityViewModel
import org.koin.androidx.compose.koinViewModel
import com.max.weitong_comp304lab3_ex1.Screens

@Composable
fun CityListScreen(navController: NavController) {
    val cityViewModel: CityViewModel = koinViewModel()
    val cityUIState by cityViewModel.cityUIState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp)) {
        when {
            cityUIState.isLoading -> CircularProgressIndicator()
            cityUIState.error != null -> Text("错误: ${cityUIState.error}", color = MaterialTheme.colorScheme.error)
            cityUIState.city.isNotEmpty() -> LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(cityUIState.city,) { city ->
                    UserItem(city, onClick = {
                        navController.navigate("${Screens.WeatherScreen.route}/${city.id}")
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { cityViewModel.loadUsers() }) {
            Text("加载人物列表")
        }
    }
}

@Composable
fun UserItem(city: City, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
                },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${city.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Age: ${city.id}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}