package com.android.kolistener

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var adapter: RvAdapter
    var dataList = ArrayList<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecView()
        btnNew.setOnClickListener { showCreateSiteDialog() }
    }


    private fun initRecView() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dataList.add(Model("Gugl", "google.de", status = 200))
        dataList.add(Model("Yandex", "yandex.ru", status = 200))
        adapter = RvAdapter(dataList, this)
        recyclerView.adapter = adapter
        adapter.getAdapterClick().observe(this, Observer {
            //TODO: show delete dialog
        })
    }

//    private fun checkUrl(url: String) : Int{
//        var code = 0
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("TAG", call.request().toString())
//                Log.d("TAG", e.message.toString())
//            }
//            override fun onResponse(call: Call, response: Response) {
//                code = response.code
//                Log.d("TAG", response.code.toString())
//            }
//        })
//        return code
//    }

    fun checkIt(url: String) {

        val queue = Volley.newRequestQueue(this)
//        val url = "http://www.google.com"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("TAG", response.toString())
            },
            Response.ErrorListener {
                Toast.makeText(this, "Please fill Name and Url", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun showCreateSiteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Observe new url")
        val view = layoutInflater.inflate(R.layout.dialog_new_site, null)
        builder.setView(view)

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val etvName = view.findViewById<EditText>(R.id.etvName)
            val etvUrl = view.findViewById<EditText>(R.id.etvUrl)
            if (etvName.text.isNullOrEmpty() && etvUrl.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill Name and Url", Toast.LENGTH_SHORT).show()

            } else {
                Log.d("TAG", etvUrl.text.toString())
                addNewItem(
                    Model(
                        name = etvName.text.toString(),
                        url = etvUrl.text.toString()
                    )
                )
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun addNewItem(model: Model) {
        checkIt(model.url)
        dataList.add(model)
        adapter.notifyDataSetChanged()
    }
}
