package com.junfan.groceryapp.session

import android.content.Context
import com.google.gson.JsonObject
import org.json.JSONObject

class SessionManager(val mContext: Context) {

    private val FILE_NAME = "my_pref"
    private val KEY_FIRSTNAME = "firstName"
    private val KEY_EMAIL = "email"
    private val KEY_ID = "id"
    private val KEY_MOBILE = "mobile"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    private val KEY_TOKEN = "token"

    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun putInfo(user: JSONObject, it: JSONObject) {
        editor.putString(KEY_TOKEN, it.getString(KEY_TOKEN))
        editor.putString(KEY_FIRSTNAME, user.getString(KEY_FIRSTNAME))
        editor.putString(KEY_EMAIL, user.getString(KEY_EMAIL))
        editor.putString(KEY_ID, user.getString("_id"))
        editor.putString(KEY_MOBILE, user.getString(KEY_MOBILE))
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_ID, "")
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, "")
    }

    fun getMobile(): String? {
        return sharedPreferences.getString(KEY_MOBILE, "")
    }

    fun getName(): String? {
        return sharedPreferences.getString(KEY_FIRSTNAME, "")
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

}