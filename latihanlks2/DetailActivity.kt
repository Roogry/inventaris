package com.ukk.latihanlks2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ukk.latihanlks2.helper.database
import com.ukk.latihanlks2.model.Cart
import com.ukk.latihanlks2.model.Menu
import com.ukk.latihanlks2.model.SQL
import com.ukk.latihanlks2.utils.convertRp
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.update
import org.jetbrains.anko.startActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        menu = intent.getParcelableExtra("menu")
        initLayout()

        mvCart.setOnClickListener {
            startActivity<CartActivity>()
        }

        btnAdd.setOnClickListener {
            addCart()
            startActivity<CartActivity>()
        }
    }

    private fun addCart() {
        database.use {
            val cart = select(SQL.TABLE)
                .whereArgs("${SQL.NAME} = {name}", "name" to menu.name)
                .parseList(classParser<Cart>())

            val isInsert = cart.isEmpty()

            if (isInsert) {
                insertData()
            } else {
                update(SQL.TABLE, SQL.QTY to cart[0].qty + 1)
                    .whereArgs("${SQL.ID} = {id}", "id" to cart[0].id)
                    .exec()
            }
        }
    }

    private fun insertData() {
        database.use {
            insert(
                SQL.TABLE,
                SQL.NAME to menu.name,
                SQL.PRICE to menu.price,
                SQL.CARBO to menu.carbo,
                SQL.PROTEIN to menu.protein,
                SQL.IMG to menu.img,
                SQL.QTY to 1
            )
        }
    }

    private fun initLayout() {
        txtName.text = menu.name
        txtPrice.text = convertRp(menu.price)
        txtCarbo.text = menu.carbo.toString()
        txtProtein.text = menu.protein.toString()

        Glide.with(this)
            .load(resources.getDrawable(resources.getIdentifier(menu.img, "drawable", packageName)))
            .into(mvMenu)
    }
}
