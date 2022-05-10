package com.pim.feature.login

import android.content.SharedPreferences
import android.util.Log
import com.pim.data.InfoContainer
import com.pim.data.model.credential.Credentials
import com.pim.data.repo.CredentialRepository
import com.pim.feature.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialRepository: CredentialRepository,
    private val preferences: SharedPreferences
) : AppViewModel() {

    fun login(username: String?, pass: String?, clientCode: String?): Completable {
        return credentialRepository.login(username, pass, clientCode).doOnSuccess {
            Log.d("test", it.toString())

            onSuccessfulLogin(username, pass, clientCode, it)

        }.ignoreElement()
    }

    private fun onSuccessfulLogin(
        username: String?,
        password: String?,
        clientCode: String?,
        credentials: Credentials
    ) {
        InfoContainer.updateToken(credentials.records[0].token)
        InfoContainer.updateUserAndPass(username, password, clientCode)
        preferences.edit().apply {
            putString("pref_username", username)
            putString("pref_password", password)
            putString("pref_client_code", clientCode)
        }.apply()
    }

    fun bakeToLogin() {
        InfoContainer.updateToken(null)
        preferences.edit().apply {
            putString("pref_username", null)
            putString("pref_password", null)
            putString("pref_client_code", null)
        }.apply()
    }
}