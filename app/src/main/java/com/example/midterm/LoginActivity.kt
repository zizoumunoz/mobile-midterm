package com.example.midterm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        findViewById<View>(R.id.login).applySystemBarPadding()

        val hintLines = UserDatabase.allUsers().map { "${it.username} / ${it.password}" }
        findViewById<TextView>(R.id.loginHint).text =
            (listOf(getString(R.string.login_hint_title)) + hintLines).joinToString("\n")

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val user = username.text.toString().trim()
            val pass = password.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
            } else {
                val loggedIn = UserDatabase.authenticate(user, pass)
                if (loggedIn == null) {
                    Toast.makeText(this, R.string.login_invalid, Toast.LENGTH_SHORT).show()
                    password.text.clear()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_USERNAME, loggedIn.username)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
