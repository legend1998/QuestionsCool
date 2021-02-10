package com.example.questionscool

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Adapter.CourseAdapter
import com.example.questionscool.Model.Course
import com.google.firebase.firestore.FirebaseFirestore

class CourseListActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var progressLayout: RelativeLayout
    lateinit var recycler: RecyclerView

    val db = FirebaseFirestore.getInstance()
    var courselist = arrayListOf<Course>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        toolbar = findViewById(R.id.toolbar_activity_course)
        progressLayout = findViewById(R.id.Progresslayout_courses)
        recycler = findViewById(R.id.recycler_courses)

        setuptoolbar()

        val intent = getIntent()
        val univid: String? = intent.getStringExtra("univid")

        progressLayout.visibility = View.VISIBLE


        if (univid != null) {
            db.collection("universities").document(univid).collection("courses").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        courselist.add(
                            Course(
                                document.getString("name"),
                                document.id,
                                document.getString("code"),
                                document.getString("university_id")
                            )
                        )
                    }
                    val layoutmanager = LinearLayoutManager(applicationContext as Context)
                    val adapter = CourseAdapter(applicationContext as Context, courselist)

                    recycler.layoutManager = layoutmanager
                    recycler.adapter = adapter

                    progressLayout.visibility = View.GONE

                }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
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
        supportActionBar?.title = "Courses"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }
}
