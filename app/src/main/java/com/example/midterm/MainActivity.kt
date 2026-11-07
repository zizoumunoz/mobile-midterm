package com.example.midterm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "com.example.midterm.USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getStringExtra(EXTRA_USERNAME)?.let { UserDatabase.getUser(it) }
        if (user == null) {
            logOut()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.main).applySystemBarPadding()

        findViewById<TextView>(R.id.title).text = getString(R.string.todo_title, user.username)
        findViewById<Button>(R.id.logoutButton).setOnClickListener { logOut() }
        findViewById<ImageButton>(R.id.profileButton).setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            profileIntent.putExtra(EXTRA_USERNAME, user.username)
            startActivity(profileIntent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val taskInput = findViewById<EditText>(R.id.taskInput)
        val adapter = ItemAdapter(user.todos)
        recyclerView.adapter = adapter

        fun addTask() {
            val task = taskInput.text.toString().trim()
            if (task.isEmpty()) return
            if (adapter.addItem(task)) {
                recyclerView.scrollToPosition(adapter.itemCount - 1)
                taskInput.text.clear()
            } else {
                Toast.makeText(this, R.string.todo_duplicate, Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.addButton).setOnClickListener { addTask() }
        taskInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTask()
                true
            } else {
                false
            }
        }
    }

    private fun logOut() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
