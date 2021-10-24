package com.example.saveus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.saveus.R

private const val MY_URL = "url"

class WebViewFragment : Fragment() {
    private var myUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            myUrl = it.getString(MY_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        myUrl?.let { view.findViewById<WebView>(R.id.web_view).loadUrl(it) }
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar?.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(myUrl: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(MY_URL, myUrl)
                }
            }
    }
}