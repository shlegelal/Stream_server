package com.example.stream

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import net.majorkernelpanic.streaming.SessionBuilder
import net.majorkernelpanic.streaming.rtsp.RtspServer


class MainActivity : Activity() {
    private var mSurfaceView: SurfaceView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mSurfaceView = findViewById<View>(R.id.surface) as SurfaceView

        // Sets the port of the RTSP server to 1234
        val editor: SharedPreferences.Editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putString(RtspServer.KEY_PORT, 1234.toString())
        editor.apply()

        // Configures the SessionBuilder
        SessionBuilder.getInstance()
            .setSurfaceView(mSurfaceView as net.majorkernelpanic.streaming.gl.SurfaceView?)
            .setPreviewOrientation(90)
            .setContext(applicationContext)
            .setAudioEncoder(SessionBuilder.AUDIO_NONE).videoEncoder = SessionBuilder.VIDEO_H264

        // Starts the RTSP server
        startService(Intent(this, RtspServer::class.java))
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}