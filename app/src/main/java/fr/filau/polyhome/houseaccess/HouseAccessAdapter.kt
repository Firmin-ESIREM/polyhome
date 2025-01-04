package fr.filau.polyhome.houseaccess

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import fr.filau.polyhome.generic.house_devices.GarageDoor
import fr.filau.polyhome.generic.house_devices.HouseDevice
import fr.filau.polyhome.generic.house_devices.Light
import fr.filau.polyhome.generic.house_devices.RollingShutter
import fr.filau.polyhome.generic.house_devices.SlidingShutter

class HouseAccessAdapter(context: Context, dataSourceLocal: Array<HouseAccessData>, private val apiWrapper: HouseAccessAPIWrapper) : CustomBaseAdapter<Array<HouseAccessData>>(context, dataSourceLocal,
    R.layout.user_item
) {
    init {
        dataSource = dataSource.filter {
            it.owner == 0
        }.toTypedArray()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): HouseAccessData {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflateView(parent)

        val user = getItem(position)

        view.findViewById<TextView>(R.id.lblUsername).text = user.userLogin

        view.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener {
            updateDataSource(dataSource.filter {
                it.userLogin != user.userLogin
            }.toTypedArray())
            apiWrapper.deleteUser(user.userLogin)
        }

        return view
    }

    fun addUserToList(username: String) {
        val newDataSource = dataSource + HouseAccessData(username, 0)
        updateDataSource(newDataSource)
    }
}
