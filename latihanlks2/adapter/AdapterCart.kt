package com.ukk.latihanlks2.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ukk.latihanlks2.R
import com.ukk.latihanlks2.helper.database
import com.ukk.latihanlks2.model.Cart
import com.ukk.latihanlks2.model.SQL
import com.ukk.latihanlks2.utils.convertRp
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.update
import org.jetbrains.anko.toast

class AdapterCart(private val ctx: Context, private val listCart: ArrayList<Cart>, val listener: (Cart) -> Unit): RecyclerView.Adapter<AdapterCart.CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_cart, parent, false))
    }

    override fun getItemCount(): Int = listCart.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindView(listCart[position], ctx, listener)
    }

    class CartViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtQty: TextView = view.findViewById(R.id.txtQty)
        val mvMenu: ImageView = view.findViewById(R.id.mvMenu)
        val mvAdd: ImageView = view.findViewById(R.id.mvAdd)
        val mvRemove: ImageView = view.findViewById(R.id.mvRemove)
        val mvDelete: ImageView = view.findViewById(R.id.mvDelete)

        fun bindView(cart: Cart, ctx: Context, listener: (Cart) -> Unit){
            txtName.text = cart.name
            txtQty.text = cart.qty.toString()
            txtPrice.text = convertRp(cart.price)

            Glide.with(ctx)
                    .load(ctx.resources.getDrawable(ctx.resources.getIdentifier(cart.img, "drawable", ctx.packageName)))
                    .into(mvMenu)

            mvAdd.setOnClickListener {
                var qty = txtQty.text.toString().toInt()
                qty++
                txtQty.text = qty.toString()
                putQty(ctx, cart)
                listener(cart)
            }

            mvRemove.setOnClickListener {
                var qty = txtQty.text.toString().toInt()
                if(qty != 1){
                    qty--
                    txtQty.text = qty.toString()
                    putQty(ctx, cart)
                    listener(cart)
                }else{
                    ctx.toast("Jumlah tidak boleh kurang dari 1")
                }
            }

            mvDelete.setOnClickListener {
                ctx.database.use {
                    delete(SQL.TABLE, "${SQL.ID} = {id}", "id" to cart.id)
                    listener(cart)
                }
            }
        }

        private fun putQty(ctx: Context, cart: Cart) {
            ctx.database.use {
                update(SQL.TABLE, SQL.QTY to txtQty.text)
                        .whereArgs("${SQL.ID} = {id}", "id" to cart.id)
                        .exec()
            }
        }
    }
}