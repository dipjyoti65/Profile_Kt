package com.example.profile

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.profile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // It contain references  to the views defined in the activity_login.xml layout
    var binding: ActivityLoginBinding? = null
    var databaseHelper: DBhelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflates the layout associated with LoginActivity --> Converting the XML layout file into actual View objects that can be displayed on the screen.
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // returns the root View of the associated layout --> In this case it refers to the layout of LoginActivity
        setContentView(binding!!.root)

        databaseHelper = DBhelper(this)

        //Setting listener on Sign In button
        binding!!.signIn.setOnClickListener {
            UserEmail = binding!!.loginEmail.text.toString()
            val password = binding!!.loginPassword.text.toString()

            //checking if all fields are filled up by user not not
            if (UserEmail == "" || password == "") Toast.makeText(
                this@LoginActivity,
                "All fields are mandatory",
                Toast.LENGTH_SHORT
            ).show() else {
                val checkCredentials = databaseHelper!!.checkEmailPassword(UserEmail!!, password)

                //Checking login credentials in database
                if (checkCredentials) {
                    Toast.makeText(this@LoginActivity, " Login Successfully ", Toast.LENGTH_SHORT)
                        .show()
                    val userData = databaseHelper!!.getUserData(UserEmail!!)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("userData", userData)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        //If not User is not registered , redirected to Registration Page
        binding!!.backToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmField
        var UserEmail: String? = null
    }

    override fun onResume() {
        super.onResume()
        clearInputFields()
    }

    private fun clearInputFields() {
        val loginEmail: EditText = findViewById(R.id.loginEmail)
        val loginPassword: EditText = findViewById(R.id.loginPassword)

        loginEmail.setText("")
        loginPassword.setText("")
    }
}