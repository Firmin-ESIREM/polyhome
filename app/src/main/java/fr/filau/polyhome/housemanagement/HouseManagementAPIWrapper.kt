package fr.filau.polyhome.housemanagement

import android.content.Intent
import android.widget.GridView
import androidx.core.content.ContextCompat.startActivity
import fr.filau.polyhome.R
import fr.filau.polyhome.devicecontrol.DeviceControlActivity
import fr.filau.polyhome.devicecontrol.DeviceControlCurrentDeviceHolder
import fr.filau.polyhome.generic.APIWrapper
import fr.filau.polyhome.generic.house_devices.GarageDoor
import fr.filau.polyhome.generic.house_devices.HouseDevice
import fr.filau.polyhome.generic.house_devices.Light
import fr.filau.polyhome.generic.house_devices.RollingShutter
import fr.filau.polyhome.generic.house_devices.Shutter
import fr.filau.polyhome.generic.house_devices.SlidingShutter
import fr.filau.polyhome.housemanagement.data.HouseManagementData
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


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

    fun doListDevices(customCallback: ((Int, HouseManagementData?) -> Unit)? = null) {
        val callback: ((Int, HouseManagementData?) -> Unit) = customCallback ?: ::listDevicesDone
        api.get("https://polyhome.lesmoulinsdudev.com/api/houses/$houseId/devices", callback, securityToken = userToken)
    }

    private fun sendCommand(command: String, device: HouseDevice) {
        val data = mapOf(
            "command" to command,
        )
        api.post("https://polyhome.lesmoulinsdudev.com/api/houses/${device.houseId}/devices/${device.id}/command", data, ::cmdSuccess, securityToken = userToken)
    }

    private fun shutterMoveToSpecificPosition(position: Double, device: Shutter) {
        fun checkIfPositionReached(directionOpen: Boolean) {
            doListDevices { responseCode, returnData ->
                if (responseCode == 200 && returnData != null) {
                    try {
                        val deviceCurrent = returnData.devices.filter {
                            it.id == device.id
                        }[0]
                        if (deviceCurrent.opening != null) {
                            if (   ( directionOpen  && deviceCurrent.opening >= position)
                                || (!directionOpen && deviceCurrent.opening <= position)) {
                                device.stop()
                                device.currentState = deviceCurrent.opening
                            } else {
                                checkIfPositionReached(directionOpen)
                            }
                        }
                    } catch (_: IndexOutOfBoundsException) {}
                }
            }
        }

        doListDevices { responseCode, returnData ->
            if (responseCode == 200 && returnData != null) {
                try {
                    val deviceCurrent = returnData.devices.filter {
                        it.id == device.id
                    }[0]
                    if (deviceCurrent.opening != null) {
                        if (deviceCurrent.opening < position) {
                            device.open()
                            checkIfPositionReached(true)
                        } else if (deviceCurrent.opening > position) {
                            device.close()
                            checkIfPositionReached(false)
                        }
                    }
                } catch (_: IndexOutOfBoundsException) {}
            }
        }
    }

    private fun cmdSuccess(responseCode: Int) {
        when (responseCode) {
            200 -> uiNotifier.cmdSuccess()
            403 -> uiNotifier.forbiddenError()
            500 -> uiNotifier.serverError("la transmission de la commande")
            else -> uiNotifier.unknownError("la transmission de la commande")
        }
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
                                devices.add(RollingShutter(it, houseId, uiNotifier, ::sendCommand, ::shutterMoveToSpecificPosition))
                            }
                            "sliding shutter" -> {
                                devices.add(SlidingShutter(it, houseId, uiNotifier, ::sendCommand, ::shutterMoveToSpecificPosition))
                            }
                            "garage door" -> {
                                devices.add(GarageDoor(it, houseId, uiNotifier, ::sendCommand, ::shutterMoveToSpecificPosition))
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
            else -> uiNotifier.unknownError("la récupération des équipements de la maison")
        }
    }

    fun insertCommandsIntoGrid(grid: GridView, device: HouseDevice) {
        grid.numColumns = device.availableCommands.size
        grid.adapter = HouseManagementCommandsAdapter(ui, device.availableCommands.toTypedArray(), this@HouseManagementAPIWrapper)
    }

    fun proceedToDeviceControlActivity(device: HouseDevice) {
        val intentToNextActivity = Intent(
            ui,
            DeviceControlActivity::class.java
        )

        DeviceControlCurrentDeviceHolder.sharedDevice = device

        startActivity(ui, intentToNextActivity, null);
    }

    fun runStateRefresh() {
        doListDevices(::stateRefreshed)
    }

    private fun stateRefreshed(responseCode: Int, returnData: HouseManagementData? = null) {
        when (responseCode) {
            200 -> {  // Success
                if (returnData != null) {
                    val adapter = ui.findViewById<GridView>(R.id.devicesGrid).adapter as HouseManagementAdapter
                    val dataSource = adapter.retrieveDataSource()
                    val data = ArrayList<HouseDevice>()
                    returnData.devices.forEach { updateDevice ->
                        try {
                            val device = dataSource.filter { originalDevice ->
                                originalDevice.id == updateDevice.id
                            }[0]
                            when (device) {
                                is Shutter -> device.currentState = updateDevice.opening ?: 0.0
                                is Light -> device.currentState = updateDevice.power ?: 0.0
                            }
                            data.add(device)
                        } catch (_: IndexOutOfBoundsException) {}
                    }
                    ui.runOnUiThread {
                        adapter.updateDataSource(data.toTypedArray())
                    }
                }
            }
        }
    }
}
