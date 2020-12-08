package com.r.foodproject.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.r.foodproject.R
import com.r.foodproject.ui.model.DatabaseHelper
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val db=DatabaseHelper(this)

        val data=intent
        val id=data.getIntExtra("id",-1)

        tv_name.text= data.getStringExtra("name")
        tv_description.text= data.getStringExtra("description")
        tv_num.text= data.getStringExtra("amout")
        tv_price.text= data.getStringExtra("price")
        img_product.setImageResource(data.getIntExtra("img",-1))
            //product
        save.setOnClickListener {
          val b =  db.save(id)
            if(b)
            Toast.makeText(this,"Saved Successfully",Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this,"Save Failed",Toast.LENGTH_LONG).show()
        }
        //cart
        btn_cart.setOnClickListener {
      //      val v=db.addOrder(id)
            val i= Intent(this,LoginActivity::class.java)
            startActivity(i)
        }
    }
}
