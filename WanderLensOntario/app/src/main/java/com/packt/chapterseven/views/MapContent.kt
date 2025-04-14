package com.packt.chapterseven.views

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapContent(
    sourceLocation: LatLng,
    destinationLocation: LatLng
) {

    val context = LocalContext.current
    var polylinePoints by remember { mutableStateOf<List<LatLng>?>(null) }
    var currentLocation by remember { mutableStateOf(sourceLocation) }


    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(destinationLocation, 10f)
    }

    // 权限处理
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    // 获取当前位置
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted) {
            try {
                val location = try {
                    withContext(Dispatchers.IO) {
                        fusedLocationClient.lastLocation.await()
                    }
                } catch (e: Exception) {
                    null
                }

                location?.let {
                    currentLocation = LatLng(it.latitude, it.longitude)
                    // 获取路径的代码...
                } ?: run {
                    Toast.makeText(context, "无法获取当前位置", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "错误: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 获取路径
    LaunchedEffect(currentLocation, destinationLocation) {
        if (locationPermissionsState.allPermissionsGranted) {
            try {
                val directionsService = Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DirectionsService::class.java)

                val response = directionsService.getDirections(
                    "${currentLocation.latitude},${currentLocation.longitude}",
                    "${destinationLocation.latitude},${destinationLocation.longitude}",
                    "AIzaSyA6WCBp-Lzr3fzXPawDmG__8Kg5kbGymNE"
                )

                if (response.isSuccessful) {
                    val points = response.body()?.routes?.firstOrNull()?.overview_polyline?.points
                    points?.let {
                        polylinePoints = PolyUtil.decode(it)
                        // 调整相机位置
                        val bounds = LatLngBounds.Builder()
                            .include(currentLocation)
                            .include(destinationLocation)
                            .apply {
                                polylinePoints?.forEach { latLng -> include(latLng) }
                            }
                            .build()
                        cameraState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 100))
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "路径加载失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 只在有权限时启用位置功能
    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = locationPermissionsState.allPermissionsGranted,
            maxZoomPreference = 15f
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = mapProperties,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            compassEnabled = true
        )
    ) {
        // 标记和路径
        Marker(
            state = rememberMarkerState(position = currentLocation),
            title = "Current Location",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )
        Marker(
            state = rememberMarkerState(position = destinationLocation),
            title = "Destination Location",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )
        polylinePoints?.let { points ->
            Polyline(
                points = points,
                color = Color(0xFF4285F4),
                width = 12f
            )
        }


    }

    // 处理权限被拒绝的情况
    if (!locationPermissionsState.allPermissionsGranted) {
        AlertDialog(
            onDismissRequest = { /* */ },
            title = { Text("需要位置权限") },
            text = { Text("此功能需要位置权限以提供更好的服务") },
            confirmButton = {
                Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                    Text("请求权限")
                }
            }
        )
    }
}


// Directions API服务接口
interface DirectionsService {
    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Response<DirectionsResponse>
}

// API响应数据结构
data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val overview_polyline: OverviewPolyline
)

data class OverviewPolyline(
    val points: String
)