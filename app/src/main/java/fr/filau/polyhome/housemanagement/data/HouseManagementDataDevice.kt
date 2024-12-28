package fr.filau.polyhome.housemanagement.data

data class HouseManagementDataDevice(
    val id: String,
    val type: String,
    val availableCommands: ArrayList<String>,
    val opening: Int?,
    val openingMode: Int?,
    val power: Int?
)
