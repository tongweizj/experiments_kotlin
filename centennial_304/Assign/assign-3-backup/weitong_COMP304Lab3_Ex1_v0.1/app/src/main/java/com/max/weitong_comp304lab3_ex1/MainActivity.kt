package com.max.weitong_comp304lab3_ex1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
//    private val userViewModel: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    setContent {
        MaterialDesignDemoTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavigation() //应用 AppNavigation
            }
        }
    }
    }
}
//
//content =  { paddingValues ->
//    WeatherScreen(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues)
//    )
//}
//@Composable
//fun UserListScreen(userViewModel: UsersViewModel) {
//    val users by userViewModel.users.collectAsState()
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "User List", style = MaterialTheme.typography.headlineMedium)
//        Spacer(modifier = Modifier.height(8.dp))
//        LazyColumn {
//            items(users) { user ->
//                UserItem(user)
//            }
//        }
//    }
//}

//@Composable
//fun UserItem(user: User) {
//    Column(modifier = Modifier.padding(8.dp)) {
//        Text(text = "Name: ${user.name}", style = MaterialTheme.typography.bodyLarge)
//        Text(text = "Age: ${user.age}", style = MaterialTheme.typography.bodyMedium)
//    }
//}



@Composable
fun MaterialDesignDemoTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            background = Color(0xFF121212),
            surface = Color(0xFF121212),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
        )
    }

    val typography = Typography(
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        )
        // Add other text styles as needed
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}