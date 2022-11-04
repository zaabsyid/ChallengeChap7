package com.zahirar.challengechap7.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.worker.BlurWorker
import com.zahirar.challengechap7.worker.KEY_IMAGE_URI
import java.lang.IllegalArgumentException

class BlurViewModel(application: Application): ViewModel() {
    private var workManager = WorkManager.getInstance(application)
    private var imageUri: Uri? = null

    init {
        imageUri = getImageUri(application.applicationContext)
    }

    internal fun applyBlur(blurLevel: Int){
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForUri())
            .build()
        workManager.enqueue(blurRequest)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    private fun getImageUri(context: Context): Uri {
        val resources = context.resources
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.profile))
            .appendPath(resources.getResourceTypeName(R.drawable.profile))
            .appendPath(resources.getResourceEntryName(R.drawable.profile))
            .build()
    }
}

class BlurViewModelFactory(private val application: Application):
    ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(BlurViewModel::class.java)){
            BlurViewModel(application) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}