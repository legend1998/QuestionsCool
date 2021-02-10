package com.example.questionscool

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Adapter.UniversityListAdapter
import com.example.questionscool.Model.University
import com.google.firebase.firestore.FirebaseFirestore
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat.getSystemService



class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var progresslayout:RelativeLayout

    var universitylist = arrayListOf<University>();

    val db = FirebaseFirestore.getInstance();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolbar = findViewById(R.id.homeToolbar);
        recyclerView = findViewById(R.id.recyclerview);
        progresslayout = findViewById(R.id.Progresslayout)
        setuptoolbar();

        progresslayout.visibility = View.VISIBLE

        db.collection("universities")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    universitylist.add(
                        University(
                            document.getString("name"),
                            document.getString("address"),
                            document.id
                        )
                    )
                }
                val layoutmanager = LinearLayoutManager(applicationContext as Context)
                val resadapter = UniversityListAdapter(applicationContext as Context, universitylist)
                recyclerView.layoutManager = layoutmanager
                recyclerView.adapter = resadapter
                progresslayout.visibility = View.GONE
            }
            .addOnFailureListener { exception ->

                Log.d("error", exception.toString());
            }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.action_download){
            //open downloads
            val intent = Intent(this,DownloadsActivity::class.java )
            startActivity(intent)
            return true
        }

        else return super.onOptionsItemSelected(item);

    }



    fun setuptoolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Question bank"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }
}
