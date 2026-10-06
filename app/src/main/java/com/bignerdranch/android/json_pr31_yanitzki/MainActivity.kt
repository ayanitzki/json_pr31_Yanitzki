package com.bignerdranch.android.json_pr31_yanitzki


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var name_et: EditText
    lateinit var price_et: EditText
    lateinit var tags_et: EditText
    lateinit var name_tv: TextView
    lateinit var price_tv: TextView
    lateinit var tags_tv: TextView
    lateinit var ButtonProcess: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name_et = findViewById(R.id.EditTextName)
        price_et = findViewById(R.id.EditTextPrice)
        tags_et = findViewById(R.id.EditTextTags)
        ButtonProcess = findViewById(R.id.ButtonProcess)

        name_tv = findViewById(R.id.TextViewResultName)
        price_tv = findViewById(R.id.TextViewResultPrice)
        tags_tv = findViewById(R.id.TextViewResultTags)

        val gson = Gson()

        val initialProduct = Product(
            name = "Программирование на Kotlin",
            price = 1500.0,
            tags = listOf("учебник", "программирование", "android")
        )

        val initialJson = gson.toJson(initialProduct)
        Log.d("GsonAssignment", "Пункт 3: $initialJson")

        ButtonProcess.setOnClickListener {
            val name = name_et.text.toString()
            val price = price_et.text.toString().toDoubleOrNull() ?: 0.0
            val tags = tags_et.text.toString()
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            val userProduct = Product(name, price, tags)

            val jsonString = gson.toJson(userProduct)
            Log.d("GsonAssignment", "Пункт 5 (Полученный JSON): $jsonString")

            val deserializedProduct = gson.fromJson(jsonString, Product::class.java)

            name_tv.text = "Название товара: ${deserializedProduct.name}"
            price_tv.text = "Цена: ${deserializedProduct.price}"
            tags_tv.text = "Теги: ${deserializedProduct.tags.joinToString(", ")}"

            Log.d("GsonAssignment", "Пункт 6 (Вывод в лог) -> Название: ${deserializedProduct.name}, Цена: ${deserializedProduct.price}, Теги: ${deserializedProduct.tags}")

            runSelfCheckTests(gson)
        }
    }
    private fun runSelfCheckTests(gson: Gson) {
        val jsonWithoutPrice = """{"name":"Тестовая книга","tags":["учеба"]}"""
        val productWithoutPrice = gson.fromJson(jsonWithoutPrice, Product::class.java)
        Log.d("GsonAssignment", "Тест А (без price) -> $productWithoutPrice")

        val jsonWithExtraField = """{"name":"Тестовая книга","price":500.0,"tags":["учеба"],"unsupportedField":"данные"}"""
        val productWithExtraField = gson.fromJson(jsonWithExtraField, Product::class.java)
        Log.d("GsonAssignment", "Тест Б (с лишним полем) -> $productWithExtraField")
    }
}