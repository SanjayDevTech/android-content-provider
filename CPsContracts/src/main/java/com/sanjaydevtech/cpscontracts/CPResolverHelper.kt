package com.sanjaydevtech.cpscontracts

import android.content.ContentValues
import android.database.ContentObserver
import android.os.Handler
import androidx.core.app.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class CPResolverHelper(private val context: ComponentActivity, handler: Handler?) {

    // @ExperimentalCoroutinesApi
    private val _titleLiveData: MutableLiveData<List<String>> = MutableLiveData(listOf())

    val titleLiveData: LiveData<List<String>>
        get() = _titleLiveData


    init {
        context.lifecycleScope.launchWhenCreated {
            _titleLiveData.value = getAllDomains()
            context.contentResolver.registerContentObserver(CPsContract.DomainEntry.DOMAIN_URI,
                true,
                object : ContentObserver(handler) {
                    override fun onChange(selfChange: Boolean) {
                        println("onChange")
                        context.lifecycleScope.launch {
                            _titleLiveData.value = getAllDomains()
                        }
                    }
                })
        }
    }


    suspend fun getAllDomains(): List<String> {
        return withContext(Dispatchers.IO) {
            val titles = mutableListOf<String>()

            val cursor = context.contentResolver!!.query(
                CPsContract.DomainEntry.DOMAIN_URI,
                null,
                null,
                null,
                null
            )
            cursor?.let {
                val titleIndex = it.getColumnIndex(CPsContract.DomainEntry.TITLE)
                while (it.moveToNext()) {
                    titles.add(it.getString(titleIndex))
                }
            }
            cursor?.close()
            return@withContext titles
        }

    }

    suspend fun insertDomain(domainData: DomainData) {
        withContext(Dispatchers.IO) {
            context.contentResolver!!.insert(
                CPsContract.DomainEntry.DOMAIN_URI,
                ContentValues().apply {
                    put("title", domainData.title)
                },
            )
        }
    }
}