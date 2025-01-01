package fr.filau.polyhome.generic.house_devices

import android.content.Context
import android.view.View
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


class GarageDoor (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit) : Shutter(houseData, houseId, notifier, sendCommandThroughApi) {
}
