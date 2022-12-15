package com.example.fung_p2hacks.LandingScreen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fung_p2hacks.R
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import com.example.fung_p2hacks.ui.theme.PrimaryWhite

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
                    LandingRootComposable(showLogInFields)
                }
            }
        }
    }
}

@Composable
fun LandingRootComposable(showLogIn: MutableState<Boolean>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlymeLogoComposable()

        Spacer(Modifier.padding(5.dp))

        if (showLogIn.value) LogInFields()
        else SignUpFields()
    }
}

@Composable
fun FlymeLogoComposable() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.padding(top = 50.dp, bottom = 50.dp, start = 50.dp, end = 50.dp)
    )
}

@Composable
fun SignUpFields() {
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
            label = { Text("ユーザー名") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            )
        )

        TextField(
            value = signUpUserID,
            onValueChange = { signUpUserID = it },
            label = { Text("ユーザーID") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PrimaryWhite,
                unfocusedIndicatorColor = PrimaryWhite
            )
        )

        TextField(
            value = signUpPassword,
            onValueChange = { signUpPassword = it },
            label = { Text("パスワード") },
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
            )
        )

        TextField(
            value = signUpPasswordConfirm,
            onValueChange = { signUpPasswordConfirm = it },
            label = { Text("パスワードを再入力") },
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
            )
        )
    }
}

@Composable
fun LogInFields() {

}

@Preview(showBackground = true)
@Composable
fun LandingPreView() {
    FuNG_p2hacksTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ){
            val showLogInFields = remember { mutableStateOf(false) }
            LandingRootComposable(showLogInFields)
        }
    }
}