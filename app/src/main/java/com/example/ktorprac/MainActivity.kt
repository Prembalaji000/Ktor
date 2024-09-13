package com.example.ktorprac

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktorprac.databinding.ActivityMainBinding
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RvAdapter
    private var usersList: List<PostItem> = emptyList()
    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging){
            level = LogLevel.ALL
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchData()

    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun fetchData(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response : HttpResponse =  client.get("https://jsonplaceholder.typicode.com/comments")
                Log.e("response", response.toString())
                Log.e("response", response.body())
                Log.e("response", "response.status.toString()")
                if (response.status.isSuccess()){
                    Log.e("response", response.status.description)
                    response.bodyAsText()
                    Log.e("response", response.bodyAsText())
                    withContext(Dispatchers.Main){
                        val users = response.bodyAsText()
                        val post = Json.decodeFromString<List<PostItem>>(users)
                        Log.e("response", post.toString())
                        binding.rvMain.apply {
                            rvAdapter = RvAdapter(post)
                            adapter = rvAdapter
                            layoutManager = LinearLayoutManager(this@MainActivity)
                        }

                    }
                    Log.e("response", response.toString())
                    Log.e("response", usersList.toString())
                } else {
                    withContext(Dispatchers.Main){
                        showError("Error : ${response.status.value}")
                    }
                }
            } catch (e : Exception) {
                withContext(Dispatchers.Main){
                    showError("Network error: ${e.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Error: ${e.message}")
                }
            }
        }
    }
}


