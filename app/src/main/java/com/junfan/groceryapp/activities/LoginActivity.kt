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
import com.google.gson.Gson
import com.junfan.groceryapp.R
import com.junfan.groceryapp.app.Endpoints
import com.junfan.groceryapp.models.User
import com.junfan.groceryapp.models.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        button_login_login.setOnClickListener {
            var sharedPreference = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()

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
                    Log.d("abcd", it.toString())

                    editor.putString("token", it.getString("token"))
                    editor.putString("firstName",it.getJSONObject("user").getString("firstName"))
                    editor.putString("email",it.getJSONObject("user").getString("email"))
                    editor.putString("id",it.getJSONObject("user").getString("_id"))
                    editor.putString("mobile",it.getJSONObject("user").getString("mobile"))
                    editor.putBoolean("isLoggedIn", true)
                    editor.commit()

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