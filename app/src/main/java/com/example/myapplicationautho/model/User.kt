package com.example.myapplicationautho.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName
import java.io.Serializable

class User: Serializable {

    @get:Exclude
    @set:Exclude
    var key: String = ""

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = ""

    @get:PropertyName("email")
    @set:PropertyName("email")
    var email: String = ""

    @get:PropertyName("phone")
    @set:PropertyName("phone")
    var phone: String = ""

    @get:PropertyName("is_hospital")
    @set:PropertyName("is_hospital")
    var isHospital: Boolean = false


}