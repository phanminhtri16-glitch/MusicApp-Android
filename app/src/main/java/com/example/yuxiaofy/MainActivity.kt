package com.example.yuxiaofy

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. Xử lý Edge-to-Edge (Căn lề)
        // Lưu ý: Đảm bảo root layout trong activity_main.xml có id là "@+id/main"
        // Nếu root layout chưa có id, hãy đổi dòng dưới thành findViewById(android.R.id.content) để test tạm
        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets // Bắt buộc phải trả về insets ở dòng cuối cùng của lambda
            }
        }

        // 2. Thiết lập RecyclerView (Đưa ra ngoài listener ở trên)
        val rvPlaylist = findViewById<RecyclerView>(R.id.rvPlaylist)

        // Kiểm tra null để tránh crash nếu chưa thêm id vào xml
        if (rvPlaylist != null) {
            rvPlaylist.layoutManager = LinearLayoutManager(this)

            // Dữ liệu giả lập
            val dummySongs = listOf("Unity", "Monody", "The Calling", "Xenogenesis", "Fly Away", "Time Lapse")
            rvPlaylist.adapter = PlaylistAdapter(dummySongs)
        }
    }
}

// Adapter cho Playlist
class PlaylistAdapter(private val songs: List<String>) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIndex: TextView = view.findViewById(R.id.tvIndex)
        val tvTitle: TextView = view.findViewById(R.id.tvSongTitle)
        val tvArtist: TextView = view.findViewById(R.id.tvArtist)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration) // Thêm dòng này nếu layout có tvDuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvIndex.text = (position + 1).toString()
        holder.tvTitle.text = songs[position]
        holder.tvArtist.text = "TheFatRat" // Giả lập tên ca sĩ

        // Highlight bài đang hát (Ví dụ bài đầu tiên - index 0)
        if (position == 0) {
            val purpleColor = Color.parseColor("#BB86FC")
            holder.tvTitle.setTextColor(purpleColor)
            holder.tvIndex.setTextColor(purpleColor)
        } else {
            holder.tvTitle.setTextColor(Color.WHITE)
            holder.tvIndex.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount() = songs.size
}