package fr.filau.polyhome.generic.house_devices

import android.view.View
import android.widget.ImageView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


class Light (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
    override fun deviceControlGetViewSpecific(view: View): View {
        val lightImage = view.findViewById<ImageView>(R.id.lightClickToToggle)
        lightImage.setOnClickListener {
            // TODO
        }
        return view
    }

    override fun turnOn() {
        sendCommand("TURN ON")
    }

    override fun turnOff() {
        sendCommand("TURN OFF")
    }

    fun toggle() {
        // TODO
    }
}
