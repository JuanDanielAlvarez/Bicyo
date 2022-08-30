package com.bicyo.bicyo.ui.screens


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import coil.compose.rememberImagePainter
import com.bicyo.bicyo.R
import com.bicyo.bicyo.data.entities.User

import com.bicyo.bicyo.ui.components.ImageFromUrl
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Profile(navController: NavHostController, userId: Int?) {
    val currentUserId = 1
    val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
    val name = remember {
        mutableStateOf(user.name)
    }
    val email = remember {
        mutableStateOf(user.email)
    }
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    val description = remember {
        mutableStateOf(user.description)
    }

    val imageUri = rememberSaveable { mutableStateOf(user.profilePictureUrl) }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.spinner
        else
            imageUri.value
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = "Mi perfil",
        style = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
            }

            Text(user.name)
            Text(user.email)
            Text(user.description)

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = { navController.navigate("explore/${currentUserId}") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF000000),
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Editar perfil",
                    fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text("Rutas publicadas ".plus(user.publishedRoutes))
            Text("Grupos ".plus(user.numberOfGroups))

        }
    }

}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Profile(navController, 1)
    }
}