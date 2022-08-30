package com.bicyo.bicyo.ui.screens


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.bicyo.bicyo.R
import com.bicyo.bicyo.data.entities.User
import com.bicyo.bicyo.ui.components.ImageFromUrl
import com.bicyo.bicyo.ui.theme.BicyoTheme


@Composable
fun EditProfile(navController: NavHostController, userId: Int?) {
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
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        imageUri.value.ifEmpty { R.drawable.spinner }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = stringResource(id = R.string.my_profile_edit),
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
        Spacer(modifier = Modifier.height(30.dp))
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
                        .wrapContentSize(),
                )
            }
            Button(onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF000000),
                    contentColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(id = R.string.change_photo),
                    fontSize = 16.sp)
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(225.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = stringResource(id = R.string.name))
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.user_icon_desc),
                    Modifier.size(30.dp),
                    tint = Color.Gray)
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    placeholder = { Text(text = stringResource(id = R.string.name_textField)) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.1f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFFFF)
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.email))
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.user_icon_desc),
                    Modifier.size(30.dp),
                    tint = Color.Gray)
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = { Text(text = stringResource(id = R.string.email_textField)) },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFFFF)
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.password))
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(painter = painterResource(id = R.drawable.candado_cerrado),
                    contentDescription = stringResource(id = R.string.password_icon_desc),
                    Modifier.size(30.dp),
                    tint = Color.Gray)
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = { Text(text = stringResource(id = R.string.password_textField)) },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFFFF)
                    )
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = stringResource(id = R.string.about))
            Row(verticalAlignment = Alignment.Bottom) {

                TextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = { Text(text = "") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFFFF)
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { navController.navigate("profile/${currentUserId}") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF000000),
                contentColor = Color(0xFFFFFFFF)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = stringResource(id = R.string.save),
                fontSize = 18.sp)
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun EditProfilePreview() {
    val navController = rememberNavController()
    BicyoTheme {
        EditProfile(navController, 1)
    }
}