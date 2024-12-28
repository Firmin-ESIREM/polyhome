package fr.filau.polyhome.generic.house_devices

import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice

class Light (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
}
