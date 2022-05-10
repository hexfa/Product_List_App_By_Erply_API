package com.pim.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.pim.data.InfoContainer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PIMApplication : Application() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var mContext: Context? = null
    override fun onCreate() {
        super.onCreate()
        mContext = this
        InfoContainer.updateUserAndPass(
            sharedPreferences.getString("pref_username", null),
            sharedPreferences.getString("pref_password", null),
            sharedPreferences.getString("pref_client_code", null)
        )

    }

}