package com.junfan.groceryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.junfan.groceryapp.models.OrderSummary
import com.junfan.groceryapp.models.Product
import com.junfan.groceryapp.session.SessionManager

class DBHelper(var mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    var db: SQLiteDatabase = writableDatabase
    var sessionManager = SessionManager(mContext)

    companion object {
        const val DATABASE_NAME = "mydb"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "product"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_COUNT = "count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "create table $TABLE_NAME ($COLUMN_ID varchar(100), $COLUMN_NAME varchar(100), $COLUMN_PRICE float, $COLUMN_IMAGE varchar(100), " +
                    "$COLUMN_MRP float, $COLUMN_COUNT integer)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun addProduct(product: Product) {
        if(!productExist(product)) {
            var contentValues = ContentValues()
            product.quantity = 1
            contentValues.put(COLUMN_ID, product._id)
            contentValues.put(COLUMN_NAME, product.productName)
            contentValues.put(COLUMN_PRICE, product.price)
            contentValues.put(COLUMN_IMAGE, product.image)
            contentValues.put(COLUMN_MRP, product.mrp)
            contentValues.put(COLUMN_COUNT, product.quantity)
            db.insert(TABLE_NAME, null, contentValues)
        }else{
            product.quantity++
            updateQuantity(product)
        }
    }

    fun isCartEmpty(): Boolean {
        var query = "select * from ${TABLE_NAME}"
        var cursor = db.rawQuery(query, null)
        return cursor.count == 0
    }

    private fun updateQuantity(product: Product) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_COUNT, product.quantity)
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)
        db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun incrementQuantity(product: Product): Int {
        var count = product.quantity + 1
        var contentValues = ContentValues()
        contentValues.put(COLUMN_COUNT, count)
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)

        return db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun decrementQuantity(product: Product): Int {
        var count = product.quantity - 1
        var contentValues = ContentValues()
        contentValues.put(COLUMN_COUNT, count)
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)
        if(count <= 0) {
            return db.delete(TABLE_NAME, whereClause, whereArgs)
        }else{
            return db.update(TABLE_NAME, contentValues, whereClause, whereArgs)
        }

    }

    fun deleteProduct(product: Product): Int {
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)
        return db.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getAllProduct(): ArrayList<Product> {
        var list: ArrayList<Product> = ArrayList()
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PRICE,
            COLUMN_IMAGE,
            COLUMN_MRP,
            COLUMN_COUNT
        )
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var mrp = cursor.getFloat(cursor.getColumnIndex(COLUMN_MRP))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT))

                var product = Product(
                    null, id, null, null, null, image, mrp, null,
                    price, name, quantity, null, null, null
                )
                list.add(product)
            } while (cursor.moveToNext())
        }
        return list
    }

    private fun productExist(product: Product): Boolean {
        var query = "select * from $TABLE_NAME where $COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)
        var cursor = db.rawQuery(query, whereArgs)
        var count = cursor.count
        return count != 0
    }

    fun calculation(): OrderSummary {

        var total = 0f
        var subtotal = 0f

        var mList = getAllProduct()

        for(product in mList) {
            total += product.mrp!! * product.quantity
            subtotal += product.price!! * product.quantity
        }

        var deliveryFee = if(total >= 300)  0f else 30f
        var discount = total - subtotal
        var finalPrice = total - discount + deliveryFee


        return OrderSummary(deliveryFee, discount, finalPrice, total, finalPrice)
    }
}