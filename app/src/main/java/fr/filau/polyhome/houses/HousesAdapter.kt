package fr.filau.polyhome.houses

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import java.text.Normalizer

class HousesAdapter(context: Context, dataSource: Array<HouseData>, private val apiWrapper: HousesAPIWrapper) : CustomBaseAdapter<Array<HouseData>>(context, dataSource,
    R.layout.houses_item
) {

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): HouseData {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].houseId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflateView(parent)

        val house = getItem(position)
        val houseNameField = view.findViewById<TextView>(R.id.lblDeviceName)

        if (house.owner) {
            houseNameField.text = "Votre maison"
        } else {
            apiWrapper.setHouseOwnerName(house.houseId, houseNameField)
            houseNameField.text = "Maison …"
        }

        view.findViewById<TextView>(R.id.lblDeviceId).text = house.houseId.toString()

        val grantAccessPicture = view.findViewById<ImageView>(R.id.grantAccess)

        if (house.owner) {
            grantAccessPicture.setOnClickListener {
                apiWrapper.proceedToHouseAccessActivity(view)
            }
        } else {
            (grantAccessPicture.parent as ViewGroup).removeView(grantAccessPicture)
        }

        view.setOnClickListener {
            apiWrapper.proceedToHouseManagementActivity(view);
        }

        return view
    }

    fun setHouseName(ui: AppCompatActivity,houseNameField: TextView, username: String) {
        val normalizedUsername = Normalizer.normalize(username, Normalizer.Form.NFD).lowercase()
        var houseName = when (normalizedUsername[0]) {
            'a', 'e', 'i', 'o', 'u', 'y', 'œ', 'æ' -> "Maison d’"
            else -> "Maison de "
        }
        houseName += username
        ui.runOnUiThread {
            houseNameField.text = houseName
        }
    }
}
