package com.example.fung_p2hacks.LandingScreen

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fung_p2hacks.API.FlymeAPI
import com.example.fung_p2hacks.API.Model.LogInUser
import com.example.fung_p2hacks.BuildConfig
import com.example.fung_p2hacks.R
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LandingScreenViewModel: ViewModel() {
    private val _isLoginSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _showLoginFields: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess.asStateFlow()
    val showLoginFields = _showLoginFields.asStateFlow()

    private val _logInUserId: MutableStateFlow<String> = MutableStateFlow("")
    private val _logInPassword: MutableStateFlow<String> = MutableStateFlow("")
    val logInUserId = _logInUserId.asStateFlow()
    val logInPassword = _logInPassword.asStateFlow()

    private val _signUpUserName: MutableStateFlow<String> = MutableStateFlow("")
    private val _signUpUserId: MutableStateFlow<String> = MutableStateFlow("")
    private val _signUpPassword: MutableStateFlow<String> = MutableStateFlow("")
    private val _signUpPasswordConfirm: MutableStateFlow<String> = MutableStateFlow("")
    val signUpUserName = _signUpUserName.asStateFlow()
    val signUpUserId = _signUpUserId.asStateFlow()
    val signUpPassword = _signUpPassword.asStateFlow()
    val signUpPasswordConfirm = _signUpPasswordConfirm.asStateFlow()

    fun invertShowLogin() {
        _showLoginFields.value = _showLoginFields.value.not()
    }

    fun onLogInUserIdChange(newId: String) {
        _logInUserId.value = newId
    }

    fun onlogInPasswordChange(newPAS: String) {
        _logInPassword.value = newPAS
    }

    fun onSignUpUserNameChange(newName: String) {
        _signUpUserName.value = newName
    }

    fun onSignUpUserIdChange(newId: String) {
        _signUpUserId.value = newId
    }

    fun onSignUpPasswordChange(newPAS: String) {
        _signUpPassword.value = newPAS
    }

    fun onSignUpPasswordConfirmChange(newPAS: String) {
        _signUpPasswordConfirm.value = newPAS
    }

    fun tryLogIn(context: Context) {
        var token: String? = null
        val userId = this._logInUserId.value
        val loginPassword = this._logInPassword.value

        viewModelScope.launch {
            val api = FlymeAPI.Instance()
            val logInUser = LogInUser(userId, loginPassword)
            try {
                val res = api.userLogIn(logInUser)
                if (res.code() == 200) {
                    println(res.raw())
                    println("Credential: ${res.body()!!.token}")
                    token = res.body()!!.token

                    val idPref = context.getSharedPreferences(context.getString(R.string.user_id), Context.MODE_PRIVATE)
                    val pasPref = context.getSharedPreferences(context.getString(R.string.user_password), Context.MODE_PRIVATE)
                    val tokenPref = context.getSharedPreferences(context.getString(R.string.api_access_token), Context.MODE_PRIVATE)

                    with(idPref.edit()) {
                        putString("user_id", userId)
                        apply()
                    }

                    with(pasPref.edit()) {
                        putString("user_password", loginPassword)
                        apply()
                    }

                    with(tokenPref.edit()) {
                        putString("current_token", token)
                        apply()
                    }

                    this@LandingScreenViewModel._isLoginSuccess.value = true
                } else {
                    println("Error!: ${res.raw()}")
                }
            } catch (e: Exception) {
                println("Error!: ${e}")
            }
        }
    }
}