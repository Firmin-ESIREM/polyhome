package fr.filau.polyhome

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HousesAdapter(context: Context, dataSource: Array<HouseData>) : CustomBaseAdapter<Array<HouseData>>(context, dataSource, R.layout.houses_item) {

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
        lateinit var houseName: String
        if (house.owner) {
            houseName = "Votre maison"
        } else {
            // TODO: see whom house this is
            houseName = "Maison de "
            houseName = "Maison d’"
        }

        view.findViewById<TextView>(R.id.lblHouseName).text = houseName
        view.findViewById<TextView>(R.id.lblHouseId).text = house.houseId.toString()

        return view
    }
}
