package com.ukk.latihanlks2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.ukk.latihanlks2.adapter.AdapterCart
import com.ukk.latihanlks2.helper.database
import com.ukk.latihanlks2.model.Cart
import com.ukk.latihanlks2.model.SQL
import com.ukk.latihanlks2.utils.convertRp
import de.cketti.mailto.EmailIntentBuilder
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.dialog_checkout.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class CartActivity : AppCompatActivity() {

    private var listCart: ArrayList<Cart> = ArrayList()
    private lateinit var adapter: AdapterCart
    private var ttlHrg: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        initLayout()
        swipe.onRefresh {
            getCart()
        }

        btnCheckout.setOnClickListener {
            if(isCartNotEmpty()){
                val view = LayoutInflater.from(this).inflate(R.layout.dialog_checkout, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)
                val alert = builder.create()
                alert.show()

                view.btnEmail.setOnClickListener {
                    EmailIntentBuilder.from(this)
                            .subject("Order Item")
                            .body(getMessage())
                            .start()
                }

                view.btnSms.setOnClickListener {
                    val uri = Uri.parse("smsto:")
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra("sms_body", getMessage())
                    startActivity(intent)
                }
            }
        }
    }

    private fun isCartNotEmpty(): Boolean {
        if(ttlHrg == 0){
            toast("Orderan anda masih kosong")
            return false
        }else{
            return true
        }
    }

    private fun initLayout() {
        adapter = AdapterCart(this, listCart) {
            getCart()
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        getCart()
    }

    private fun getCart() {
        listCart.clear()
        database.use {
            val cart = select(SQL.TABLE)
                    .parseList(classParser<Cart>())

            listCart.addAll(cart)
        }
        adapter.notifyDataSetChanged()
        getTotalHarga()
        swipe.isRefreshing = false
    }

    private fun getTotalHarga() {
        ttlHrg = 0
        for (i in listCart.indices) {
            ttlHrg += listCart[i].qty * listCart[i].price
        }
        txtTotal.text = convertRp(ttlHrg)
    }

    private fun getMessage(): String {
        var message = "Total harga : " + convertRp(ttlHrg) + ",-\n"
        for (i in listCart.indices) {
            message += "\n${listCart[i].name} ${ listCart[i].qty}pcs"
        }
        return message
    }
}
