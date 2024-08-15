package com.mahmulp.projekakhirdts.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahmulp.projekakhirdts.R
import com.mahmulp.projekakhirdts.data.DatabaseHelper
import com.mahmulp.projekakhirdts.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.tvNoacc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edUsername.text.toString()
            val password = binding.edPassword.text.toString()
            val checkUser = databaseHelper.checkUser(username)
            if (!checkUser) {
                Toast.makeText(this, "Data tidak ada", Toast.LENGTH_SHORT).show()
            } else {
                val checkUserPassword = databaseHelper.checkUserPassword(username, password)
                if (checkUserPassword) {
                    Toast.makeText(this, "Data benar, berhasil login", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Password salah!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}