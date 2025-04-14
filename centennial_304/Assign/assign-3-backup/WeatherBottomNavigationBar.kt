package com.max.weitong_comp304lab3.view
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.wrapContentWidth
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.NavigationDrawerItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.max.weitong_comp304lab3.Screens
//
//
//@Composable
//fun WeatherNavigationBar(
//    onFavoriteClicked: () -> Unit,
//    onHomeClicked: () -> Unit,
//) {
//    val items = listOf(Screens.CityListScreen, Screens.FavoriteCityScreen)
//    val selectedItem = remember { mutableStateOf(items[0]) }
//    NavigationBar(
//        modifier = Modifier
//            .fillMaxWidth(),
//        containerColor = MaterialTheme.colorScheme.background
//    ) {
//        NavigationBarItem(
//            label = { Text(text = "Pets") },
//            selected = selectedItem.value == Screens.CityListScreen,
//            onClick = {
//                onHomeClicked()
//                selectedItem.value = Screens.CityListScreen
//            },
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Home,
//                    contentDescription = "Home Icon"
//                )
//            }
//        )
//
//        NavigationBarItem(
//            label = { Text(text = "Favorites") },
//            selected = selectedItem.value == Screens.FavoriteCityScreen,
//            onClick = {
//                onFavoriteClicked()
//                selectedItem.value = Screens.FavoriteCityScreen
//            },
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.Favorite,
//                    contentDescription = "Favorite Icon"
//                )
//            }
//        )
//
//    }
//}