package fr.filau.polyhome.account

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import fr.filau.polyhome.houses.HousesActivity
import fr.filau.polyhome.generic.APIWrapper
import kotlinx.coroutines.runBlocking

class AccountsAPIWrapper(ui: AccountActivity) : APIWrapper(ui) {

    init {
        tryToken()
    }

    fun doRegister(providedUsername: String, providedPassword: String) {
        val data = mapOf(
            "login" to providedUsername,
            "password" to providedPassword
        )
        username = providedUsername
        passwordTemporary = providedPassword
        api.post<Map<String, String>>("https://polyhome.lesmoulinsdudev.com/api/users/register", data, ::registerDone)
    }



    fun doLogin(providedUsername: String, providedPassword: String) {
        val data = mapOf(
            "login" to providedUsername,
            "password" to providedPassword
        )
        username = providedUsername
        api.post<Map<String, String>, Map<String, String>>("https://polyhome.lesmoulinsdudev.com/api/users/auth", data, ::loginDone)
    }

    private fun loginDone(responseCode: Int, returnData: Map<String, String>? = null) {
        when (responseCode) {
            200 -> {  // Success
                val returnDataToken = returnData?.get("token")
                if (returnDataToken == null) {
                    uiNotifier.unknownError("la connexion")
                } else {
                    userToken = returnDataToken
                    runBlocking { saveData() }

                    proceedToHousesActivity()

                    // uiNotifier.bravo()  // TODO: Remove this
                }
            }
            400 -> {  // Incorrect data provided
                uiNotifier.badRequestError("la connexion")
            }
            404 -> {  // Invalid credentials
                uiNotifier.invalidCredentials()
            }
            500 -> {  // Server error
                uiNotifier.serverError("la connexion")
            }
            else -> {  // Unhandled error
                uiNotifier.unknownError("la connexion")
            }
        }
    }

    private fun registerDone(responseCode: Int) {
        when (responseCode) {
            200 -> {  // Success
                doLogin(username, passwordTemporary)
            }
            400 -> {  // Incorrect data provided
                uiNotifier.badRequestError("l’inscription")
            }
            409 -> {  // Username already in use
                uiNotifier.usernameAlreadyInUse()
            }
            500 -> {  // Server error
                uiNotifier.serverError("l’inscription")
            }
            else -> {  // Unhandled error
                uiNotifier.unknownError("l’inscription")
            }
        }
        passwordTemporary = ""
    }

    private fun proceedToHousesActivity() {
        val intentToNextActivity = Intent(
            ui,
            HousesActivity::class.java
        )
        startActivity(ui, intentToNextActivity, null);

        ui.finish()
    }

    private fun tryToken() {
        api.get<Array<Map<String, String>>>("https://polyhome.lesmoulinsdudev.com/api/houses", ::tokenTried, userToken)
    }

    private fun tokenTried(responseCode: Int, returnData: Array<Map<String, String>>? = null) {
        if (responseCode == 200) {
            proceedToHousesActivity()
            uiNotifier.toast("Connecté·e automatiquement 😉")
        }
    }
}
