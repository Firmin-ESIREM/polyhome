package fr.filau.polyhome.houses

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.GridView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import fr.filau.polyhome.housemanagement.HouseManagementActivity
import fr.filau.polyhome.R
import fr.filau.polyhome.account.AccountActivity
import fr.filau.polyhome.generic.APIWrapper
import fr.filau.polyhome.houseaccess.HouseAccessActivity
import kotlinx.coroutines.runBlocking

class HousesAPIWrapper(ui: HousesActivity) : APIWrapper(ui) {

    fun doListHouses() {
        api.get("https://polyhome.lesmoulinsdudev.com/api/houses", ::listHousesDone, securityToken = userToken)
    }

    private fun listHousesDone(responseCode: Int, returnData: Array<HouseData>? = null) {
        when (responseCode) {
            200 -> {  // Success
                val returnDataToken = returnData?.get(0)
                if (returnDataToken == null) {
                    uiNotifier.unknownError("la récupération des maisons")
                } else {
                    ui.runOnUiThread {
                        val grid = ui.findViewById<GridView>(R.id.devicesGrid)
                        grid.adapter = HousesAdapter(ui, returnData, this@HousesAPIWrapper)
                    }
                }
            }

            400 -> uiNotifier.badRequestError("la récupération des maisons")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la récupération des maisons")
            else -> uiNotifier.unknownError("la récupération des maisons")
        }
    }

    fun setHouseOwnerName(houseId: Int, houseNameField: TextView) {
        api.get("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/users", { responseCode: Int, returnData: Array<HouseUserData>? ->
            when (responseCode) {
                200 -> {  // Success
                    lateinit var ownerUsername: String
                    try {
                        val ownerData = returnData?.filter {
                            it.owner == 1
                        }?.get(0)
                        if (ownerData != null) {
                            ownerUsername = ownerData.userLogin
                        }
                    } catch (_: IndexOutOfBoundsException) {  // The house has no owner
                        ownerUsername = "anonyme 🥷"
                    } finally {
                        val grid = ui.findViewById<GridView>(R.id.devicesGrid)
                        if (grid.adapter is HousesAdapter) {
                            (grid.adapter as HousesAdapter).setHouseName(ui, houseNameField, ownerUsername)
                        }
                    }
                }
            }
        }, securityToken = userToken)
    }

    fun proceedToHouseManagementActivity(view: View) {
        val intentToNextActivity = Intent(
            ui,
            HouseManagementActivity::class.java
        )

        val houseId = view.findViewById<TextView>(R.id.lblDeviceId).text.toString()

        intentToNextActivity.putExtra("houseId", houseId)

        startActivity(ui, intentToNextActivity, null);
    }

    fun proceedToHouseAccessActivity(view: View) {
        val intentToNextActivity = Intent(
            ui,
            HouseAccessActivity::class.java
        )

        val houseId = view.findViewById<TextView>(R.id.lblDeviceId).text.toString()

        intentToNextActivity.putExtra("houseId", houseId)

        startActivity(ui, intentToNextActivity, null);
    }

    private fun proceedBackToAccountActivity() {
        val intentToNextActivity = Intent(
            ui,
            AccountActivity::class.java
        )

        startActivity(ui, intentToNextActivity, null);
    }

    fun logoutManager() {
        ui.runOnUiThread {
            AlertDialog.Builder(ui)
                .setTitle("Bonjour !")
                .setMessage("$username, vous êtes actuellement connecté·e.")
                .setNegativeButton("Déconnexion") { _, _ ->
                    username = ""
                    userToken = ""
                    runBlocking {
                        saveData()
                    }
                    proceedBackToAccountActivity()
                    ui.finish()
                }
                .setPositiveButton("Fermer") { _, _ ->
                    // Nothing to do
                }
                .show()
        }
    }
}
