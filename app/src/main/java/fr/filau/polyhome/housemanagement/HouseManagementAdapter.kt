package fr.filau.polyhome.housemanagement

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import fr.filau.polyhome.generic.house_devices.GarageDoor
import fr.filau.polyhome.generic.house_devices.HouseDevice
import fr.filau.polyhome.generic.house_devices.Light
import fr.filau.polyhome.generic.house_devices.RollingShutter
import fr.filau.polyhome.generic.house_devices.SlidingShutter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HouseManagementAdapter(context: Context, dataSource: Array<HouseDevice>, private val apiWrapper: HouseManagementAPIWrapper) : CustomBaseAdapter<Array<HouseDevice>>(context, dataSource,
    R.layout.device_item
) {
    init {
        Thread {
            while (true) {
                runBlocking {
                    delay(5000)
                }
                apiWrapper.runStateRefresh()
            }
        }.start()
    }

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
        val view = inflateView(parent)

        val device = getItem(position)

        view.findViewById<TextView>(R.id.lblDeviceName).text = "Étage ${device.floor}, n°${device.deviceId}"

        val pictureId = when (device) {
            is RollingShutter -> R.drawable.rolling_shutter
            is SlidingShutter -> R.drawable.sliding_shutter
            is GarageDoor -> R.drawable.garage_door
            is Light -> R.drawable.light_off
            else -> 0
        }

        view.findViewById<ImageView>(R.id.lblDeviceTypeImage).setImageResource(pictureId)

        val commandsGrid = view.findViewById<GridView>(R.id.lblCommandsGrid)
        apiWrapper.insertCommandsIntoGrid(commandsGrid, device)

        view.setOnClickListener {
            apiWrapper.proceedToDeviceControlActivity(device)
        }

        return view
    }
}
