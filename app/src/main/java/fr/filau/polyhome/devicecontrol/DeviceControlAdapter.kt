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
    override val layoutItem = dataSource.layout

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