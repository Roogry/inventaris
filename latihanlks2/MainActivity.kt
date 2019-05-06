package com.ukk.latihanlks2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ukk.latihanlks2.adapter.AdapterMenu
import com.ukk.latihanlks2.model.Menu
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var listMenu: ArrayList<Menu> = ArrayList()
    private lateinit var  adapter: AdapterMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initLayout()

        mvCart.setOnClickListener {
            startActivity<CartActivity>()
        }
    }

    private fun initData() {
        listMenu.clear()
        listMenu.add(Menu("Risotto", 50000, 150, 50, "m1"))
        listMenu.add(Menu("Lasagna", 55000, 150, 200, "m2"))
        listMenu.add(Menu("Mac and Chesse", 60000, 150, 75, "m3"))
        listMenu.add(Menu("French Fries", 20000, 120, 70, "m4"))
        listMenu.add(Menu("Cheese Burger", 35000, 50, 120, "m5"))
        listMenu.add(Menu("Iced Coffee", 18000, 150, 80, "m6"))
        listMenu.add(Menu("Matcha Latte", 21000, 75, 75, "m7"))
        listMenu.add(Menu("Pepperoni Pizza", 50000, 160, 100, "m8"))
    }

    private fun initLayout() {
        adapter = AdapterMenu(this, listMenu)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
