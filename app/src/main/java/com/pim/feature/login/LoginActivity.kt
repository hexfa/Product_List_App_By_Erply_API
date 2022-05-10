package com.pim.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pim.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, LoginFragment.newInstance())
        }.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) =
            Intent(context, LoginActivity::class.java)
    }
}