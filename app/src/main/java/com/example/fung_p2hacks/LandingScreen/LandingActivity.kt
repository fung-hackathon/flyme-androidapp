package com.example.fung_p2hacks.LandingScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fung_p2hacks.HomeScreen.HomeActivity
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.ui.theme.Gray
import com.example.fung_p2hacks.ui.theme.PrimaryWhite
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class LandingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuNG_p2hacksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    val showLogInFields = remember { mutableStateOf(false) }
                    val landingViewModel: LandingScreenViewModel = viewModel()
                    LandingRootComposable(landingViewModel, showLogInFields)
                }
            }
        }
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LandingRootComposable(
    viewModel: LandingScreenViewModel,
    showLogIn: MutableState<Boolean>
) {
    val permissionState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET
            )
        )

    if (permissionState.permissionRequested || permissionState.shouldShowRationale) {
        permissionState.launchMultiplePermissionRequest()
    }
    else {
        println("Permission Granted")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlymeLogoComposable()
        Spacer(Modifier.padding(5.dp))
        if (showLogIn.value) {
            LogInFields(
                viewModel,
                onSignUpNavClicked = { showLogIn.value = false }
            )
        } else SignUpFields(viewModel, onLogInNavClicked = { showLogIn.value = true })
    }
}

@Composable
fun FlymeLogoComposable() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.padding(top = 30.dp, bottom = 30.dp, start = 50.dp, end = 50.dp)
    )
}

@Composable
fun SignUpFields(
    viewModel: LandingScreenViewModel,
    onLogInNavClicked: () -> Unit
) {
    var signUpUserName by remember { mutableStateOf("") }
    var signUpUserID by remember { mutableStateOf("") }
    var signUpPassword by remember { mutableStateOf("") }
    var signUpPasswordConfirm by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = signUpUserName,
            onValueChange = { signUpUserName = it },
            placeholder = { Text("???????????????", color = Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        )

        TextField(
            value = signUpUserID,
            onValueChange = { signUpUserID = it },
            placeholder = { Text("????????????ID", color = Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        )

        TextField(
            value = signUpPassword,
            onValueChange = { signUpPassword = it },
            placeholder = { Text("???????????????", color = Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        )

        TextField(
            value = signUpPasswordConfirm,
            onValueChange = { signUpPasswordConfirm = it },
            placeholder = { Text("???????????????????????????", color = Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        )

        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("??????")
                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(270F)
                        .size(15.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Button(
            onClick = onLogInNavClicked,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("????????????????????????")
                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(270F)
                        .size(15.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun LogInFields(
    viewModel: LandingScreenViewModel,
    onSignUpNavClicked: () -> Unit
) {
    var logInUserId by remember { mutableStateOf("") }
    var logInPassword by remember { mutableStateOf("") }
    val thisContext = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = logInUserId,
            onValueChange = { logInUserId = it },
            placeholder = { Text("????????????ID", color = Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        )

        TextField(
            value = logInPassword,
            onValueChange = { logInPassword = it },
            placeholder = { Text("???????????????", color = Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
        )

        Button(
            onClick = {
                viewModel.tryLogIn(logInUserId, logInPassword, thisContext)
                thisContext.startActivity(
                    Intent(thisContext, HomeActivity::class.java)
                )
                (thisContext as Activity).finish()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("????????????")
                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(270F)
                        .size(15.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Button(
            onClick = onSignUpNavClicked,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("????????????????????????")
                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(270F)
                        .size(15.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LandingSignUpView() {
//    FuNG_p2hacksTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.background
//        ){
//            val showLogInFields = remember { mutableStateOf(false) }
//            LandingRootComposable(showLogInFields)
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LandingLogInView() {
//    FuNG_p2hacksTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.background
//        ){
//            val showLogInFields = remember { mutableStateOf(true) }
//            LandingRootComposable(showLogInFields)
//        }
//    }
//}