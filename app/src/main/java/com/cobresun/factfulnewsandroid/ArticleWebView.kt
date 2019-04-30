package com.cobresun.factfulnewsandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.article_web_view.*

const val ARTICLE_URL_EXTRA = "url"

class ArticleWebView: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_web_view)

        val url = intent.getStringExtra(ARTICLE_URL_EXTRA)

        webView.loadUrl(url)
    }
}