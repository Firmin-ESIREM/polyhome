package fr.filau.polyhome.houses

import android.content.Intent
import android.view.View
import android.widget.GridView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import fr.filau.polyhome.housemanagement.HouseManagementActivity
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.APIWrapper

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
                    // uiNotifier.bravo()
                    ui.runOnUiThread {
                        val grid = ui.findViewById<GridView>(R.id.housesGrid)
                        grid.adapter = HousesAdapter(ui, returnData, this@HousesAPIWrapper)
                    }
                }
            }

            400 -> uiNotifier.badRequestError("la récupération des maisons")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la récupération des maisons")
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
                    } catch (_: IndexOutOfBoundsException) {
                        ownerUsername = "anonyme 🥷"
                    } finally {
                        val grid = ui.findViewById<GridView>(R.id.housesGrid)
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

        val houseId = view.findViewById<TextView>(R.id.lblHouseId).text.toString()

        intentToNextActivity.putExtra("houseId", houseId)

        startActivity(ui, intentToNextActivity, null);
    }
}
