package com.bicyo.bicyo.ui.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.ui.theme.BicyoTheme
import com.bicyo.bicyo.R
import com.bicyo.bicyo.storage.ExternalFileManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.bicyo.bicyo.data.daos.UserDAO
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.bicyo.bicyo.storage.EXTRA_LOGIN
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Login(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val currentUserId = 1
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }

        val passwordVisible = remember {
            mutableStateOf(false)
        }
        Text(
            text = stringResource(id = R.string.app),
            style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = stringResource(id = R.string.login),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = stringResource(id = R.string.email))
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.user_icon_desc),
                    Modifier.size(40.dp),
                    tint = Color.Gray
                )
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    placeholder = { Text(text = stringResource(id = R.string.email_textField)) },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFFFF)
                    ),
                    trailingIcon = {
                        if (email.value=="")
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.password))
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(
                    painter = painterResource(id = R.drawable.candado_cerrado),
                    contentDescription = stringResource(id = R.string.password_icon_desc),
                    Modifier.size(40.dp),
                    tint = Color.Gray
                )
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    placeholder = { Text(text = stringResource(id = R.string.password_textField)) },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFFFFFFF)
                    ),
                    trailingIcon = {
                        if (password.value=="")
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                    }
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginWithEmailButton(navController,email.value,password.value,currentUserId)
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.wrapContentSize(Alignment.Center)) {
            Text(text = stringResource(id = R.string.no_count))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier
                    .clickable(true) {
                        navController.navigate("signup")
                    },
                text = stringResource(id = R.string.singup_2),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(72.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(Color(0xFF000000))
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_social_network),
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoginWithGoogleButton(navController, currentUserId)
        }
    }
}
private fun validatePassword(password: String): String {
    if (password.isEmpty()) {
        return "La clave es obligatoria"
    }
    if (password.length < 8) {
        return "La clave debe tener al menos 8 caracteres"
    }
    return ""
}

private fun validateEmail(email: String): String {
    if (email.isEmpty()) {
        return "El email es obligatorio"
    }
    val pattern = Patterns.EMAIL_ADDRESS
    if (!pattern.matcher(email).matches()) {
        return "El email ingresado no es válido"
    }
    return ""
}

fun AutenticarUsuario(
    context: Context,
    email: String,
    password: String,
    onsSuccess: () -> Unit
) {
    val auth = Firebase.auth
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                Log.d(EXTRA_LOGIN, "signInWithEmail:success")
                onsSuccess()
            } else {
                Log.w(EXTRA_LOGIN, "signInWithEmail:failure", task.exception)
                Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
}

@Composable
private fun LoginWithEmailButton(
    navController: NavHostController,
    email: String,
    password: String,
    currentUserId: Int
) {
    val context = LocalContext.current
    var authenticated by remember { mutableStateOf(false) }

    Button(
        onClick = {

            navController.navigate("explore/${currentUserId}")

            if (validateEmail(email) == "" && validatePassword(password) == "") {
                AutenticarUsuario(
                    context,
                    email,
                    password,
                    { navController.navigate("explore/${currentUserId}")})
            }

        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF000000),
            contentColor = Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 20.sp
        )
    }
}

@Composable
private fun LoginWithGoogleButton(navController: NavHostController, currentUserId: Int) {
    val context = LocalContext.current

    //Se define la acción después de obtener el resultado de iniciar sesión
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            //Log.d(TAG,result.resultCode.toString())
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    val gsa = task.getResult(ApiException::class.java)
                    if (gsa != null) {
                        val user = User(
                            1,
                            "${gsa.email}",
                            "${gsa.displayName}",
                            "",
                            "${gsa.photoUrl}",
                            0,
                            0,
                            listOf(),
                            listOf()
                        )
                        UserDAO().save(user)
                        Toast.makeText(context, "Bienvenido ${gsa.displayName} ", Toast.LENGTH_LONG)
                            .show()
                        navController.navigate("explore/${currentUserId}")
                    } else {
                        //Toast.makeText(context, "ERROR AL INICIAR SESIÓN", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                //Toast.makeText(context, "ERROR AL INICIAR SESIÓN", Toast.LENGTH_LONG).show()
            }
        }

    //Se define el usuario de google
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    val googleClient = GoogleSignIn.getClient(context, gso);


    //Boton de iniciar sesión con google
    Button(
        onClick = {
            startForResult.launch(googleClient.signInIntent)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFF000000)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google1),
            contentDescription = "",
            Modifier.height(26.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.login_google),
            fontSize = 20.sp
        )
    }
}

@Composable
private fun leerDatosDePreferencias(
    setEmail: (newEmail: String) -> Unit,
    setPassword: (newPassword: String) -> Unit
) {
    val context = LocalContext.current
    val manejadorArchivo = ExternalFileManager(context as Activity)
    val listadoLeido = manejadorArchivo.ReadInformation()

    setEmail(listadoLeido.first)
    setPassword(listadoLeido.second)
    //guardarDatosEnPreferencias(context, setEmail, setPassword)
}

private fun guardarDatosEnPreferencias(
    context: Context,
    email: TextFieldValue,
    password: TextFieldValue
) {
    val email = email.text
    val password = password.text
    val listadoAGrabar: Pair<String, String>
    listadoAGrabar = email to password
    val manejadorArchivo = ExternalFileManager(context as Activity)
    manejadorArchivo.SaveInformation(listadoAGrabar)
}

@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Login(navController)
    }
}