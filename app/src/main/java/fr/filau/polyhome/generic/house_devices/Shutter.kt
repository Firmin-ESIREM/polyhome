package fr.filau.polyhome.generic.house_devices

import android.view.View
import android.widget.Button
import android.widget.SeekBar
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


abstract class Shutter (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit, private val moveToSpecificPositionThroughApi: (Double, Shutter) -> Unit) : HouseDevice(houseData, houseId, notifier, sendCommandThroughApi) {
    override var currentState: Double = houseData.opening ?: 0.0

    override fun open() {
        sendCommand("OPEN")
        currentState = 1.0
    }

    override fun close() {
        sendCommand("CLOSE")
        currentState = 0.0
    }

    override fun stop() {
        sendCommand("STOP")
    }

    fun moveToSpecificPosition(position: Int) {
        val desiredPosition = position / 100.0
        moveToSpecificPositionThroughApi(desiredPosition, this@Shutter)
    }

    override fun deviceControlGetViewSpecific(view: View): View {
        val openButton = view.findViewById<Button>(R.id.openButton)
        val closeButton = view.findViewById<Button>(R.id.closeButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)
        val positionSeekbar = view.findViewById<SeekBar>(R.id.seekPosition)

        openButton.setOnClickListener {
            open()
            positionSeekbar.progress = 100
        }

        closeButton.setOnClickListener {
            close()
            positionSeekbar.progress = 0
        }

        stopButton.setOnClickListener {
            stop()
        }

        positionSeekbar.progress = (currentState * 100).toInt()

        positionSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                moveToSpecificPosition(seekBar.progress)
            }
        })

        return view
    }
}
