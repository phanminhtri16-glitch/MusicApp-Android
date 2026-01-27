package com.example.yuxiaofy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Import các view binding hoặc findViewById

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Lưu ý: Nhớ đổi setContentView trong AndroidManifest.xml để chạy HomeActivity trước
        setContentView(R.layout.activity_home)

        // 1. Setup RecyclerView cho danh sách "For You"
        // Sử dụng StaggeredGridLayoutManager để tạo hiệu ứng so le "độc lạ"
        val rvSongs = findViewById<RecyclerView>(R.id.rvSongs)
        rvSongs.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // rvSongs.adapter = ... (Bạn cần tạo Adapter riêng cho danh sách bài hát)

        // 2. Xử lý sự kiện click vào thẻ Hero (Featured Song)
        // Ví dụ: Click vào ảnh "Unity" thì mở màn hình Player cũ
        val heroCard =
            findViewById<androidx.cardview.widget.CardView>(R.id.cvPlay) // Hoặc ID tương ứng trong layout home
        // Trong layout trên tôi chưa đặt ID cho CardView featured, hãy thêm id="@+id/cardFeatured"

        // Code giả lập chuyển màn hình
        /*
        cardFeatured.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        */
    }
}