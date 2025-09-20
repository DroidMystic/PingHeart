package com.sabir.pingheart.service

import android.media.MediaPlayer
import android.net.Uri
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.sabir.pingheart.data.SoundMappingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue


class CustomNotificationListener: NotificationListenerService() {

    private val repository: SoundMappingRepository by inject()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val appPackage = sbn?.packageName
        val extras = sbn?.notification?.extras
        val sender = extras?.getString("android.title")

        scope.launch {
            val soundUri = repository.resolveSound(appPackage.toString(), sender)
            soundUri?.let{playSound(it)}
        }
    }

    private fun playSound(uri: String){
        try {
            val mp = MediaPlayer.create(this, (Uri.parse(uri)))
            mp.setOnCompletionListener { it.release() }
            mp.start()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}