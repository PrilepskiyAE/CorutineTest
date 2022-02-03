package com.ambrella.corutinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import com.ambrella.corutinetest.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }
//private val handler:Handler= Handler()
    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity {
            binding.tvLocation.text = it
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            Handler(Looper.getMainLooper()).post {
                callback.invoke("Moscow")
            }

        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {

        //Handler(Looper.getMainLooper()).post <===>  runOnUiThread
            Thread.sleep(5000)
            thread {
                runOnUiThread {

                    Toast.makeText(
                            this,
                    getString(R.string.loading_temperature_toast, city),
                    Toast.LENGTH_SHORT
                    ).show()

                }
                Handler(Looper.getMainLooper()).post {
                    callback.invoke(17)
                }
        }
    }
}