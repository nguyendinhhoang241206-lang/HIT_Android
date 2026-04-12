package com.example.myapplication

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ProductAdapter

    private var products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        products = mutableListOf(
            Product("Product 1", "100"),
            Product("Product 2", "100"),
            Product("Product 1", "100"),
            Product("Product 2", "100"),
            Product("Product 1", "100"),
            Product("Product 2", "100"),
            Product("Product 1", "100"),
            Product("Product 2", "100"),
            Product("Product 3", "100")
        )

        adapter = ProductAdapter(
            products,
            onItemClick = { product: Product ->
                Toast.makeText(this, "Clicker: ${product.name}", Toast.LENGTH_LONG).show()
            }
        )

        val rvProducts = binding.rvProducts
//        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)   // Danh sách dọc
        rvProducts.adapter = adapter
    }
}