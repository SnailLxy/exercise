package com.lixueyang.exercise.activity.animator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lixueyang.exercise.R
import com.lixueyang.exercise.databinding.ActivityViewPagerAnimatorBinding
import com.lixueyang.exercise.fragment.FirstFragment
import com.lixueyang.exercise.fragment.SecondFragment
import com.lixueyang.exercise.widgets.DepthPageTransformer
import com.lixueyang.exercise.widgets.ZoomOutPageTransformer

class ViewPagerAnimatorActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPagerAnimatorBinding

    fun startViewPagerAnimatorActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, ViewPagerAnimatorActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerAnimatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.contentViewPagerAnimator.vpAnimator.adapter = ViewPageAnimatorAdapter(supportFragmentManager)
        binding.contentViewPagerAnimator.vpAnimator.setPageTransformer(true, ZoomOutPageTransformer())
//        binding.contentViewPagerAnimator.vpAnimator.setPageTransformer(true, DepthPageTransformer())
    }

    private inner class ViewPageAnimatorAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                FirstFragment()
            } else {
                SecondFragment()
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}