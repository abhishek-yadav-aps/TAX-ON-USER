package com.taxonteam.taxon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.taxonteam.taxon.ui.dataRecorder
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import java.io.File

class dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setNavigationDrawer();

        val dataDir=ContextCompat.getDataDir(this)
        val dataFile=File(dataDir,"userdata.txt")
        if(dataFile.exists()){
            //get all the data u needed
            var data = dataFile.readText()
            var datalist = data.split("/n")
            for (i in 0..datalist.size-1)
                Log.i("userdata",datalist[i])
        }
        else{

            supportFragmentManager.beginTransaction().add(R.id.datafrag,
                dataRecorder()
            ).commitNow()
            //dataFile.writeText("Abhishek/nChandigarh University/nstudent/n")
            Log.i("userdata","writing")
        }
    }

    private fun setNavigationDrawer() {
          nav_drawer.setNavigationItemSelectedListener {
              when(it.itemId){
                R.id.menu_profile -> {
                                        Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                                        return@setNavigationItemSelectedListener true }
                R.id.menu_setting -> true
                  R.id.menu_out -> true
                  else -> { return@setNavigationItemSelectedListener  false}
              }
          }

        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

}
