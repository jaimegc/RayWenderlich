package com.raywenderlich.android.potterverse


// You need to create an equivalent of PotterApp that returns the mock URL instead of the real URL
// 8080 is the port MockWebServer will use.
class PotterTestApp : PotterApp() {

    var url = "http://127.0.0.1:8080"

    override fun getBaseUrl(): String {
        return url
    }
}
