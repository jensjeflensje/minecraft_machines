# Minecraft Machines
A Minecraft machines plugin with keys, GUIs and an extendable framework.
This plugin uses Dutch messages.
This plugin was tested on Minecraft version `1.12.2`.
It's not guaranteed that it'll work on other versions.
You're free to create a PR if you'd like to make it work on other versions (it also has to work for 1.12.2 though :P).

## Machines
- Clothing (featuring color selector) (IDs are prefixed with `K`)
- Items (IDs are prefixed with `I`)

## Spigot Dependencies
- Vault

## Tutorial
### Machine creation
To create a machine, you need to take a few steps.
1. Use `/machine` to see all the commands.
2. Execute `/machine create <type>` to create a machine. You will have to place an observer block to finish the machine's setup (this block will become the machine).
3. Create yourself some keys for this machine by executing `/machine givekey <machine_id> <player>`. The machine id will need to be formatted in the way you got it from the create command.
4. Give the created keys to the players you created them for.

### Machine Usage
Machine usage is very straight forward.
Please keep in mind that this only applies to the current machine types.
Other types may introduce a different flow.
1. Click on the machine with your key.
2. Choose machine-specific options.
   1. Clothing: color (RGB)
   2. Items: duplication amount.
3. Choose properties. These can be any of the following
   1. Unbreakable
   2. Glowing
   3. Display name
   4. Lore
4. Finish and pay.

### Revoking keys
Revoking keys can be done by removing a key from a player's inventory.

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

## Local development dependencies

### Canvas
Install https://github.com/IPVP-MC/canvas to your local maven repository.