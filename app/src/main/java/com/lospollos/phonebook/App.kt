package com.lospollos.phonebook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


class App: Application() {

    private var cicerone: Cicerone<Router>? = null

    fun getNavigatorHolder(): NavigatorHolder? = cicerone!!.navigatorHolder

    fun getRouter(): Router? = cicerone!!.router

    override fun onCreate() {
        super.onCreate() //todo navigation
        cicerone = Cicerone.create()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }
}