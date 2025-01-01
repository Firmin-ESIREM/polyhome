package fr.filau.polyhome.devicecontrol

import android.widget.ListView
import android.widget.TextView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.APIWrapper
import fr.filau.polyhome.generic.house_devices.GarageDoor
import fr.filau.polyhome.generic.house_devices.Light
import fr.filau.polyhome.generic.house_devices.RollingShutter
import fr.filau.polyhome.generic.house_devices.SlidingShutter

class DeviceControlAPIWrapper(ui: DeviceControlActivity) : APIWrapper(ui) {
    fun setDevice() {
        val device = DeviceControlCurrentDeviceHolder.sharedDevice
        if (device == null) {
            uiNotifier.toast("Aucun périphérique spécifié !")
            ui.finish()
            return
        }

        val deviceType = when (device) {
            is RollingShutter -> "Volet roulant"
            is SlidingShutter -> "Volet coulissant"
            is GarageDoor -> "Porte de garage"
            is Light -> "Lampe"
            else -> ""
        }
        ui.findViewById<TextView>(R.id.headerTitle).text = "$deviceType ${device.floor}.${device.deviceId}"
        ui.findViewById<ListView>(R.id.controlLayoutToPopulate).adapter = DeviceControlAdapter(ui, device, this@DeviceControlAPIWrapper)
    }
}
