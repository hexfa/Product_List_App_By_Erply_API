package com.pim.data

object InfoContainer {
    var token: String? = null
        private set
    var user: String? = null
        private set
    var pass: String? = null
        private set
    var clientCode: String? = null
        private set

    fun updateToken(token: String?) {
        InfoContainer.token = token
    }

    fun updateUserAndPass(user: String?, pass: String?, clientCode: String?) {
        InfoContainer.user = user
        InfoContainer.pass = pass
        InfoContainer.clientCode = clientCode
    }
}