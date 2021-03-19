package com.sanjaydevtech.samplecp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sanjaydevtech.cpscontracts.CPResolverHelper
import com.sanjaydevtech.cpscontracts.DomainData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.textView)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val resolverHelper =  CPResolverHelper(this, Handler(Looper.getMainLooper()))
        resolverHelper.titleLiveData.observe(this) {
            textView.text = "Size: ${it.size}"

        }
        fab.setOnClickListener {
            lifecycleScope.launch {
                resolverHelper.insertDomain(DomainData(title = "Hey"))
            }
        }
    }
}