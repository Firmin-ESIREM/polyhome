package fr.filau.polyhome.housemanagement

import android.content.Context
import android.view.View
import android.view.ViewGroup
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import fr.filau.polyhome.generic.house_devices.HouseDevice

class HouseManagementAdapter(context: Context, dataSource: Array<HouseDevice>, private val apiWrapper: HouseManagementAPIWrapper) : CustomBaseAdapter<Array<HouseDevice>>(context, dataSource,
    R.layout.peripheral_item
) {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): HouseDevice {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return inflateView(parent) // TODO
    }
}
