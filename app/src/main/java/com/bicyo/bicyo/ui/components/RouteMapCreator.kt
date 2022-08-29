package com.bicyo.bicyo.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.bicyo.bicyo.entities.Route
import com.bicyo.bicyo.tools.DirectionsParser
import com.bicyo.bicyo.tools.TaskRequestDirections
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import okhttp3.internal.wait
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ColumnScope.RouteMapCreator(route: Route) {
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoomPreference = 23f,
                minZoomPreference = 1f,
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true,
            )
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = true,
                myLocationButtonEnabled = true,
            )
        )
    }

    //Points in the line
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-0.21, -78.49), 11f)
    }

    var renderedPaths by remember { mutableStateOf(mutableListOf(mutableListOf<LatLng>())) }
    var pathIsRendered by remember { mutableStateOf(false) }
    var startPoint by remember { mutableStateOf(LatLng(-0.21, -78.49)) }
    var endPoint by remember { mutableStateOf(LatLng(-0.21, -78.49)) }
    var isFirstPoint by remember { mutableStateOf(true)}

    if (!pathIsRendered and !isFirstPoint and !((startPoint.latitude == endPoint.latitude) and (startPoint.longitude == endPoint.longitude))) {
        object : TaskRequestDirections() {
            override fun onPostExecute(latLngList: MutableList<LatLng>) {
                renderedPaths.set(renderedPaths.indexOfLast { true },latLngList)
                pathIsRendered = true
            }
        }.execute(
            startPoint,
            endPoint
        )
    }


    AskLocationPermissions {

        Box(Modifier.height(600.dp)) {
            GoogleMap(
                properties = mapProperties,
                uiSettings = mapUiSettings,
                cameraPositionState = cameraPositionState,
                onMapClick = {
                    endPoint = it
                    pathIsRendered = false
                }
            ) {
                if (isFirstPoint) {
                    Marker(
                        state = MarkerState(position = endPoint)
                    )
                } else{
                    for (point in route.points) {
                        Marker(
                            state = MarkerState(position = point)
                        )
                    }
                }

                Polyline(
                    points = renderedPaths.flatten()
                )

            }
            Row(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Button(onClick = {
                    if((startPoint.latitude != endPoint.latitude) and (startPoint.longitude != endPoint.longitude)){
                        route.points.add(endPoint)
                        startPoint = LatLng(endPoint.latitude,endPoint.longitude)
                        if(!isFirstPoint) renderedPaths.add(mutableListOf())
                        pathIsRendered = false
                        isFirstPoint = false
                    }
                }) {
                    Text(text = "Agregar punto")
                }
                Button(onClick = {
                    if(!isFirstPoint and (route.points.size > 1)){
                        route.points.removeLast()
                        if((renderedPaths.size > 0)){
                            renderedPaths.removeLast()
                            if(renderedPaths.size > 1) renderedPaths.removeLast()
                        }
                        pathIsRendered = false
                        startPoint = LatLng(route.points.last().latitude,route.points.last().longitude)
                        endPoint = LatLng(route.points.last().latitude,route.points.last().longitude)
                    }
                    else if(route.points.size == 1){
                        route.points.removeLast()
                        isFirstPoint = true
                        startPoint = LatLng(endPoint.latitude,endPoint.longitude)
                        if(renderedPaths.size > 0) renderedPaths.removeLast()
                    }
                }) {
                    Text(text = "Eliminar punto ")
                }
            }

        }

    }

}


@Composable
private fun AskLocationPermissions(content: @Composable () -> Unit) {
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                // Precise location access granted.
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                        permissions.getOrDefault(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            false
                        ) -> {
                }
            }
        }


    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        SideEffect {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    } else {
        content()
    }
}