package com.junfan.groceryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.session.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        Picasso
            .get()
            .load("https://assets-2.placeit.net/smart_templates/8c61e9e18bf03a34f81759dd8a685aec/assets/preview_a637ca6e4da6e38f03eb0d650ac5d6ba.jpg")
            .into(image_view_login)

        init()
    }

    private fun init() {

        text_view_login.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        button_login_login.setOnClickListener {

            var email = edit_text_email_login.text.toString()
            var password = edit_text_password_login.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password", password)

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLogin(),
                jsonObject,
                Response.Listener {
                    Toast.makeText(applicationContext, "Successfully Login", Toast.LENGTH_SHORT).show()
                    sessionManager = SessionManager(this)
                    var user = it.getJSONObject("user")
                    sessionManager.putInfo(user,it)

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, "Your credential is not correct", Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(jsonRequest)
        }
    }
}