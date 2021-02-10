package com.example.questionscool

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Adapter.DownloadAdapter
import com.example.questionscool.Adapter.PaperAdapter
import com.example.questionscool.Model.Paper
import com.google.firebase.firestore.FirebaseFirestore

class PaperQActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var progresslayout: RelativeLayout
    lateinit var recycler: RecyclerView

    var db = FirebaseFirestore.getInstance()

    var paperList = arrayListOf<Paper>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper_q)

        toolbar = findViewById(R.id.toolbar_activity_paper)
        progresslayout = findViewById(R.id.Progresslayout_paper)
        recycler = findViewById(R.id.recycler_paper)

        setuptoolbar()


        val intent = getIntent()
        val courseid: String? = intent.getStringExtra("courseid")
        val univ: String? = intent.getStringExtra("univid")

        if (courseid != null && univ != null) {
            db.collection("universities").document(univ).collection("courses").document(courseid)
                .collection("papers").get().addOnSuccessListener { result ->
                    for (document in result) {
                        paperList.add(
                            Paper(
                                document.getString("course_id"),
                                document.getString("link"),
                                document.getString("university_id"),
                                document.getString("yearandsem")
                            )
                        )
                    }

                    val layoutmanager = LinearLayoutManager(applicationContext as Context)
                    val adapter = PaperAdapter(applicationContext as Context, paperList, this)

                    recycler.layoutManager = layoutmanager
                    recycler.adapter = adapter

                    progresslayout.visibility = View.GONE

                }.addOnFailureListener { exception ->
                    Log.d("error", exception.toString())
                }
        } else {
            Toast.makeText(
                applicationContext as Context,
                "university id not found",
                Toast.LENGTH_LONG
            ).show();
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_download) {
            //open downloads
            val intent = Intent(this, DownloadsActivity::class.java)
            startActivity(intent)
            return true
        } else return super.onOptionsItemSelected(item);

    }

    fun setuptoolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Papers"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }
}
