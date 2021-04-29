package com.junfan.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Endpoints
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }

    private fun init() {

        text_view_register.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        button_register_register.setOnClickListener {
            var firstName = edit_text_firstname_register.text.toString()
            var email = edit_text_email_register.text.toString()
            var password = edit_text_password_register.text.toString()
            var mobile = edit_text_mobile_register.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("firstName", firstName)
            jsonObject.put("email", email)
            jsonObject.put("password", password)
            jsonObject.put("mobile", mobile)

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getRegister(),
                jsonObject,
                Response.Listener {
                    Toast.makeText(
                        applicationContext,
                        "Successfully registered",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("abc", it.toString())
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, "email has been registered", Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(jsonRequest)

        }
    }
}