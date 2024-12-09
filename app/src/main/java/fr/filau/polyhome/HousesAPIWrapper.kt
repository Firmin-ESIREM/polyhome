package fr.filau.polyhome

import androidx.appcompat.app.AppCompatActivity

class HousesAPIWrapper(ui: AppCompatActivity) : APIWrapper(ui) {

    fun doListHouses() {
        api.get("https://polyhome.lesmoulinsdudev.com/api/houses", ::listHousesDone, securityToken = userToken)
    }

    private fun listHousesDone(responseCode: Int, returnData: Array<Map<String, Int>>? = null) {
        when (responseCode) {
            200 -> {  // Success
                val returnDataToken = returnData?.get(0)
                if (returnDataToken == null) {
                    uiNotifier.unknownError("la connexion")
                } else {
                    uiNotifier.bravo()
                    ui  // .functionCall()  TODO: Add houses to UI
                }
            }

            400 -> uiNotifier.badRequestError("la récupération des maisons")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la récupération des maisons")
        }
    }

}