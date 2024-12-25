package fr.filau.polyhome.housemanagement

import android.content.Context
import android.view.View
import android.view.ViewGroup
import fr.filau.polyhome.R
import fr.filau.polyhome.generic.CustomBaseAdapter
import fr.filau.polyhome.houses.HouseData
import fr.filau.polyhome.houses.HousesAPIWrapper

class HouseManagementAdapter(context: Context, dataSource: Array<HouseManagementData>, private val apiWrapper: HousesAPIWrapper) : CustomBaseAdapter<Array<HouseManagementData>>(context, dataSource,
    R.layout.houses_item // TODO: To update !!!
) {
    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}
