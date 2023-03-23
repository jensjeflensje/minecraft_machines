# Minecraft Machines
A Minecraft machines plugin with keys, GUIs and an extendable framework.

## Machines
- Clothing (featuring color selector)
- Items

## The machines framework
To create a machine, extend BaseMachine and implement the methods.
- `getName` is used as a type identifier for configs and files. Should be a string without spaces.
- `getTypePrefix` is used for the prefix on keys.
- `openMenu` is the method that gets called when a machine is clicked on.

### Menu's
Menu's can be opened from the `openMenu` method, or from other menu's.
A menu is basically just a GUI which can change properties or execute actions.
Initiate the class and call displayMenu to get started.
A menu takes a player and a properties object.

### Properties
A properties class is a class that stores data between menu's,
so that the final output can be made from the specifications of the properties.

### Machine price
A machine takes money from a user using vault.
This mostly happens at the end of the configuration process.


## Dependencies

### Canvas
Install https://github.com/IPVP-MC/canvas to your local maven repository.