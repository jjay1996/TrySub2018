package com.egco428.trysub

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.LinearLayout
import android.support.v4.view.ViewPager
import android.text.Html
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_learn_word.*
import java.text.FieldPosition
import java.util.*


class LearnWordActivity : AppCompatActivity() {
    private var mDots: ArrayList<TextView>? = ArrayList()
    private var mSlideViewPager: ViewPager? = null
    private var mDotLayout: LinearLayout? = null
    private var viewPager:ViewPager? = null

    private var sliderAdapter: SliderAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

        var nolevel = intent.getStringExtra("LevelLearn").toInt()
        //var nolevel = 9

        mSlideViewPager = findViewById(R.id.slideViewPager) as ViewPager
        mDotLayout = findViewById(R.id.dotsLayout) as LinearLayout

        sliderAdapter = SliderAdapter(this, nolevel);

        mSlideViewPager!!.adapter = sliderAdapter

        addDotsIndicator(0)

        //mSlideViewPager.addOnPageChangeListener(view)

//
        mSlideViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                addDotsIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


    }

    //จุดบอกหน้า
    fun addDotsIndicator(position:Int) {

        mDotLayout!!.removeAllViews()

        for (i in 1..10) {
            mDots!!.add(TextView(this))
            mDots!![i - 1].setTextSize(36f)
            mDots!![i - 1].setText(Html.fromHtml("&#8226"))
            mDots!![i - 1].setTextColor(Color.GRAY)
            mDotLayout!!.addView(mDots!![i - 1])
        }

        if (mDots!!.size > 0){
            mDots!![position].setTextColor(Color.WHITE)
        }
    }





}
