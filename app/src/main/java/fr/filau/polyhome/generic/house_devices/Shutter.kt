package fr.filau.polyhome.generic.house_devices

import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice

abstract class Shutter (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
    override fun open() {
        sendCommand("OPEN")
    }

    override fun close() {
        sendCommand("CLOSE")
    }

    override fun stop() {
        sendCommand("STOP")
    }
}
