package fr.filau.polyhome.generic.house_devices

data class DeviceCommand(
    val name: String,
    val execute: () -> Unit
)
