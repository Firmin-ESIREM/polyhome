package fr.filau.polyhome.generic.house_devices

import android.view.View
import android.widget.ImageView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


class Light (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
    override var currentState: Float = houseData.power ?: 0F
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
        currentState = 1F
    }

    override fun turnOff() {
        sendCommand("TURN OFF")
        currentState = 0F
    }

    private fun toggle() {
        when (currentState) {
            0F -> turnOn()
            1F -> turnOff()
        }
    }

    private fun setImageResourceForCurrentState(imageView: ImageView) {
        val imgResource = when (currentState) {
            1F -> R.drawable.light_on
            0F -> R.drawable.light_off
            else -> 0
        }
        imageView.setImageResource(imgResource)
    }
}
