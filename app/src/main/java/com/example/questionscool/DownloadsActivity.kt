package com.example.questionscool

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Adapter.DownloadAdapter
import com.example.questionscool.Model.Paper

class DownloadsActivity : AppCompatActivity() {

    lateinit var toolbar:Toolbar
    lateinit var progress:RelativeLayout
    lateinit var recycler:RecyclerView

    var downloaded_files = arrayListOf<Paper>()
    var loading:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloads)

        toolbar   = findViewById(R.id.downloads_toolbar)
        progress = findViewById(R.id.progress_download)
        recycler = findViewById(R.id.recyclerview_downloads)

        setuptoolbar()

        progress.visibility = View.VISIBLE

        addfiles()

        if(loading){

            val layoutmanager = LinearLayoutManager(applicationContext as Context)
            val adapter = DownloadAdapter(applicationContext as Context, downloaded_files,this)
            recycler.layoutManager = layoutmanager
            recycler.adapter = adapter

            progress.visibility  = View.GONE

        }

    }

    fun addfiles(){
        applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.walk()?.forEach {
            if(it.name.endsWith("qbapp")){
                downloaded_files.add(Paper(null,it.absolutePath,null,it.name))
            }
        }
        loading= true
        print("this is data")
        print(downloaded_files)
    }

    fun setuptoolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Downloads"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }
}
