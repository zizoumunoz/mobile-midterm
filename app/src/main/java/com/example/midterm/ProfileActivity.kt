package com.example.midterm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getStringExtra(MainActivity.EXTRA_USERNAME)?.let { UserDatabase.getUser(it) }
        if (user == null) {
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        findViewById<View>(R.id.profile).applySystemBarPadding()

        findViewById<TextView>(R.id.profileName).text = user.username
        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }

        val switchUserList = findViewById<LinearLayout>(R.id.switchUserList)
        val otherUsers = UserDatabase.loggedInUsers().filter { it.username != user.username }
        if (otherUsers.isEmpty()) {
            findViewById<TextView>(R.id.noOtherUsers).visibility = View.VISIBLE
        }
        for (other in otherUsers) {
            val button = Button(this)
            button.text = other.username
            button.setOnClickListener { switchTo(other) }
            switchUserList.addView(button)
        }

        findViewById<Button>(R.id.loginAnotherButton).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // Clear the current user's screens so Back doesn't return to them.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun switchTo(other: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_USERNAME, other.username)
        // Clear the old user's screens so Back doesn't return to them.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
