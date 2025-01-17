package fr.filau.polyhome.generic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

abstract class CustomBaseAdapter<T>(context: Context, protected var dataSource: T, protected open val layoutItem: Int) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun updateDataSource(newDataSource: T) {
        dataSource = newDataSource
        notifyDataSetChanged()
    }

    fun retrieveDataSource(): T {
        return dataSource
    }

    protected fun inflateView(parent: ViewGroup?): View {
        return inflater.inflate(layoutItem, parent, false)
    }
}
