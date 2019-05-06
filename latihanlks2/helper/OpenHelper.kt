package com.ukk.latihanlks2.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ukk.latihanlks2.model.SQL
import org.jetbrains.anko.db.*

class OpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "resto.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(SQL.TABLE, true,
            SQL.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            SQL.NAME to TEXT,
            SQL.PRICE to INTEGER,
            SQL.CARBO to INTEGER,
            SQL.PROTEIN to INTEGER,
            SQL.IMG to TEXT,
            SQL.QTY to INTEGER
            )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(SQL.TABLE, true)
    }

    companion object {
        private var instance: OpenHelper? = null
        fun getInstance(ctx: Context): OpenHelper{
            if(instance == null){
                instance = OpenHelper(ctx.applicationContext)
            }
            return instance  as OpenHelper
        }
    }
}
val Context.database : OpenHelper
get() = OpenHelper.getInstance(applicationContext)