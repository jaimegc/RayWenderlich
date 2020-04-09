package com.raywenderlich.android.photouploader.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.raywenderlich.android.photouploader.ImageUtils

private const val LOG_TAG = "CleanFilesWorker"

class CleanFilesWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result = try {
        // Sleep for debugging purposes
        Thread.sleep(3000)
        Log.d(LOG_TAG, "Cleaning files!")

        ImageUtils.cleanFiles(applicationContext)

        Log.d(LOG_TAG, "Success!")
        Result.success()
    } catch (e: Throwable) {
        Log.e(LOG_TAG, "Error executing work: ${e.message}", e)
        Result.failure()
    }
}