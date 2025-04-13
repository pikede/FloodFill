package com.example.floodfill

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.floodfill.compose_based.FloodFillRoute
import com.example.floodfill.databinding.ActivityMainBinding
import com.example.floodfill.ui.theme.FloodFillTheme
import com.example.floodfill.view_based.FloodFillFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.compose.setOnClickListener {
            useCompose()
        }
        binding.view.setOnClickListener {
            useView()
        }
    }

    private fun useCompose() {
        setContent {
            FloodFillTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FloodFillRoute(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun useView() {
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, FloodFillFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}
