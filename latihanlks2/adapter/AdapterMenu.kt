package com.ukk.latihanlks2.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ukk.latihanlks2.DetailActivity
import com.ukk.latihanlks2.R
import com.ukk.latihanlks2.model.Menu
import com.ukk.latihanlks2.utils.convertRp
import org.jetbrains.anko.startActivity

class AdapterMenu(private val ctx: Context, private val listMenu: ArrayList<Menu>): RecyclerView.Adapter<AdapterMenu.MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_menu, parent, false))
    }

    override fun getItemCount(): Int = listMenu.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindView(listMenu[position], ctx)
    }

    class MenuViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val txtCarbo: TextView = view.findViewById(R.id.txtCarbo)
        val txtProtein: TextView = view.findViewById(R.id.txtProtein)
        val mvMenu: ImageView = view.findViewById(R.id.mvMenu)

        fun bindView(menu: Menu, ctx: Context){
            txtName.text = menu.name
            txtPrice.text = convertRp(menu.price)
            txtCarbo.text = menu.carbo.toString()
            txtProtein.text = menu.protein.toString()

            Glide.with(ctx)
                    .load(ctx.resources.getDrawable(ctx.resources.getIdentifier(menu.img, "drawable", ctx.packageName)))
                    .into(mvMenu)

            view.setOnClickListener {
                ctx.startActivity<DetailActivity>("menu" to menu)
            }
        }
    }
}