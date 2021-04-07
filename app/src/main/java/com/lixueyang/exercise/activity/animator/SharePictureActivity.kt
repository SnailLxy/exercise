package com.lixueyang.exercise.activity.animator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lixueyang.exercise.databinding.ActivitySharePictureBinding

class SharePictureActivity : AppCompatActivity() {

    lateinit var bind: ActivitySharePictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySharePictureBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }
}