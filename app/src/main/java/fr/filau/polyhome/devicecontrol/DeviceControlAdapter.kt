package fr.filau.polyhome.devicecontrol

import android.content.Context
import android.view.View
import android.view.ViewGroup
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import fr.filau.polyhome.generic.house_devices.GarageDoor
import fr.filau.polyhome.generic.house_devices.HouseDevice
import fr.filau.polyhome.generic.house_devices.Light
import fr.filau.polyhome.generic.house_devices.RollingShutter
import fr.filau.polyhome.generic.house_devices.SlidingShutter

class DeviceControlAdapter(context: Context, dataSource: HouseDevice, private val apiWrapper: DeviceControlAPIWrapper) : CustomBaseAdapter<HouseDevice>(context, dataSource,
    0
) {
    init {
        layoutItem = when (dataSource) {
            is RollingShutter, is GarageDoor -> R.layout.rollingshutter_garagedoor_control_item
            is SlidingShutter -> R.layout.slidingshutter_control_item
            is Light -> R.layout.light_control_item
            else -> 0
        }
    }

    override fun getCount(): Int {
        return dataSource.deviceControlGetCount()
    }

    override fun getItem(position: Int): Any {
        return dataSource.deviceControlGetItem(position)
    }

    override fun getItemId(position: Int): Long {
        return dataSource.deviceControlGetItemId(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflateView(parent)
        return dataSource.deviceControlGetView(position, view)
    }
}