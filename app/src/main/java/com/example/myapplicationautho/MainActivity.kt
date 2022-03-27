package com.example.myapplicationautho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.rpc.context.AttributeContext

class MainActivity : AppCompatActivity() {
    private var drawer:DrawerLayout? =null
    private var navingationview:NavigationView? =null
    private var framelayout:FrameLayout? =null
    private var bottomnavigation:BottomNavigationView? =null
    private var toolBar:Toolbar? =null
    private var Authe: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectview()
        Authe= FirebaseAuth.getInstance()
        ClickOnItemInDrawerNav()
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer?.addDrawerListener(toggle)
        toggle.syncState()ااا
    }
    private fun ClickOnItemInDrawerNav() {
        navingationview?.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    drawer?.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout ->{
                    Authe?.signOut()
                    var x : Intent = Intent(this,Login::class.java)
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                drawer?.openDrawer(GravityCompat.START)
                true
            }
            else -> true
        }
    }
    private fun connectview() {
        drawer= findViewById(R.id.drawer1)
        navingationview= findViewById(R.id.navigation_view)
        framelayout= findViewById(R.id.framelayout)
        bottomnavigation= findViewById(R.id.bottom_nav)
        toolBar= findViewById(R.id.Toolbar)
    }
}