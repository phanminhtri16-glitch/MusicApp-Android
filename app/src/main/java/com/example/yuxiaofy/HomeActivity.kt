package com.example.yuxiaofy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 1. Model dữ liệu
data class SongHome(val title: String, val artist: String, val imageRes: Int)

class HomeActivity : AppCompatActivity() {

    private lateinit var btnChill: TextView
    private lateinit var btnWorkout: TextView
    private lateinit var btnFocus: TextView
    private lateinit var btnRB: TextView
    private lateinit var listTitle: TextView
    private lateinit var miniPlayer: CardView
    private lateinit var rvSongs: RecyclerView // Khai báo RecyclerView
    private lateinit var songAdapter: HomeSongAdapter

    // 2. Dữ liệu mẫu (Cần đảm bảo bạn có ảnh trong drawable hoặc dùng ic_launcher_background)
    private val chillList = listOf(
        SongHome("Unity", "TheFatRat", R.drawable.unitythefatrat),
        SongHome("Monody", "TheFatRat", R.drawable.ic_launcher_background),
        SongHome("Time Lapse", "TheFatRat", R.drawable.ic_launcher_background)
    )

    private val workoutList = listOf(
        SongHome("Stronger", "Kanye West", R.drawable.ic_launcher_background),
        SongHome("Believer", "Imagine Dragons", R.drawable.ic_launcher_background)
    )

    private val focusList = listOf(
        SongHome("River Flows In You", "Yiruma", R.drawable.ic_launcher_background),
        SongHome("Weightless", "Marconi Union", R.drawable.ic_launcher_background)
    )

    private val rbList = listOf(
        SongHome("Blinding Lights", "The Weeknd", R.drawable.ic_launcher_background),
        SongHome("Stay", "Kid LAROI", R.drawable.ic_launcher_background)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Ánh xạ View
        btnChill = findViewById(R.id.btnCatChill)
        btnWorkout = findViewById(R.id.btnCatWorkout)
        btnFocus = findViewById(R.id.btnCatFocus)
        btnRB = findViewById(R.id.btnCatRB)
        listTitle = findViewById(R.id.tvListTitle)
        miniPlayer = findViewById(R.id.miniPlayer)
        rvSongs = findViewById(R.id.rvSongs) // Ánh xạ RecyclerView

        // 3. Cài đặt RecyclerView Adapter
        rvSongs.layoutManager = LinearLayoutManager(this)
        rvSongs.isNestedScrollingEnabled = false // Để cuộn mượt trong ScrollView

        // Mặc định load danh sách Chill
        songAdapter = HomeSongAdapter(chillList) { song ->
            // Sự kiện khi click vào bài hát
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        rvSongs.adapter = songAdapter

        // 4. Xử lý click Category
        setupCategoryClick(btnChill, "Chill Vibes For You", chillList)
        setupCategoryClick(btnWorkout, "Workout Energy", workoutList)
        setupCategoryClick(btnFocus, "Deep Focus Mix", focusList)
        setupCategoryClick(btnRB, "R&B Classics", rbList)

        // Mini Player Click
        miniPlayer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun setupCategoryClick(
        selectedBtn: TextView,
        titleText: String,
        songs: List<SongHome>
    ) {
        selectedBtn.setOnClickListener {
            resetButtonStyle(btnChill)
            resetButtonStyle(btnWorkout)
            resetButtonStyle(btnFocus)
            resetButtonStyle(btnRB)

            selectedBtn.background = ContextCompat.getDrawable(this, R.drawable.bg_chip_active)
            selectedBtn.setTextColor(Color.BLACK)
            listTitle.text = titleText

            // Cập nhật dữ liệu cho Adapter
            songAdapter.updateData(songs)
        }
    }

    private fun resetButtonStyle(btn: TextView) {
        btn.background = ContextCompat.getDrawable(this, R.drawable.bg_chip_inactive)
        btn.setTextColor(Color.WHITE)
    }
}

// 5. Adapter Class
class HomeSongAdapter(
    private var songs: List<SongHome>,
    private val onClick: (SongHome) -> Unit
) : RecyclerView.Adapter<HomeSongAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tvHomeSongTitle)
        val artist: TextView = v.findViewById(R.id.tvHomeArtist)
        val img: ImageView = v.findViewById(R.id.imgSongCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        // QUAN TRỌNG: Dòng này quyết định giao diện item
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_song, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = songs[position]
        holder.title.text = s.title
        holder.artist.text = s.artist
        try {
            holder.img.setImageResource(s.imageRes)
        } catch (e: Exception) {
            holder.img.setImageResource(R.drawable.ic_launcher_background)
        }

        holder.itemView.setOnClickListener { onClick(s) }
    }

    override fun getItemCount() = songs.size

    fun updateData(newSongs: List<SongHome>) {
        songs = newSongs
        notifyDataSetChanged()
    }
}