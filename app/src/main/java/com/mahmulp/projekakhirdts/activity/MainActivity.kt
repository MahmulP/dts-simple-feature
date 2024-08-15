package com.mahmulp.projekakhirdts.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahmulp.projekakhirdts.R
import com.mahmulp.projekakhirdts.databinding.ActivityLoginBinding
import com.mahmulp.projekakhirdts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnList.setOnClickListener {
            startActivity(Intent(this, ListDataActivity::class.java))
        }

        binding.btnInput.setOnClickListener {
            startActivity(Intent(this, InputDataActivity::class.java))
        }

        binding.btnInformation.setOnClickListener {
            startActivity(Intent(this, InformationActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}