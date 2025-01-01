package fr.filau.polyhome.devicecontrol

import fr.filau.polyhome.generic.house_devices.HouseDevice

object DeviceControlCurrentDeviceHolder {
    var sharedDevice: HouseDevice? = null
}
