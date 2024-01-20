package com.example.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var firstName: TextView? = null
    var lastName: TextView? = null
    var email: TextView? = null
    private lateinit var logout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Retrieve user data from Intent extras
        val intent = intent
        val userData = intent.getSerializableExtra("userData") as UserData?
        if (userData != null) {
            // Use safe cast and null check for TextView assignments
            firstName = findViewById<TextView>(R.id.firstNameTextView)?.apply { text = userData.firstName }
            lastName = findViewById<TextView>(R.id.lastNameTextView)?.apply { text = userData.lastName }
            email = findViewById<TextView>(R.id.emailTextView)?.apply { text = userData.email }
        }  else {
            Toast.makeText(this, "User data not Available", Toast.LENGTH_SHORT).show()
        }

        logout = findViewById(R.id.logoutButton)
        logout.setOnClickListener {
            // databaseHelper.logoutUser()

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.removeExtra("email")
            intent.removeExtra("password")
            startActivity(intent)
            finish()
        }

    }
}