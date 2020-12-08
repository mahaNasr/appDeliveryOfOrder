package  com.r.foodproject.ui.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.r.foodproject.R
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var db: SQLiteDatabase
    init {
        db = writableDatabase
    }

    //called just one
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(Product.CREATE_TABLE)
        p0.execSQL(Categories.CREATE_TABLE)
        p0.execSQL(Users.CREATE_TABLE)
//        p0!!.execSQL(Orders.CREATE_TABLE)
        //  val db=DatabaseHelper(context = Context)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS ${Product.TABLE_NAME}")
        onCreate(p0)
    }
    //==================================================================

    fun addProduct(name: String, description: String, prodAmount: Int, price: Double, is_fav: Int, image: String,
        rate: Int, id_categ: Int): Boolean {
        val cv = ContentValues()
        cv.put(Product.COL_NAME, name)
        cv.put(Product.COL_DESC, description)
        cv.put(Product.COL_AMOUNT, prodAmount)
        cv.put(Product.COL_PRICE, price)
        cv.put(Product.COL_ISFAV, is_fav)
        cv.put(Product.COL_IMG, image)
        cv.put(Product.COL_RATE, rate)
        cv.put(Product.COL_ID_CATEGORIES, id_categ)
        return db.insert(Product.TABLE_NAME, null, cv) > 0
    }

    fun save(id:Int):Boolean{
        val cv = ContentValues()
        cv.put(Product.COL_ISFAV, 1)
        Log.e("save",cv.toString())
        return db.update(Product.TABLE_NAME, cv, "id = $id", null) > 0
    }

    fun getFavourite(): ArrayList<Product> {

     val products = ArrayList<Product>()
        val c =
            db.rawQuery("select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ISFAV}=1  order by ${Product.COL_ID} DESC", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val prod = Product(
                c.getInt(0), c.getString(1), c.getString(2),
                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
            )
            products.add(prod)
            c.moveToNext()
        }
        c.close()
        return products
    }

    fun getAllProducts(): ArrayList<Product> {
        val products = ArrayList<Product>()
        val c =
            db.rawQuery("select * from ${Product.TABLE_NAME} order by ${Product.COL_ID} DESC", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val prod = Product(
                c.getInt(0), c.getString(1), c.getString(2),
                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
            )
            products.add(prod)
            c.moveToNext()
        }
        c.close()
        return products
    }
    fun getBurger(): ArrayList<Product> {
        val products = ArrayList<Product>()
        val c =
            db.rawQuery("select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ID_CATEGORIES}=0  order by ${Product.COL_ID}   DESC",null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val prod = Product(
                c.getInt(0), c.getString(1), c.getString(2),
                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
            )
            products.add(prod)
            c.moveToNext()
        }
        c.close()
        return products
    }
    fun getPizza(): ArrayList<Product> {
        val products = ArrayList<Product>()
        val c =
            db.rawQuery("select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ID_CATEGORIES}=1  order by ${Product.COL_ID}   DESC",null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val prod = Product(
                c.getInt(0), c.getString(1), c.getString(2),
                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
            )
            products.add(prod)
            c.moveToNext()
        }
        c.close()
        return products
    }

    fun getDessert(): ArrayList<Product> {
        val products = ArrayList<Product>()
        val c =
            db.rawQuery("select * from ${Product.TABLE_NAME} WHERE ${Product.COL_ID_CATEGORIES}=2  order by ${Product.COL_ID}   DESC",null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val prod = Product(
                c.getInt(0), c.getString(1), c.getString(2),
                c.getInt(3), c.getInt(4), c.getDouble(5), c.getInt(6), c.getInt(7), c.getInt(8)
            )
            products.add(prod)
            c.moveToNext()
        }
        c.close()
        return products
    }

    fun userAuth(email: String, password: String) : Int{
        var i:Int = -1
        val selectionArgs = arrayOf<String>(email, password)
        val c =
            db.rawQuery("select * from ${Users.TABLE_NAME} where ${Users.COL_EMAIL}=? and ${Users.COL_PASSWORD}=?", selectionArgs)
        c.moveToFirst()
        if(!c.isAfterLast){
            i = c.getInt(0)
        }
        return if(i > 0){
            i
        } else {
            -1
        }
    }



    fun deleteProduct(id: Int): Boolean {
       // return db.delete(Product.TABLE_NAME, "id = $id", null) > 0
    return true
    }

    fun updateProduct(oldId: Int, name: String, des: String): Boolean {
        val cv = ContentValues()
        cv.put(Product.COL_NAME, name)
        cv.put(Product.COL_DESC, des)
        return db.update(Product.TABLE_NAME, cv, "id = $oldId", null) > 0
    }

    /////////// categories
    fun addCategory(name: String): Boolean {
        val cv = ContentValues()
        cv.put(Categories.COL_NAME, name)
        return db.insert(Categories.TABLE_NAME, null, cv) > 0
    }
   /////////users

    fun addUser(image: String, name: String, email: String, password: String ): Boolean {
        val cv = ContentValues()
        cv.put(Users.COL_IMG, image)
        cv.put(Users.COL_NAME, name)
        cv.put(Users.COL_EMAIL, email)
        cv.put(Users.COL_PASSWORD, password)

        return db.insert(Users.TABLE_NAME, null, cv) > 0
    }
//    fun getUser(): ArrayList<Users> {
//        val users = ArrayList<Users>()
//        val c =db.rawQuery(
//                "select * from ${Users.TABLE_NAME}  order by ${Product.COL_ID}   DESC", null)
//        c.moveToFirst()
//            while (!c.isAfterLast) {
//                val prod = Users(c.getInt(0))>0
//               //  users.add(prod)
//              //  c.moveToNext()
//            }
//        c.close()
//        return users
//    }
    /////////orders
    fun addOrder(id:Int ,id_prod: Int, user_id:Int, quantity:Int, total_price:Double):Boolean{
        val cv = ContentValues()
        cv.put(Orders.COL_ID, id)
        cv.put(Orders.COL_PRODUCT_ID, id_prod)
        cv.put(Orders.COL_USER_ID, user_id)
        cv.put(Orders.COL_Quantity, quantity)
        cv.put(Orders.COL_TOTAL_PRICE, total_price)

        return db.insert(Orders.TABLE_NAME, null, cv) > 0
    }
    companion object {
        const val DATABASE_NAME = "FoodDB"
        const val DATABASE_VERSION = 1
    }




}