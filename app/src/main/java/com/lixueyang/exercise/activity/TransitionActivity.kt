package com.lixueyang.exercise.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.transition.Transition.TransitionListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.transition.addListener
import androidx.core.view.marginTop
import com.lixueyang.exercise.R
import com.lixueyang.exercise.databinding.ActivityTransitionBinding

class TransitionActivity : AppCompatActivity() {
    lateinit var binding: ActivityTransitionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransitionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        addViewTransition()
    }

    private fun initView() {
        val sceneOne: Scene = Scene.getSceneForLayout(binding.flSceneRoot, R.layout.layout_scene_one, this)
        val sceneTwo: Scene = Scene.getSceneForLayout(binding.flSceneRoot, R.layout.layout_scene_two, this)
        var fadeTransition: Transition = TransitionInflater
                .from(this)
                .inflateTransition(R.transition.fade_transition)

        binding.btnTransition.setOnClickListener {
            binding.tvTransition.text = "点击成功"
            TransitionManager.go(sceneTwo, fadeTransition)
        }
    }

    private fun addViewTransition() {
        val labelText = TextView(this).apply {
            text = "Label"
            setTextColor(resources.getColor(R.color.c_features_normal))
        }
        var fadeTransitionKt: Transition = Fade()
        TransitionManager.beginDelayedTransition(binding.flSceneRoot, fadeTransitionKt)
        binding.flSceneRoot.addView(labelText)
    }
}