package com.example.midterm

object UserDatabase {
    private val users: Map<String, User> = listOf(
        User("admin", "admin123"),
        User("zach", "zach"),
        User("john", "john"),
        User("paul", "paul"),
        User("mary", "mary")
    ).associateBy { it.username }

    // Users who have logged in since the app started, in login order.
    private val loggedInUsernames = linkedSetOf<String>()

    fun authenticate(username: String, password: String): User? {
        val user = users[username]
        if (user == null || user.password != password) return null
        loggedInUsernames.add(user.username)
        return user
    }

    fun getUser(username: String): User? = users[username]

    fun allUsers(): List<User> = users.values.toList()

    fun loggedInUsers(): List<User> = loggedInUsernames.mapNotNull { users[it] }
}
