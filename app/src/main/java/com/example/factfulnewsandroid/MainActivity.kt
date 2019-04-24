package com.example.factfulnewsandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val articles = mutableListOf<Article>()
        for (i in 0..10) {
            articles.add(Article(
                "A Random News Article",
                "https://bnor.me/images/cartoon-v1.png",
                "This is a random snippet.",
                0,
                "https://bnor.me"))
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ArticlesAdapter(articles)
        }
    }
}
