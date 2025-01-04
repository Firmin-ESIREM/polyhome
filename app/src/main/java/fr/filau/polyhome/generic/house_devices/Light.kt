package fr.filau.polyhome.generic.house_devices

import android.view.View
import android.widget.ImageView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


class Light (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
    override var currentState: Double = houseData.power ?: 0.0
    override val layout = R.layout.light_control_item

    override fun deviceControlGetViewSpecific(view: View): View {
        val lightImage = view.findViewById<ImageView>(R.id.lightClickToToggle)

        setImageResourceForCurrentState(lightImage)

        lightImage.setOnClickListener {
            toggle()
            setImageResourceForCurrentState(lightImage)
        }
        return view
    }

    override fun turnOn() {
        sendCommand("TURN ON")
        currentState = 1.0
    }

    override fun turnOff() {
        sendCommand("TURN OFF")
        currentState = 0.0
    }

    private fun toggle() {
        when (currentState) {
            0.0 -> turnOn()
            1.0 -> turnOff()
        }
    }

    private fun setImageResourceForCurrentState(imageView: ImageView) {
        val imgResource = when (currentState) {
            1.0 -> R.drawable.light_on
            0.0 -> R.drawable.light_off
            else -> 0
        }
        imageView.setImageResource(imgResource)
    }
}
