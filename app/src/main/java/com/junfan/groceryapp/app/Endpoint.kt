package com.junfan.groceryapp.app

class Endpoints {

    companion object{

        private const val URL_CATEGORY ="category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT_BY_SUB_ID = "products/sub/"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"
        private const val URL_ADDRESS = "address/"
        private const val URL_ORDER = "orders"

        fun getCategory(): String{
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int):String{
            return "${Config.BASE_URL + URL_SUB_CATEGORY + catId}"
        }

        fun getProductBySubId(subId: Int): String{
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB_ID + subId}"
        }

        fun getRegister(): String{
            return "${Config.BASE_URL+ URL_REGISTER}"
        }

        fun getLogin(): String{
            return "${Config.BASE_URL+ URL_LOGIN}"
        }

        fun getAddress(userId: String): String{
            return "${Config.BASE_URL+ URL_ADDRESS + userId}"
        }

        fun postAddress(): String {
            return "${Config.BASE_URL+ URL_ADDRESS}"
        }

        fun deleteAddress(id: String): String {
            return "${Config.BASE_URL + URL_ADDRESS + id}"
        }

        fun updateAddress(id: String): String {
            return "${Config.BASE_URL + URL_ADDRESS + id}"
        }

        fun postOrder(): String {
            return "${Config.BASE_URL + URL_ORDER}"
        }

    }

}