package com.example.demoapinewyorktimes

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapinewyorktimes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArituloAdapter

    private var articlesList = mutableListOf<NYTArticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArituloAdapter(articlesList) {
            binding.webViewArticulos.loadUrl(it.url)
            //Toast.makeText(this, it.url, Toast.LENGTH_LONG).show()
        }

        binding.recyclerViewArticulos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewArticulos.adapter = adapter

        getEmailPopular()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getEmailPopular() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<NYTJsonResponse> =
                getRetrofit().create(APIService::class.java)
                    .getMostPopularArticles("emailed/7.json?api-key=wGBoVoFG8hjGHlGMYf6foSJyLTFk2EgA")
            val nytJsonResponse: NYTJsonResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    // Respuesta exitosa, mostrar
                    if (nytJsonResponse != null) {
                        articlesList.clear()
                        articlesList.addAll(nytJsonResponse.results)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    // Mostrar un error
                    Toast.makeText(this@MainActivity, "Ocurri?? un error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}