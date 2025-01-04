package fr.filau.polyhome.generic.house_devices

import android.view.View
import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice


abstract class HouseDevice (houseData: HouseManagementDataDevice, val houseId: String, private val notifier: UINotifier, private val sendCommandThroughApi: (String, HouseDevice) -> Unit) {
    val availableCommands = ArrayList<DeviceCommand>()
    val id = houseData.id
    var floor = 0
    var deviceId = 0
    abstract var currentState: Float
    abstract val layout: Int

    init {
        houseData.availableCommands.forEach {
            lateinit var functionToLink: () -> Unit
            lateinit var commandName: String
            when (it) {
                "OPEN" -> {
                    functionToLink = {
                        open()
                    }
                    commandName = if (this is SlidingShutter) "←" else "↑"
                }
                "CLOSE" -> {
                    functionToLink = {
                        close()
                    }
                    commandName = if (this is SlidingShutter) "→" else "↓"
                }
                "STOP" -> {
                    functionToLink = {
                        stop()
                    }
                    commandName = "■"
                }
                "TURN ON" -> {
                    functionToLink = {
                        turnOn()
                    }
                    commandName = "on"
                }
                "TURN OFF" -> {
                    functionToLink = {
                        turnOff()
                    }
                    commandName = "off"
                }
                else -> {
                    functionToLink = {
                        unsupportedCommand()
                    }
                    commandName = "?"
                }
            }
            availableCommands.add(
                DeviceCommand(
                    it,
                    commandName,
                    functionToLink
                )
            )
        }
        val fullId = houseData.id.split(" ")[1].split(".")
        floor = fullId[0].toInt()
        deviceId = fullId[1].toInt()
    }

    protected fun sendCommand(command: String) {
        sendCommandThroughApi(command, this@HouseDevice)
    }

    private fun unsupportedCommand() {
        notifier.toast("Commande indisponible.")
    }

    open fun open() {
        unsupportedCommand()
    }

    open fun close() {
        unsupportedCommand()
    }

    open fun stop() {
        unsupportedCommand()
    }

    open fun turnOn() {
        unsupportedCommand()
    }

    open fun turnOff() {
        unsupportedCommand()
    }

    fun deviceControlGetCount(): Int {
        return 1
    }

    fun deviceControlGetItem(position: Int): Int {
        return position
    }

    fun deviceControlGetItemId(position: Int): Long {
        return position.toLong()
    }

    fun deviceControlGetView(position: Int, view: View): View {
        if (position != 0) throw IndexOutOfBoundsException()
        return deviceControlGetViewSpecific(view)
    }

    protected abstract fun deviceControlGetViewSpecific(view: View): View
}
