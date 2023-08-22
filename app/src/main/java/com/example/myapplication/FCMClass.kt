package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMClass : FirebaseMessagingService() {
    private val Notification_ID = "1200"
    private var isServiceRunning = false
    private val  channel_Id = "foreground Service"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("onNewToken", "$token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null) {
                startForgroundNotification(message.notification!!)
        }

    }

    private fun startForgroundNotification(notification: RemoteMessage.Notification) {
        if(isServiceRunning) return
        isServiceRunning = true

        var notifManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        var intent = Intent(applicationContext, MainActivity::class.java)
        intent.also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.action= Intent.ACTION_MAIN
        }

        var pendingIntent = PendingIntent.getActivity(this, 100, intent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            else PendingIntent.FLAG_UPDATE_CURRENT)


        //android version greater than oreo version code 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifiation Name"
            val descriptionText = "This notification is for Playing music in background even when app is killed"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channelId =
                NotificationChannel(channel_Id, name, importance).apply {
                    this.description = descriptionText
                }
            notifManager.createNotificationChannel(channelId)
        }

        val notification = NotificationCompat.Builder(this, channel_Id)
            .apply {
                setContentTitle(notification.title)
                setContentInfo(notification.body)
                setContentIntent(pendingIntent)

            }.build()

        //startForeground(1, notification)
        notifManager.notify(1, notification)
    }

}