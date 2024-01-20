package com.example.profile

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhelper(context: Context?) : SQLiteOpenHelper(context, "User_data.db", null, 1) {

    override fun onCreate(Db: SQLiteDatabase) {
        Db.execSQL("create table userData(email TEXT primary key, password TEXT, firstName TEXT , lastName TEXT)")
    }

    override fun onUpgrade(Db: SQLiteDatabase, i: Int, i1: Int) {
        Db.execSQL("drop table if exists userData")
        onCreate(Db)
    }

    // Method for data insertion to database
    fun insertData(
        email: String?,
        password: String?,
        firstName: String?,
        lastName: String?
    ): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("firstName", firstName)
        cv.put("lastName", lastName)
        cv.put("email", email)
        cv.put("password", password)
        val result = db.insert("userData", null, cv)

        //inset method return in which row data is inserted in the database, if insertion fails it return -1
        return if (result == -1L) {
            false
        } else {
            true
        }
    }

    // Method to check if user email already exist in database
    fun checkEmail(email: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery("Select * from userData where email = ?", arrayOf(email))
        return if (cursor.count > 0) {
            true
        } else {
            false
        }
    }

    // Login credentials checking
    fun checkEmailPassword(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "Select * from userData where email= ? and password = ?",
            arrayOf(email, password)
        )
        return if (cursor.count > 0) {
            true
        } else {
            false
        }
    }

    // Method to get user data to show in profile
    fun getUserData(UserEmail: String): UserData? {
        val db = this.readableDatabase
        val query = "Select * from userData where userData.email = ?"
        val cursor = db.rawQuery(query, arrayOf(UserEmail))
        var userData: UserData? = null
        val dataholder = ArrayList<UserData>()
        try {
            if (cursor.moveToFirst()) {
                val emailIndex = cursor.getColumnIndex("email")
                val firstNameIndex = cursor.getColumnIndex("firstName")
                val lastNameIndex = cursor.getColumnIndex("lastName")

                // Check if columns exist in the cursor
                if (emailIndex != -1 && firstNameIndex != -1 && lastNameIndex != -1) {
                    val userEmail = cursor.getString(emailIndex)
                    val firstName = cursor.getString(firstNameIndex)
                    val lastName = cursor.getString(lastNameIndex)
                    userData = UserData(firstName, lastName, userEmail)
                }
            }
        } finally {
            cursor.close()
        }
        return userData
    }

    companion object {
        private const val dbname = "User_data.db"
    }
}
