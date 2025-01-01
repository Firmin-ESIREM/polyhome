package fr.filau.polyhome.generic.house_devices

import android.view.View
import android.widget.Button
import android.widget.SeekBar
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


abstract class Shutter (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
    override fun open() {
        sendCommand("OPEN")
    }

    override fun close() {
        sendCommand("CLOSE")
    }

    override fun stop() {
        sendCommand("STOP")
    }

    override fun deviceControlGetViewSpecific(view: View): View {
        val openButton = view.findViewById<Button>(R.id.openButton)
        val closeButton = view.findViewById<Button>(R.id.closeButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)
        val positionSeekbar = view.findViewById<SeekBar>(R.id.seekPosition)

        openButton.setOnClickListener {
            open()
        }

        closeButton.setOnClickListener {
            close()
        }

        stopButton.setOnClickListener {
            stop()
        }

        positionSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        return view
    }
}
