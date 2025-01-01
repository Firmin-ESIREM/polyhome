package fr.filau.polyhome.generic.house_devices

import android.content.Context
import android.view.View
import android.widget.SeekBar
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


class SlidingShutter (houseData: HouseManagementDataDevice, houseId: String, notifier: UINotifier, sendCommandThroughApi: (String, HouseDevice) -> Unit) : Shutter(houseData, houseId, notifier, sendCommandThroughApi) {
    override fun deviceControlGetViewSpecific(view: View): View {
        // TODO
        return view
    }
}
