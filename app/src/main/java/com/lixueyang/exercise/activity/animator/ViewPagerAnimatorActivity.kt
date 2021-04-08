package com.lixueyang.exercise.activity.animator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
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
        initViewPager()
    }

    private fun initViewPager() {
        val tabLayout = binding.contentViewPagerAnimator.tlAnimator
        val vpAnimator = binding.contentViewPagerAnimator.vpAnimator
        val viewPageAnimatorAdapter: ViewPageAnimatorAdapter = ViewPageAnimatorAdapter(this)
        vpAnimator.adapter = viewPageAnimatorAdapter
        vpAnimator.setPageTransformer(ZoomOutPageTransformer())
        //        binding.contentViewPagerAnimator.vpAnimator.setPageTransformer(DepthPageTransformer())
        TabLayoutMediator(tabLayout, vpAnimator) { tab, position ->
            tab.text = "第" + position + "页"
        }.attach()
        vpAnimator.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT  //指初始化当前item，懒加载
        vpAnimator.currentItem = 2
        vpAnimator.isUserInputEnabled = true   //启用用户启动的滚动
//        viewPageAnimatorAdapter.notifyDataSetChanged()
    }

    private inner class ViewPageAnimatorAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 5
        }

        override fun createFragment(position: Int): Fragment {
            return if (position % 2 == 0) {
                FirstFragment()
            } else {
                SecondFragment()
            }
        }

    }
}