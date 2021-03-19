package com.sanjaydevtech.cps

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.sanjaydevtech.cps.database.Domain
import com.sanjaydevtech.cps.database.DomainDao
import com.sanjaydevtech.cps.database.MainDatabase

const val PROVIDER = "com.sanjaydevtech.cps"

class CPSDataProvider : ContentProvider() {

    private lateinit var database: MainDatabase
    private lateinit var domainDao: DomainDao


    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(PROVIDER, DOMAINS, DOMAINS_CODE)
        addURI(PROVIDER, DOMAINS_ITEM, DOMAINS_ITEM_CODE)
    }

    override fun onCreate(): Boolean {
        database = MainDatabase.getInstance(context!!)
        domainDao = database.domainDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            DOMAINS_CODE -> {
                domainDao.selectAll()
            }

            DOMAINS_ITEM_CODE -> {
                domainDao.selectById(uri.lastPathSegment!!.toInt())
            }

            else -> null
        }

    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            DOMAINS_CODE -> "vnd.android.cursor.dir/$PROVIDER/domains"
            DOMAINS_ITEM_CODE -> "vnd.android.cursor.item/$PROVIDER/domains"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            DOMAINS_CODE -> {
                val domain = Domain(title = values!!.get("title") as String)
                val rowId = domainDao.insert(domain)
                val finalUri = ContentUris.withAppendedId(uri, rowId)
                context!!.contentResolver.notifyChange(finalUri, null)
                finalUri
            }
            else -> null
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            DOMAINS_CODE -> {
                val domain =
                    Domain(id = values!!.get("id") as Int, title = values.get("title") as String)
                val count = domainDao.update(domain)
                if (count == 1) {
                    context!!.contentResolver.notifyChange(
                        ContentUris.withAppendedId(
                            uri,
                            domain.id.toLong()
                        ), null
                    )
                }
                count
            }
            else -> 0
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            DOMAINS_ITEM_CODE -> {
                val rowId = uri.lastPathSegment!!.toInt()
                val count = domainDao.deleteById(rowId)
                if (count == 1) {
                    context!!.contentResolver.notifyChange(
                        ContentUris.withAppendedId(
                            uri,
                            rowId.toLong()
                        ), null
                    )
                }
                count
            }
            else -> 0
        }
    }
}