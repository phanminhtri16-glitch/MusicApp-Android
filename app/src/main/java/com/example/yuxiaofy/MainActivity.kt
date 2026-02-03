package com.example.yuxiaofy

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rotateAnimator: ObjectAnimator
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Xử lý Edge-to-Edge
        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // Setup Animation cho đĩa nhạc
        val coverArt = findViewById<ImageView>(R.id.cover_art)
        rotateAnimator = ObjectAnimator.ofFloat(coverArt, "rotation", 0f, 360f).apply {
            duration = 10000 // 10 giây 1 vòng
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

        // Setup Nút Play/Pause
        val playBtn = findViewById<FloatingActionButton>(R.id.play_pause_btn)
        playBtn.setOnClickListener {
            if (isPlaying) {
                // Pause
                playBtn.setImageResource(android.R.drawable.ic_media_play)
                rotateAnimator.pause()
            } else {
                // Play
                playBtn.setImageResource(android.R.drawable.ic_media_pause)
                if (rotateAnimator.isPaused) rotateAnimator.resume() else rotateAnimator.start()
            }
            isPlaying = !isPlaying
        }

        // Setup Playlist bên dưới
        val rvPlaylist = findViewById<RecyclerView>(R.id.rvPlaylist)
        if (rvPlaylist != null) {
            rvPlaylist.layoutManager = LinearLayoutManager(this)
            val dummySongs =
                listOf("Unity", "Monody", "The Calling", "Xenogenesis", "Fly Away", "Time Lapse")
            rvPlaylist.adapter = PlaylistAdapter(dummySongs)
        }
    }
}

// Giữ nguyên PlaylistAdapter cũ của bạn
class PlaylistAdapter(private val songs: List<String>) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIndex: TextView = view.findViewById(R.id.tvIndex)
        val tvTitle: TextView = view.findViewById(R.id.tvSongTitle)
        val tvArtist: TextView = view.findViewById(R.id.tvArtist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvIndex.text = (position + 1).toString()
        holder.tvTitle.text = songs[position]
        holder.tvArtist.text = "TheFatRat"
        if (position == 0) {
            holder.tvTitle.setTextColor(Color.parseColor("#BB86FC"))
            holder.tvIndex.setTextColor(Color.parseColor("#BB86FC"))
        } else {
            holder.tvTitle.setTextColor(Color.WHITE)
            holder.tvIndex.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount() = songs.size
}