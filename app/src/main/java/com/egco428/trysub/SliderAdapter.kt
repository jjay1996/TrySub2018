package com.egco428.trysub

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.slide_layout.*


class SliderAdapter : PagerAdapter {

    var objects:QuestionAnswer = QuestionAnswer()
    var context: Context? = null
    var layoutInflater: LayoutInflater? = null
    var nowlevel:Int = 0
    var slide_headings = arrayOf("")
    var slide_descs = arrayOf("")

    constructor(context: Context,level:Int){

        this.context = context
        nowlevel = level

        slide_headings = objects.MyQuestion[nowlevel]
        slide_descs = objects.CorrectAnswer[nowlevel]



    }

    override fun getCount(): Int {
        return slide_headings.size;
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        //สไลด์หน้าจอ
        layoutInflater = context!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slide_layout, container, false)

        val slideHeading = view.findViewById(R.id.wordTextView) as TextView
        val slideDescription = view.findViewById(R.id.slide_desc) as TextView

        slideHeading.text = slide_headings[position]
        slideDescription.text = slide_descs[position]

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as RelativeLayout)
        //super.destroyItem(container, position, `object`)
    }
}