package com.junfan.groceryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.junfan.groceryapp.models.Product

class DBHelper(var mContext: Context): SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    var db: SQLiteDatabase = writableDatabase

    companion object {
        const val DATABASE_NAME = "mydb"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "product"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_MRP = "mrp"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table $TABLE_NAME ($COLUMN_NAME varchar(100), $COLUMN_PRICE float, $COLUMN_IMAGE varchar(100), " +
                "$COLUMN_MRP, float)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun addProduct(product: Product) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, product.productName)
        contentValues.put(COLUMN_PRICE, product.price)
        contentValues.put(COLUMN_IMAGE, product.image)
        contentValues.put(COLUMN_MRP, product.mrp)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun deleteProduct(product: Product): Int {
        var whereClause = "$COLUMN_NAME = ?"
        var whereArgs = arrayOf(product.productName.toString())
        return db.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getAllProduct(): ArrayList<Product> {
        var list: ArrayList<Product> = ArrayList()
        var columns = arrayOf(
            COLUMN_NAME,
            COLUMN_PRICE,
            COLUMN_IMAGE,
            COLUMN_MRP
        )
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()) {
            do {
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var mrp = cursor.getFloat(cursor.getColumnIndex(COLUMN_MRP))

                var product = Product(null, null, null, null, null, image, mrp, null,
                price, name, null, null, null, null)
                list.add(product)
            }while(cursor.moveToNext())
        }
        return list
    }

}