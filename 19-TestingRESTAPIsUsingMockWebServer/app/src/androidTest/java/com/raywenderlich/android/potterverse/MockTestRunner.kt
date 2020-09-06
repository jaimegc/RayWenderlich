package com.raywenderlich.android.potterverse

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

// You need to provide a test runner that uses PotterTestApp instead of PotterApp when running a test
class MockTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?,
                                context: Context?): Application {
        return super.newApplication(cl, PotterTestApp::class.java.name, context)
    }
}
