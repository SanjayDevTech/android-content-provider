package com.sanjaydevtech.cpscontracts

import android.net.Uri
import android.provider.BaseColumns

object CPsContract {
    const val AUTHORITY = "com.sanjaydevtech.cps"
    val AUTHORITY_URI = Uri.parse("content://$AUTHORITY")!!

    object DomainEntry : BaseColumns {
        val TABLE_NAME = "domains"
        val DOMAIN_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME)
        val TITLE = "title"
    }

}