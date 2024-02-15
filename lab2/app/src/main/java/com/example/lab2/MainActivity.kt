package com.example.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val adapter = TabPagerAdapter(supportFragmentManager)

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }
}

class TabPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabFragments = listOf(TabFragment1(), TabFragment2(), TabFragment3())

    override fun getItem(position: Int): Fragment {
        return tabFragments[position]
    }

    override fun getCount(): Int {
        return tabFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "Вкладка 1"
            1 -> "Вкладка 2"
            2 -> "Вкладка 3"
            else -> "Вкладка"
        }
    }
}


class TabFragment1 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_tab1, container, false)

        return view
    }
}

/*class TabFragment2 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_tab2, container, false)
        val listItems = arrayOf("Кот", "Собака", "Рыбки", "Хомяк", "Хамелион")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)

        val listView = ListView(requireContext())
        listView.adapter = adapter

        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.addView(listView)

        return view
    }
}*/

class TabFragment2 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_tab2, container, false)
        val listItems = arrayOf("Кот", "Собака", "Рыбки", "Хомяк", "Хамелион")

        val adapter = object: ArrayAdapter<String>(requireContext(), R.layout.list_item_layout, listItems){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var listItemView = convertView
                if(listItemView == null){
                    listItemView = layoutInflater.inflate(R.layout.list_item_layout, null)
                }

                val textView = listItemView?.findViewById<TextView>(R.id.textView)
                val imageView1 = listItemView?.findViewById<ImageView>(R.id.imageView1)
                val imageView2 = listItemView?.findViewById<ImageView>(R.id.imageView2)
                val imageView3 = listItemView?.findViewById<ImageView>(R.id.imageView3)
                val imageView4 = listItemView?.findViewById<ImageView>(R.id.imageView4)
                val imageView5 = listItemView?.findViewById<ImageView>(R.id.imageView5)

                textView?.text = getItem(position) // Устанавливаем текст

                // Устанавливаем изображения
                when(position){
                    0 -> imageView1?.setImageResource(R.drawable.cat_png)
                    1 -> imageView2?.setImageResource(R.drawable.dog_png)
                    2 -> imageView3?.setImageResource(R.drawable.fish_png)
                    3 -> imageView4?.setImageResource(R.drawable.hamster_png)
                    4 -> imageView5?.setImageResource(R.drawable.hamel_png)
                    // Добавьте другие изображения здесь в том же формате
                }

                return listItemView!!
            }
        }

        val listView = ListView(requireContext())
        listView.adapter = adapter

        val linearLayout = view.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.addView(listView)

        return view
    }
}


class TabFragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_tab3, container, false)
        val tableLayout = TableLayout(requireContext())

        for (i in 1..3) {
            val tableRow = TableRow(requireContext())


            val weatherImage = ImageView(requireContext())
            val randomWeatherImage = getWeatherImage()
            weatherImage.setImageResource(randomWeatherImage)

            val params = TableRow.LayoutParams(50, 50)
            weatherImage.layoutParams = params
            tableRow.addView(weatherImage)

            val temperatureTextView = TextView(requireContext())
            val randomTemp = generateRandomTemperature()
            temperatureTextView.text = "Температура: $randomTemp °C"
            tableRow.addView(temperatureTextView)

            tableLayout.addView(tableRow)
        }

        return tableLayout
    }

    private fun getWeatherImage(): Int {
        val randomImages = arrayOf(R.drawable.sunny, R.drawable.cloudy, R.drawable.rainy)
        return randomImages.random()
    }

    private fun generateRandomTemperature(): Int {

        return (Random.nextInt(-20, 40))
    }
}


/*class TabFragment3 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_tab3, container, false)
        val tableLayout = TableLayout(requireContext())

        for (i in 1..2) {
            val tableRow = TableRow(requireContext())
            val textView1 = TextView(requireContext())
            val textView2 = TextView(requireContext())
            val params1 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            textView1.layoutParams = params1
            textView2.layoutParams = params1

            if(i == 1){
                textView1.text = "Денис"
                textView2.text = "Кузьменок"
            }
            if(i == 2){
                textView1.text = "Михаил"
                textView2.text = "Звягинцев"
            }
            if(i == 3){
                textView1.text = "Никита"
                textView2.text = "Патрушев"
            }


            tableRow.addView(textView1)
            tableRow.addView(textView2)
            tableLayout.addView(tableRow)
        }

        return tableLayout
    }
}*/