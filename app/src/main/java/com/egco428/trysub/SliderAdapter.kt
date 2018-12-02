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


class SliderAdapter : PagerAdapter {

    var objects:QuestionAnswer = QuestionAnswer()
    var context: Context? = null
    var layoutInflater: LayoutInflater? = null
    var a:Int = 0
    var slide_headings = arrayOf("")
    var slide_descs = arrayOf("")

    constructor(context: Context,level:Int){

        this.context = context
        a = level

        slide_headings = objects.MyQuestion[a]
        slide_descs = objects.CorrectAnswer[a]

    }
    //Arrays
    //var slide_images = arrayOf(R.drawable.eat_icon, R.drawable.sleep_icon, R.drawable.code_icon)
//    var slide_headings = arrayOf("Swag", "Swah", "Swahing on Sesilien ")
//    var slide_descs = arrayOf("Pocodomäne der Superwohnmarkt", "Real, einmal hin alles drin.", "Wie? Wo? Was? Weiß Obi?!")

    override fun getCount(): Int {
        return slide_headings.size;
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //return super.instantiateItem(container, position)
        layoutInflater = context!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //layoutInflater = Context.LAYOUT_INFLATER_SERVICE as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slide_layout, container, false)

        //val slideImageView = view.findViewById(R.id.slide_image) as ImageView
        val slideHeading = view.findViewById(R.id.wordTextView) as TextView
        val slideDescription = view.findViewById(R.id.slide_desc) as TextView

        //slideImageView.setImageResource(slide_images[position])
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