package com.example.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.profile.LoginActivity
import com.example.profile.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    // It contain references  to the views defined in the activity_register.xml layout
    var binding: ActivityRegisterBinding? = null
    var databaseHelper: DBhelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflates the layout associated with RegisterActivity --> Converting the XML layout file into actual View objects that can be displayed on the screen.
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        // returns the root View of the associated layout --> In this case it refers to the layout of RegisterActivity
        setContentView(binding!!.root)

        databaseHelper = DBhelper(this)
        // Setting listener on Register Button
        binding!!.buttonRegister.setOnClickListener {
            val email = binding!!.registerEmail.text.toString()
            val password = binding!!.registerPassword.text.toString()
            val confirmPassword = binding!!.confirmPassword.text.toString()
            val firstName = binding!!.firstName.text.toString()
            val lastName = binding!!.lastName.text.toString()
            if (email == "" || password == "" || confirmPassword == "" || firstName == "" || lastName == "") Toast.makeText(
                this@RegisterActivity,
                "All fields are mandatory",
                Toast.LENGTH_SHORT
            ).show() else {
                if (password == confirmPassword) {
                    val checkUserEmail = databaseHelper!!.checkEmail(email)
                    if (!checkUserEmail) {
                        val insert =
                            databaseHelper!!.insertData(email, password, firstName, lastName)
                        if (insert) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registered Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "User email already exists , Please login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password not matched",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Setting Listener to go back to login page from Register page
        binding!!.backToLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}