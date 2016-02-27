package me.dong.gdg_testsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.*

class DetailPageActivity : AppCompatActivity() {
    //    internal var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.activity_detail_page)
        //        val toolbar = findViewById(R.id.toolbar) as Toolbar
        //        setSupportActionBar(toolbar)
        //
        //        val actionBar = supportActionBar
        //
        //        actionBar?.setDisplayHomeAsUpEnabled(true)
        //
        //        val intent = intent
        //        val detailPageUrl = intent.getStringExtra("detailProductUrl")
        //
        //        mWebView = findViewById(R.id.webView) as WebView
        //        mWebView.setWebViewClient(object : WebViewClient() {
        //
        //            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        //                view.loadUrl(url)
        //                return true
        //            }
        //        })
        //        mWebView.loadUrl(detailPageUrl)

        DetailPageActivityUi().setContentView(this)

        var webView: WebView? = null

        webView = findViewById(R.id.webView) as WebView

        //        verticalLayout {
        //            webView = webView() {
        //                id = R.id.webView
        //            }
        //        }

        var detailPageUrl = intent.getStringExtra("detailProductUrl")
        setupWebView(webView as WebView, detailPageUrl)
        //        webView?.loadUrl(detailPageUrl)
    }

    fun setupWebView(wv: WebView, productUrl: String): Unit {

        wv.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        })
        wv.loadUrl(productUrl)
    }

    fun buttonClick() {
        toast("click")
    }

    companion object {
        val TAG = DetailPageActivity::class.java!!.getSimpleName()
    }
}


class DetailPageActivityUi : AnkoComponent<DetailPageActivity> {

    private val customStyle = { v: Any ->
        when (v) {
            is Button -> {
                v.textSize = 20f
                v.backgroundColor = 0x99CCCCCC.toInt()
            }
            is EditText -> v.textSize = 12f
        }
    }

    override fun createView(ui: AnkoContext<DetailPageActivity>) = with(ui) {
        verticalLayout {
            webView {
                id = R.id.webView
                padding = dip(8)
            }.lparams {
                width = matchParent
                height = 0
                weight = 10f
            }
            button("why?") {
                onClick {
                    ui.owner.buttonClick()
                }
            }.lparams {
                width = matchParent
                height = 0
                weight = 1f
            }
            myCustomView()
        }
    }
}
