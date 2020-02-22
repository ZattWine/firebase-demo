package com.norm.firebasedemo

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * Created by Kyaw Zayar Tun on 2020-02-22.
 */
class MyApp: Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}