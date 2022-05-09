package com.example.myapplicationautho.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myapplicationautho.R
import com.example.myapplicationautho.fragment.DonatorFragment
import com.example.myapplicationautho.fragment.EmergencyFragment
import com.example.myapplicationautho.fragment.NeederFragment
import com.example.myapplicationautho.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    companion object {
        var user = User()
    }

    private var drawer:DrawerLayout? =null
    private var navingationview:NavigationView? =null
    private var framelayout:FrameLayout? =null
    private var bottomnavigation:BottomNavigationView? =null
    private var toolBar:Toolbar? =null
    private var Authe: FirebaseAuth? =  FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private val CurrentUserRefDocRef: DocumentReference get() = firestore!!.document("Users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")
    private lateinit var tvUsername :TextView


    val bloodTypes = arrayOf("All", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+")
    val cities = arrayOf("All", "Riyadh", "Jeddah", "Yanbu")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectview()
        ClickOnItemInDrawerNav()
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var toggle = ActionBarDrawerToggle(this,drawer, R.string.open, R.string.close)
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        clickbottomnav()
        getUserInformation() { user ->
            tvUsername.text = user.name
            MainActivity.user = user
            preparenavigation(DonatorFragment(),"Donator")
        }
    }


    private fun getUserInformation(onComplete:(User)->Unit) {
        CurrentUserRefDocRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)!!
            user.key = it.id
            onComplete(user)
        }
    }
    private fun ClickOnItemInDrawerNav() {
        navingationview?.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.logout ->{
                    Authe?.signOut()
                    var x : Intent = Intent(this, Login::class.java)
                    startActivity(x)
                    finish()
                    true
                }
                R.id.Howtheappwork ->{
                    drawer?.closeDrawer(GravityCompat.START)
                    true
                }else -> true
            }
        }
    }

    private fun preparenavigation(frag: Fragment, tag:String) {
        val fragment: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.framelayout,frag,tag)
        fragment.addToBackStack(tag)
        fragment.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragment.commit()
    }
    private fun clickbottomnav() {
        bottomnavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.donator ->{
                    preparenavigation(DonatorFragment(),"Donator")

                }
                R.id.needer ->{
                    preparenavigation(NeederFragment(),"Needer")
                }
                R.id.emergency -> {
                    preparenavigation(EmergencyFragment(),"Emergency")
                }

            }
            true
        }

    }
    private fun connectview() {
        drawer= findViewById(R.id.drawer1)
        navingationview= findViewById(R.id.navigation_view)
        tvUsername = navingationview!!.getHeaderView(0).findViewById<TextView>(R.id.tvUsername)
        framelayout= findViewById(R.id.framelayout)
        bottomnavigation= findViewById(R.id.bottom_nav)
        toolBar= findViewById(R.id.Toolbar)
    }

    //============Override Methods==============//

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_filter, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            android.R.id.home -> {
                drawer?.openDrawer(GravityCompat.START)
                true
            }

            R.id.blood -> {

                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setTitle("Blood type")
                alertDialog.setSingleChoiceItems(
                    bloodTypes, 0
                ) { dialog, item ->
                    val donatorFragment = supportFragmentManager.findFragmentByTag("Donator") as? DonatorFragment
                    val neederFragment = supportFragmentManager.findFragmentByTag("Needer") as? NeederFragment
                    val emergencyFragment = supportFragmentManager.findFragmentByTag("Emergency") as? EmergencyFragment

                    if(donatorFragment?.isVisible == true){
                        donatorFragment.filterByBloodType(bloodTypes[item])
                    }

                    if(neederFragment?.isVisible == true){
                        neederFragment.filterByBloodType(bloodTypes[item])
                    }

                    if(emergencyFragment?.isVisible == true){
                        emergencyFragment.filterByBloodType(bloodTypes[item])
                    }
                }

                alertDialog.create().show()

                true
            }

            R.id.city -> {
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setTitle("City")
                alertDialog.setSingleChoiceItems(
                    cities, 0
                ) { dialog, item ->

                    val donatorFragment = supportFragmentManager.findFragmentByTag("Donator") as? DonatorFragment
                    val neederFragment = supportFragmentManager.findFragmentByTag("Needer") as? NeederFragment
                    val emergencyFragment = supportFragmentManager.findFragmentByTag("Emergency") as? EmergencyFragment

                    if(donatorFragment?.isVisible == true){
                        donatorFragment.filterByCity(cities[item])
                    }

                    if(neederFragment?.isVisible == true){
                        neederFragment.filterByCity(cities[item])
                    }

                    if(emergencyFragment?.isVisible == true){
                        emergencyFragment.filterByCity(cities[item])
                    }
                }

                alertDialog.create().show()

                true
            }

            else -> true

        }
    }

}