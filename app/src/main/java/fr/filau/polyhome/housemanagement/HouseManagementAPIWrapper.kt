package fr.filau.polyhome.housemanagement

import fr.filau.polyhome.generic.APIWrapper

class HouseManagementAPIWrapper(ui: HouseManagementActivity) : APIWrapper(ui) {
    private lateinit var houseId: String

    init {
        val houseIdFromIntent = ui.intent.getStringExtra("houseId")
        if (houseIdFromIntent == null) {
            uiNotifier.toast("Aucune maison spécifiée !")
            ui.finish()
        } else {
            houseId = houseIdFromIntent
        }
    }

    fun doListPeripherals() {
        // api.get("https://polyhome.lesmoulinsdudev.com/api/houses/<houseId>/devices", ::listPeripheralsDone, securityToken = userToken)
    }

}
