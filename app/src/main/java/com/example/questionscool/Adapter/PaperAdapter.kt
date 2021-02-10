package com.example.questionscool.Adapter

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.questionscool.Model.Paper
import com.example.questionscool.R

class PaperAdapter(val context: Context, val paperlist: ArrayList<Paper>,val activity: Activity) :
    RecyclerView.Adapter<PaperAdapter.PaperHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaperHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.paper_specific, parent, false)
        return PaperHolder(view)

    }

    override fun getItemCount(): Int {
        return paperlist.size
    }

    override fun onBindViewHolder(holder: PaperHolder, position: Int) {
        val p: Paper = paperlist[position]
        holder.title.text = p.yearandsam

        if(p.yearandsam?.endsWith("pdf")!!){
            holder.download.visibility = View.GONE
        }

        holder.download.setOnClickListener{

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    //request permission
                    Toast.makeText(context,"ask for permission",Toast.LENGTH_LONG).show()
                    ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0)


                }else{
                    // permission granted download here
                    startDownloading(p)
                }
            }else{
                //runtime permission not required
                startDownloading(p)
            }
        }

    }

    private fun startDownloading(p:Paper){
        val url = p.link
        if(url!=null){

           try {
               val request = DownloadManager.Request(Uri.parse(url))
               request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
               request.setTitle(URLUtil.guessFileName(url,null,null))
               request.setDescription("file is downloading")
               request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
               request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOCUMENTS,"qb-${p.yearandsam}qbapp")

               val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
               manager.enqueue(request)
           }catch (e:Exception){
               Toast.makeText(context,"url is invalid ",Toast.LENGTH_LONG).show()
           }
        }



    }

    class PaperHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title_download)
        var download:ImageButton = view.findViewById(R.id.downlaod_paper)
    }

}