package com.example.harrypotterapp.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//Set the application as HiltAndroidApp because it will be used to kickoff code generation.
//Also,i have added this application class and internet permission to the AndroidManifest.xml file.
@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}