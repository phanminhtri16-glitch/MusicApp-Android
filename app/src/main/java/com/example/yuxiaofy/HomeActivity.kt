package com.example.yuxiaofy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    // Khai báo các nút thể loại
    private lateinit var btnChill: TextView
    private lateinit var btnWorkout: TextView
    private lateinit var btnFocus: TextView
    private lateinit var btnRB: TextView
    private lateinit var listTitle: TextView
    private lateinit var miniPlayer: CardView

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

        // TODO: Gán Adapter cho rvSongs ở đây

        // Xử lý click Category
        setupCategoryClick(btnChill, "Chill Vibes For You")
        setupCategoryClick(btnWorkout, "Workout Energy")
        setupCategoryClick(btnFocus, "Deep Focus Mix")
        setupCategoryClick(btnRB, "R&B Classics")

        // Xử lý click Mini Player -> Mở Player Full (MainActivity cũ)
        miniPlayer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Có thể truyền dữ liệu bài hát hiện tại qua intent
            startActivity(intent)
            // Hiệu ứng chuyển cảnh mượt mà (slide up)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun setupCategoryClick(selectedBtn: TextView, titleText: String) {
        selectedBtn.setOnClickListener {
            // 1. Reset tất cả nút về trạng thái Inactive (nền trong suốt, viền xám, chữ trắng)
            resetButtonStyle(btnChill)
            resetButtonStyle(btnWorkout)
            resetButtonStyle(btnFocus)
            resetButtonStyle(btnRB)

            // 2. Set nút được chọn thành Active (nền tím, chữ đen)
            selectedBtn.background = ContextCompat.getDrawable(this, R.drawable.bg_chip_active)
            selectedBtn.setTextColor(Color.BLACK)

            // 3. Đổi tiêu đề danh sách
            listTitle.text = titleText

            // 4. TODO: Lọc lại dữ liệu trong RecyclerView Adapter tại đây
            Toast.makeText(this, "Switched to $titleText", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetButtonStyle(btn: TextView) {
        btn.background = ContextCompat.getDrawable(this, R.drawable.bg_chip_inactive)
        btn.setTextColor(Color.WHITE)
    }
}