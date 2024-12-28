package fr.filau.polyhome.housemanagement

import android.widget.GridView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.APIWrapper
import fr.filau.polyhome.generic.house_devices.GarageDoor
import fr.filau.polyhome.generic.house_devices.HouseDevice
import fr.filau.polyhome.generic.house_devices.Light
import fr.filau.polyhome.generic.house_devices.RollingShutter
import fr.filau.polyhome.generic.house_devices.SlidingShutter
import fr.filau.polyhome.housemanagement.data.HouseManagementData

class HouseManagementAPIWrapper(ui: HouseManagementActivity) : APIWrapper(ui) {
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

    fun doListDevices() {
        api.get("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/devices", ::listDevicesDone, securityToken = userToken)
    }

    private fun sendCommand(command: String) {

    }

    private fun listDevicesDone(responseCode: Int, returnData: HouseManagementData? = null) {
        when (responseCode) {
            200 -> {  // Success
                if (returnData == null) {
                    uiNotifier.unknownError("la récupération des équipements de la maison")
                } else {
                    val devices = ArrayList<HouseDevice>()
                    returnData.devices.forEach {
                        when (it.type) {
                            "rolling shutter" -> {
                                devices.add(RollingShutter(it, houseId, uiNotifier, ::sendCommand))
                            }
                            "sliding shutter" -> {
                                devices.add(SlidingShutter(it, houseId, uiNotifier, ::sendCommand))
                            }
                            "garage door" -> {
                                devices.add(GarageDoor(it, houseId, uiNotifier, ::sendCommand))
                            }
                            "light" -> {
                                devices.add(Light(it, houseId, uiNotifier, ::sendCommand))
                            }
                            else -> {
                                uiNotifier.toast("Type d’équipement non supporté « ${it.type} » détecté.")
                            }
                        }
                    }
                    ui.runOnUiThread {
                        val grid = ui.findViewById<GridView>(R.id.devicesGrid)
                        grid.adapter = HouseManagementAdapter(ui, devices.toTypedArray(), this@HouseManagementAPIWrapper)
                    }
                }
            }

            400 -> uiNotifier.badRequestError("la récupération des équipements de la maison")
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la récupération des équipements de la maison")
        }
    }

    fun insertCommandsIntoGrid(grid: GridView, device: HouseDevice) {
        grid.numColumns = device.availableCommands.size
        grid.adapter = HouseManagementCommandsAdapter(ui, device.availableCommands.toTypedArray())
    }
}
