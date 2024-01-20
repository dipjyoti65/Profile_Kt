package com.example.profile

import java.io.Serializable

class UserData(// Getter methods
    @JvmField val firstName: String, @JvmField val lastName: String, @JvmField val email: String) : Serializable
