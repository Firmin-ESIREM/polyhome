package fr.filau.polyhome

import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

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
                        grid.adapter = HousesAdapter(ui, returnData)
                    }
                }
            }

            400 -> uiNotifier.badRequestError("la récupération des maisons")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la récupération des maisons")
        }
    }

}