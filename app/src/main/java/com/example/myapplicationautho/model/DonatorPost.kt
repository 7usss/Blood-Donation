package com.example.myapplicationautho.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.io.Serializable

class DonatorPost: Serializable {

    @get:Exclude
    @set:Exclude
    var key: String = ""

    @get:PropertyName("blood_type")
    @set:PropertyName("blood_type")
    var bloodType: String = ""

    @get:PropertyName("city")
    @set:PropertyName("city")
    var city: String = ""

    @get:PropertyName("phone")
    @set:PropertyName("phone")
    var phone: String = ""

    @get:PropertyName("age")
    @set:PropertyName("age")
    var age: String = ""

    @get:PropertyName("patient_number")
    @set:PropertyName("patient_number")
    var patientNumber: String = ""

    @get:PropertyName("notes")
    @set:PropertyName("notes")
    var notes: String = ""

    @get:PropertyName("user_key")
    @set:PropertyName("user_key")
    var userKey: String = ""

    @get:Exclude
    @set:Exclude
    var user: User = User()

}