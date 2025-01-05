# PolyHome

This project aims at creating a home automation command app, that interfaces with a given API.

The followed instructions and the API docs are available [at that link](https://www.lamarmotte.info/wp-content/uploads/2023/01/Android-Projet-PolyHome-3.pdf).

## Bonus features

* You can add and remove user access for houses that you are the owner of, using the view accessible by touching the key icon.
* You can move to a specific position for the shutters (using the seekbar in the device-specific view).
    * **Known bug :** For rolling shutters and garage doors, the seekbars act weirdly, because the component is not designed to be put vertically. You should not drag-and-drop the cursor of those seekbars, but only touch where you want to place the cursor.
* A different device-specific view is implemented for sliding shutters, given the API actually works the way it is documented 🙃.

## _____

This software has been designed by [Firmin Launay](mailto:Firmin_Launay@etu.u-bourgogne.fr), in 2024, as part of the of the Mobile Applications Development course at Polytech Dijon.
