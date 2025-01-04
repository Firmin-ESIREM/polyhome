package fr.filau.polyhome.generic.house_devices

import fr.filau.polyhome.R
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


class SlidingShutter (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit, moveToSpecificPositionThroughApi: (Double, Shutter) -> Unit) : Shutter(houseData, houseId, notifier, sendCommandThroughApi, moveToSpecificPositionThroughApi) {
    override val layout = R.layout.slidingshutter_control_item
}
