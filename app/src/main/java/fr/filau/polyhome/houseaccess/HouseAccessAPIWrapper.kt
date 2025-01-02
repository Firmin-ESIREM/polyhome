package fr.filau.polyhome.houseaccess

import android.widget.ListView
import androidx.core.content.ContextCompat.startActivity
import fr.filau.polyhome.R

import fr.filau.polyhome.generic.APIWrapper



class HouseAccessAPIWrapper(ui: HouseAccessActivity) : APIWrapper(ui) {
    private lateinit var houseId: String

    init {
        val houseIdFromIntent = ui.intent.getStringExtra("houseId")
        if (houseIdFromIntent == null) {
            uiNotifier.toast("Aucune maison spécifiée !")
            ui.finish()
        } else {
            houseId = houseIdFromIntent
            ui.setHouseIdInTitle(houseId)
        }
    }

    fun doListUsers() {
        api.get("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", ::listUsersDone, securityToken = userToken)
    }


    private fun listUsersDone(responseCode: Int, returnData: Array<HouseAccessData>? = null) {
        when (responseCode) {
            200 -> {  // Success
                if (returnData == null) {
                    uiNotifier.unknownError("la récupération des utilisateurs de la maison")
                } else {
                    ui.runOnUiThread {
                        val list = ui.findViewById<ListView>(R.id.accessList)
                        list.adapter = HouseAccessAdapter(ui, returnData, this@HouseAccessAPIWrapper)
                    }
                }
            }

            400 -> uiNotifier.badRequestError("la récupération des utilisateurs de la maison")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la récupération des utilisateurs de la maison")
            else -> uiNotifier.unknownError("la récupération des utilisateurs de la maison")
        }
    }

    fun deleteUser(username: String) {
        api.delete("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", mapOf("userLogin" to username), ::deleteUserDone, securityToken = userToken)
    }

    fun addUser(username: String) {
        api.post("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", mapOf("userLogin" to username), ::addUserDone, securityToken = userToken)
    }

    private fun deleteUserDone(responseCode: Int) {
        when (responseCode) {
            200 -> {  // Success
                uiNotifier.toast("Accès utilisateur supprimé")
                return
            }
            400 -> uiNotifier.badRequestError("la suppression de l’accès utilisateur")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la suppression de l’accès utilisateur")
            else -> uiNotifier.unknownError("la suppression de l’accès utilisateur")
        }
        ui.finish()
        startActivity(ui, ui.intent, null)
    }

    private fun addUserDone(responseCode: Int) {
        when (responseCode) {
            200 -> {  // Success
                uiNotifier.toast("Accès utilisateur ajouté")
                return
            }
            400 -> uiNotifier.badRequestError("l’ajout de l’accès utilisateur")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("l’ajout de l’accès utilisateur")
            else -> uiNotifier.unknownError("l’ajout de l’accès utilisateur")
        }
        ui.finish()
        startActivity(ui, ui.intent, null)
    }
}
