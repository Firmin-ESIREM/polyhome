package fr.filau.polyhome.generic.house_devices

import fr.filau.polyhome.generic.UINotifier
import fr.filau.polyhome.housemanagement.data.HouseManagementDataDevice

abstract class HouseDevice (houseData: HouseManagementDataDevice, private val houseId: String, private val notifier: UINotifier, private val sendCommandThroughApi: (String) -> Unit) {
    val deviceId = houseData.id
    private val availableCommands = ArrayList<DeviceCommand>()

    init {
        houseData.availableCommands.forEach {
            lateinit var functionToLink: () -> Unit
            when (it) {
                "OPEN" -> functionToLink = {
                    open()
                }
                "CLOSE" -> functionToLink = {
                    close()
                }
                "STOP" -> functionToLink = {
                    stop()
                }
                "TURN ON" -> functionToLink = {
                    turnOn()
                }
                "TURN OFF" -> functionToLink = {
                    turnOff()
                }
            }
            availableCommands.add(
                DeviceCommand(
                    it,
                    functionToLink
                )
            )
        }
    }

    protected fun sendCommand(command: String) {
        // TODO
    }

    private fun unsupportedCommand() {

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
