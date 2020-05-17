package com.taxonteam.taxon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.File
import java.io.InputStream

class dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val dataDir=ContextCompat.getDataDir(this)
        val dataFile=File(dataDir,"userdata.txt")
        if(dataFile.exists()){
            //get all the data u needed
            var data=dataFile.readText()
            var datalist=data.split("/n")
            for (i in 0..datalist.size-1)
                Log.i("userdata",datalist[i])
        }
        else{

            supportFragmentManager.beginTransaction().add(R.id.datafrag,dataRecorder()).commitNow()
            //dataFile.writeText("Abhishek/nChandigarh University/nstudent/n")
            Log.i("userdata","writing")
        }
    }
}
