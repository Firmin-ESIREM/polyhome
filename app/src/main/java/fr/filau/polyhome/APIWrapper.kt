package fr.filau.polyhome

enum class LoginStatus {
    SUCCESS, WRONG_CREDENTIALS, SERVER_ERROR, UNKNOWN_ERROR
}

class APIWrapper {
    val username = ""
    val userToken = ""

    fun login(username: String, password: String): Pair<LoginStatus, String> {
        Api().post<List<String>>("https://polyhome.lesmoulinsdudev.com/api/users/auth", ::loadingSuccess)
        // POST https://polyhome.lesmoulinsdudev.com/api/users/auth { login: username, password: "" }
        val returnCode = 200
        return when (returnCode) {
            200 -> LoginStatus.SUCCESS to ""
            404 -> LoginStatus.WRONG_CREDENTIALS to ""
            500 -> LoginStatus.SERVER_ERROR to ""
            else -> {
                LoginStatus.UNKNOWN_ERROR to ""
            }
        }
    }

}