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
import java.util.Calendar

// Data Model có thêm trạng thái yêu thích
data class SongHome(
    val title: String,
    val artist: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)

class HomeActivity : AppCompatActivity() {

    private lateinit var rvSongs: RecyclerView
    private lateinit var songAdapter: HomeSongAdapter
    private lateinit var listTitle: TextView
    private lateinit var tvGreeting: TextView
    private lateinit var miniPlayer: CardView

    // Nút Categories
    private lateinit var btnChill: TextView
    private lateinit var btnWorkout: TextView
    private lateinit var btnFocus: TextView
    private lateinit var btnRB: TextView

    // Dữ liệu mẫu
    private val chillList = listOf(
        SongHome("Unity", "TheFatRat", R.drawable.unitythefatrat, true),
        SongHome("Monody", "TheFatRat", R.drawable.ic_launcher_background),
        SongHome("Time Lapse", "TheFatRat", R.drawable.ic_launcher_background)
    )
    private val workoutList = listOf(
        SongHome("Stronger", "Kanye West", R.drawable.ic_launcher_background),
        SongHome("Believer", "Imagine Dragons", R.drawable.ic_launcher_background)
    )
    private val focusList = listOf(
        SongHome("River Flows", "Yiruma", R.drawable.ic_launcher_background),
        SongHome("Weightless", "Marconi Union", R.drawable.ic_launcher_background)
    )
    private val rbList = listOf(
        SongHome("Blinding Lights", "The Weeknd", R.drawable.ic_launcher_background),
        SongHome("Stay", "Kid LAROI", R.drawable.ic_launcher_background)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1. Ánh xạ View
        tvGreeting = findViewById(R.id.tvGreeting)
        listTitle = findViewById(R.id.tvListTitle)
        miniPlayer = findViewById(R.id.miniPlayer)
        rvSongs = findViewById(R.id.rvSongs)
        btnChill = findViewById(R.id.btnCatChill)
        btnWorkout = findViewById(R.id.btnCatWorkout)
        btnFocus = findViewById(R.id.btnCatFocus)
        btnRB = findViewById(R.id.btnCatRB)

        // 2. Setup Lời chào
        setupGreeting()

        // 3. Setup RecyclerView
        rvSongs.layoutManager = LinearLayoutManager(this)
        rvSongs.isNestedScrollingEnabled = false // Quan trọng để scroll mượt
        songAdapter = HomeSongAdapter(chillList) { song ->
            // Click vào bài hát -> Mở Player
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        rvSongs.adapter = songAdapter

        // 4. Sự kiện Click Category
        setupCategoryClick(btnChill, "Chill Vibes For You", chillList)
        setupCategoryClick(btnWorkout, "Workout Energy", workoutList)
        setupCategoryClick(btnFocus, "Deep Focus Mix", focusList)
        setupCategoryClick(btnRB, "R&B Classics", rbList)

        // 5. Mini Player Click
        miniPlayer.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun setupGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingText = when (hour) {
            in 5..11 -> "Good Morning,"
            in 12..17 -> "Good Afternoon,"
            else -> "Good Evening,"
        }
        tvGreeting.text = "$greetingText\nMusic Lover!"
    }

    private fun setupCategoryClick(
        selectedBtn: TextView,
        titleText: String,
        songs: List<SongHome>
    ) {
        selectedBtn.setOnClickListener {
            // Reset style
            resetButtonStyle(btnChill); resetButtonStyle(btnWorkout)
            resetButtonStyle(btnFocus); resetButtonStyle(btnRB)

            // Active style
            selectedBtn.background = ContextCompat.getDrawable(this, R.drawable.bg_chip_active)
            selectedBtn.setTextColor(Color.BLACK)
            listTitle.text = titleText

            // Update List
            songAdapter.updateData(songs)
        }
    }

    private fun resetButtonStyle(btn: TextView) {
        btn.background = ContextCompat.getDrawable(this, R.drawable.bg_chip_inactive)
        btn.setTextColor(Color.WHITE)
    }
}

// ADAPTER
class HomeSongAdapter(
    private var songs: List<SongHome>,
    private val onClick: (SongHome) -> Unit
) : RecyclerView.Adapter<HomeSongAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tvHomeSongTitle)
        val artist: TextView = v.findViewById(R.id.tvHomeArtist)
        val img: ImageView = v.findViewById(R.id.imgSongCover)
        val heart: ImageView = v.findViewById(R.id.btnHeart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
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

        // Logic Heart
        updateHeartIcon(holder.heart, s.isFavorite)
        holder.heart.setOnClickListener {
            s.isFavorite = !s.isFavorite
            updateHeartIcon(holder.heart, s.isFavorite)
        }

        holder.itemView.setOnClickListener { onClick(s) }
    }

    private fun updateHeartIcon(imageView: ImageView, isFav: Boolean) {
        if (isFav) {
            imageView.setImageResource(android.R.drawable.star_big_on) // Tạm dùng sao vàng
            imageView.setColorFilter(Color.parseColor("#FF4081")) // Màu hồng
        } else {
            imageView.setImageResource(android.R.drawable.star_off)
            imageView.setColorFilter(Color.GRAY)
        }
    }

    override fun getItemCount() = songs.size

    fun updateData(newSongs: List<SongHome>) {
        songs = newSongs
        notifyDataSetChanged()
    }
}