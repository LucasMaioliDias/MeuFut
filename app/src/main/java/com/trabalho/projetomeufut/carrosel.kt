package com.trabalho.projetomeufut

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class carrosel : AppCompatActivity() {


    private var currentPage = 0
    private var imageHandler = Handler(Looper.getMainLooper())
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrosel)

        viewPager = findViewById<ViewPager>(R.id.view_pager)
        val dotsLayout = findViewById<LinearLayout>(R.id.dots_layout)

        val images = listOf(
            R.drawable.logo,
            R.drawable.img6,
            R.drawable.img4,
            R.drawable.img5


        )

        val adapter = ImagePagerAdapter(images, this)
        viewPager.adapter = adapter

        val dots = mutableListOf<ImageView>()
        for (i in images.indices) {
            val dot = ImageView(this)
            dot.setImageResource(R.drawable.dot_indicator_selected)
            dots.add(dot)
            dotsLayout.addView(dot)
        }

        dots[0].setImageResource(R.drawable.dot_indicator_selected)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                currentPage = position
                for (i in 0 until dots.size) {
                    if (i == currentPage) {
                        dots[i].setImageResource(R.drawable.dot_indicator_selected)
                    } else {
                        dots[i].setImageResource(R.drawable.dot_indicator)
                    }
                }
            }
        })

        // Passa automaticamente as imagens a cada 2 segundos
        val imageRunnable = object : Runnable {
            override fun run() {
                currentPage = (currentPage + 1) % images.size
                viewPager.setCurrentItem(currentPage, true)

                imageHandler.postDelayed(this, 6000)
            }
        }
        imageHandler.postDelayed(imageRunnable, 6000)
    }

    class ImagePagerAdapter(private val images: List<Int>, private val context: Context) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(context)
            imageView.setImageResource(images[position])
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return images.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
    }
}