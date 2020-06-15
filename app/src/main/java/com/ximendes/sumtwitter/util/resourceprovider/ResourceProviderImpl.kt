package com.ximendes.sumtwitter.util.resourceprovider

import android.content.Context

class ResourceProviderImpl constructor(private val context: Context) :
    ResourceProvider {

    override fun getString(resId: Int): String {
        return context.resources.getString(resId)
    }
}