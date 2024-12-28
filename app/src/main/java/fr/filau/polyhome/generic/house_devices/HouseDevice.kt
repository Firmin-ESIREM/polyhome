package fr.filau.polyhome.generic.house_devices

import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice

abstract class HouseDevice (houseData: HouseManagementDataDevice, private val houseId: String, private val notifier: UINotifier, private val sendCommandThroughApi: (String) -> Unit) {
    val availableCommands = ArrayList<DeviceCommand>()
    var floor = 0
    var deviceId = 0

    init {
        houseData.availableCommands.forEach {
            lateinit var functionToLink: () -> Unit
            lateinit var commandName: String
            when (it) {
                "OPEN" -> {
                    functionToLink = {
                        open()
                    }
                    commandName = "↑"
                }
                "CLOSE" -> {
                    functionToLink = {
                        close()
                    }
                    commandName = "↓"
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
        sendCommandThroughApi(command)
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
}
