package com.example.expoplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializePlayer()

    }



    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        _binding.exoPlayerVideo.player = player
        buildMediaSource().let {
            player?.setMediaSource(it)
            player?.prepare()
        }
    }

    private fun buildMediaSource(): MediaSource {
        val videoUrl = intent.getStringExtra("https://android-tv-classics.firebaseapp.com/content/le_voyage_dans_la_lun/media_le_voyage_dans_la_lun.mp4").toString()
        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.playWhenReady = false
        if (isFinishing) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.release()
    }
}