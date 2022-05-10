package com.pim.feature.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pim.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, HomeFragment.newInstance())
        }.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) =
            Intent(context, HomeActivity::class.java)
    }
}