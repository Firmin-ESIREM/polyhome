package fr.filau.polyhome.housemanagement

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import fr.filau.polyhome.generic.house_devices.DeviceCommand


class HouseManagementCommandsAdapter(context: Context, dataSource: Array<DeviceCommand>, private val apiWrapper: HouseManagementAPIWrapper) : CustomBaseAdapter<Array<DeviceCommand>>(context, dataSource,
    R.layout.command_item
) {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): DeviceCommand {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflateView(parent)

        val command = getItem(position)

        val button = view.findViewById<Button>(R.id.lblCommandName)

        button.text = command.name

        button.setOnClickListener {
            command.execute()
            if (command.id == "STOP") apiWrapper.runStateRefresh()
        }

        return view
    }
}
