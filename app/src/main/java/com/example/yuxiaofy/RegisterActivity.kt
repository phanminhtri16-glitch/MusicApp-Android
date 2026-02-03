package com.example.yuxiaofy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        btnRegister.setOnClickListener {
            // Logic đăng ký (giả lập)
            Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()
            // Đăng ký xong thì vào thẳng Home
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        tvBackToLogin.setOnClickListener {
            finish() // Quay lại trang Login
        }
    }
}