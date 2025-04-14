package com.packt.chapterseven.views

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
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
import org.koin.androidx.compose.koinViewModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.packt.chapterseven.BuildConfig
import com.packt.chapterseven.data.City
import kotlinx.coroutines.delay
import kotlinx.serialization.InternalSerializationApi
import org.koin.compose.koinInject
import com.packt.chapterseven.R
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
//
//@OptIn(ExperimentalPermissionsApi::class, InternalSerializationApi::class)
//@Composable
//fun MapContent(
//    destination: City,
//    directionsService: DirectionsService = koinInject()
//) {
//    // 假设用户当前位置（实际应用中应该获取真实位置）
//    val sourceLocation = LatLng(43.8673, -79.3358)
//    val destinationLocation = LatLng(destination.latitude, destination.longitude)
//    val context = LocalContext.current
//    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
//
//    var polylinePoints by remember { mutableStateOf<List<LatLng>?>(null) }
//    var currentLocation by remember { mutableStateOf(sourceLocation) }
//
//    var shouldLoadPath by remember { mutableStateOf(false) }
//
//    val locationPermissionsState = rememberMultiplePermissionsState(
//        permissions = listOf(
//            android.Manifest.permission.ACCESS_FINE_LOCATION,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//    )
//
//    val cameraState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(destinationLocation, 10f)
//    }
//
//
//    // 获取当前位置
//    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
//        if (locationPermissionsState.allPermissionsGranted) {
//            val location = runCatching {
//                withContext(Dispatchers.IO) {
//                    fusedLocationClient.lastLocation.await()
//                }
//            }.getOrNull()
//
//            if (location != null) {
//                currentLocation = LatLng(location.latitude, location.longitude)
//                shouldLoadPath = true // ✅ 成功后才触发路径加载
//            } else {
//                Toast.makeText(context, "无法获取当前位置", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    // 加载路径数据
//    LaunchedEffect(shouldLoadPath) {
//        if (!shouldLoadPath) return@LaunchedEffect
//
//        runCatching {
//            val response = directionsService.getDirections(
//                "${currentLocation.latitude},${currentLocation.longitude}",
//                "${destinationLocation.latitude},${destinationLocation.longitude}",
//                BuildConfig.PLACES_API_KEY
//            )
//
//            if (response.isSuccessful) {
//                val encoded = response.body()?.routes?.firstOrNull()?.overview_polyline?.points
//                encoded?.let {
//                    polylinePoints = PolyUtil.decode(it).map { LatLng(it.latitude, it.longitude) }
//                    val boundsBuilder = LatLngBounds.Builder()
//                    polylinePoints?.forEach { boundsBuilder.include(it) }
//                    val bounds = boundsBuilder.build()
//                    withContext(Dispatchers.Main) {
//                        cameraState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 100))
//                    }
//                } ?: Toast.makeText(context, "路线数据为空", Toast.LENGTH_SHORT).show()
//            } else {
//                Log.e("MapContent", "API调用失败: ${response.code()} ${response.message()}")
//                Toast.makeText(context, "路径加载失败", Toast.LENGTH_SHORT).show()
//            }
//        }.onFailure {
//            Log.e("MapContent", "路径加载异常", it)
//            Toast.makeText(context, "路径加载失败：${it.localizedMessage}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // 地图显示
//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraState,
//        properties = MapProperties(
//            isMyLocationEnabled = locationPermissionsState.allPermissionsGranted,
//            maxZoomPreference = 15f
//        ),
//        uiSettings = MapUiSettings(
//            zoomControlsEnabled = true,
//            compassEnabled = true
//        )
//    ) {
//        Marker(
//            state = rememberMarkerState(position = currentLocation),
//            title = "Current Location",
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
//        )
//        Marker(
//            state = rememberMarkerState(position = destinationLocation),
//            title = destination.name,
//            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
//        )
//        polylinePoints?.let {
//            Polyline(
//                points = it,
//                color = Color(0xFF4285F4),
//                width = 12f
//            )
//        }
//    }
//
//    // 权限对话框
//    if (!locationPermissionsState.allPermissionsGranted) {
//        AlertDialog(
//            onDismissRequest = { },
//            title = { Text("需要位置权限") },
//            text = { Text("此功能需要位置权限以显示路径") },
//            confirmButton = {
//                Button(onClick = {
//                    locationPermissionsState.launchMultiplePermissionRequest()
//                }) {
//                    Text("请求权限")
//                }
//            }
//        )
//    }
//}


@OptIn(ExperimentalPermissionsApi::class, InternalSerializationApi::class)
@Composable
fun MapContent(
    destination: City,
    directionsService: DirectionsService = koinInject()
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // 状态管理
    var polylinePoints by remember { mutableStateOf<List<LatLng>?>(null) }
    var currentLocation by remember { mutableStateOf(LatLng(43.8673, -79.3358)) } // 默认位置
    var isLoading by remember { mutableStateOf(true) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var lastTappedLocation by remember { mutableStateOf<LatLng?>(null) }
    // 权限检查
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    // 相机初始位置（聚焦目的地）
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(destination.latitude, destination.longitude),
            10f
        )
    }
    // ===== 点击/长按地图事件处理 =====
    // 扩展函数：格式化小数位数
    fun Double.format(digits: Int) = "%.${digits}f".format(this)
    val mapClickLambda = remember {
        { latLng: LatLng ->
            lastTappedLocation = latLng
            Toast.makeText(
                context,
                "Tapped Location: ${latLng.latitude.format(6)}, ${latLng.longitude.format(6)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val mapLongClickLambda = remember {
        { latLng: LatLng ->
            lastTappedLocation = latLng
            Toast.makeText(
                context,
                "Long Tapped Location: ${latLng.latitude.format(6)}, ${latLng.longitude.format(6)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    // 路径加载函数（独立协程）
    suspend fun loadPath(start: LatLng, end: LatLng) {
        runCatching {
            val response = withContext(Dispatchers.IO) {
                directionsService.getDirections(
                    "${start.latitude},${start.longitude}",
                    "${end.latitude},${end.longitude}",
                    BuildConfig.PLACES_API_KEY
                )
            }

            if (response.isSuccessful) {
                val encoded = response.body()?.routes?.firstOrNull()?.overview_polyline?.points
                encoded?.let {
                    polylinePoints = PolyUtil.decode(it)
                    // 自动调整视野包含完整路径
                    val bounds = LatLngBounds.Builder().apply {
                        polylinePoints?.forEach { include(it) }
                    }.build()
                    cameraState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 100))
                }
            }
        }.onFailure {
            Toast.makeText(context, "路径加载失败", Toast.LENGTH_SHORT).show()
        }
    }


    // ===== 关键优化：分阶段异步加载 =====
    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted) {
            // 阶段1：快速获取当前位置（不阻塞UI）
            val location = withContext(Dispatchers.IO) {
                runCatching { fusedLocationClient.lastLocation.await() }.getOrNull()
            }

            // 更新位置（如果成功）
            location?.let {
                currentLocation = LatLng(it.latitude, it.longitude)
                // 阶段2：延迟加载路径（确保地图已渲染）
                delay(300) // 短暂延迟让地图完成初始化
                loadPath(currentLocation, LatLng(destination.latitude, destination.longitude))
            }
        } else {
            showPermissionDialog = true
        }
        isLoading = false
    }



    // ===== 地图UI =====
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(
            isMyLocationEnabled = locationPermissionsState.allPermissionsGranted,
            maxZoomPreference = 15f
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            compassEnabled = true
        ),
        onMapLoaded = { isLoading = false },
        onMapClick = mapClickLambda,       // 点击事件
        onMapLongClick = mapLongClickLambda // 长按事件
    ) {

        // 目的地标记（始终显示）
        Marker(
            state = rememberMarkerState(position = LatLng(destination.latitude, destination.longitude)),
            title = destination.name,
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        )

        // 当前位置标记（有权限时显示）
        if (locationPermissionsState.allPermissionsGranted) {
            Marker(
                state = rememberMarkerState(position = currentLocation),
                title = "Your Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
        }
        // 点击/长按位置的临时标记
        lastTappedLocation?.let { latLng ->
            Marker(
                state = rememberMarkerState(position = latLng),
                title = "Selected Point",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        }
        // 路径（异步加载后显示）
        polylinePoints?.let {
            Polyline(
                points = it,
                color = Color(0xFF4285F4),
                width = 12f
            )
        }

        // 加载状态提示
        if (isLoading) {
            val loadingIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
            Marker(
                state = rememberMarkerState(position = LatLng(destination.latitude, destination.longitude)),
                icon = loadingIcon, // 自定义加载图标
                title = "Loading..."
            )
        }
    }

    // ===== 权限对话框 =====
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("需要位置权限") },
            text = { Text("请允许位置权限以显示实时路径") },
            confirmButton = {
                Button(onClick = {
                    locationPermissionsState.launchMultiplePermissionRequest()
                    showPermissionDialog = false
                }) {
                    Text("授权")
                }
            },
            dismissButton = {
                Button(onClick = { showPermissionDialog = false }) {
                    Text("拒绝")
                }
            }
        )
    }
}