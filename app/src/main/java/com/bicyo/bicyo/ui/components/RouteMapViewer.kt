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
fun ColumnScope.RouteMapViewer(route: Route) {
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
        position = CameraPosition.fromLatLngZoom(
            if (route.points.size >0) route.points.get(0) else LatLng(-0.21, -78.49),
            11f)
    }

    var renderedPaths by remember { mutableStateOf(mutableListOf(mutableListOf<LatLng>())) }
    var pathIsRendered by remember { mutableStateOf(false) }

    if (!pathIsRendered) {
        for( i in 0 until route.points.size){
            if(i+1 < route.points.size){
                object : TaskRequestDirections() {
                    override fun onPostExecute(latLngList: MutableList<LatLng>) {
                        renderedPaths.add(renderedPaths.indexOfLast { true },latLngList)
                    }
                }.execute(
                    route.points[i],
                    route.points[i+1]
                )
            }
        }
        pathIsRendered = true
    }


    AskLocationPermissions {

        Box(Modifier.height(600.dp)) {
            GoogleMap(
                properties = mapProperties,
                uiSettings = mapUiSettings,
                cameraPositionState = cameraPositionState,
            ) {

                for (point in route.points) {
                    Marker(
                        state = MarkerState(position = point)
                    )
                }

                Polyline(
                    points = renderedPaths.flatten()
                )

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