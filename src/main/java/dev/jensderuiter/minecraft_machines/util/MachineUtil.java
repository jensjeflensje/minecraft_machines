package dev.jensderuiter.minecraft_machines.util;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.machine.BaseMachine;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class MachineUtil {

    private static Logger logger = Bukkit.getLogger();

    public static void loadMachines() {
        MachinesPlugin.activeMachines.clear();
        ConfigurationSection machinesSection = MachinesPlugin.plugin.getConfig().getConfigurationSection("machines");
        for (String machineId : machinesSection.getKeys(false)) {
            ConfigurationSection machineSection = machinesSection.getConfigurationSection(machineId);
            int machineIdInt;
            try {
                machineIdInt = Integer.parseInt(machineId);
            } catch (NumberFormatException e) {
                logger.warning("Machine id" + machineId + " isn't a number");
                continue;
            }


            String type = machineSection.getString("type");
            int locX = machineSection.getInt("loc.x");
            int locY = machineSection.getInt("loc.y");
            int locZ = machineSection.getInt("loc.z");

            Class<? extends BaseMachine> machineType = MachinesPlugin.machineTypes.get(type);

            if (machineType == null) {
                logger.warning("Machine type " + type + " for machine id #" + machineId + " doesn't exist");
                continue;
            }

            Machine machine;
            try {
                machine = machineType.getConstructor(int.class, int.class, int.class, int.class)
                        .newInstance(machineIdInt, locX, locY, locZ);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }

            MachinesPlugin.activeMachines.put(
                    machineIdInt,
                    machine
            );
        }
    }

    public static ItemStack getKey(String formattedId, UUID uuid) {
        ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK, 1);

        ItemMeta keyMeta = key.getItemMeta();
        keyMeta.setDisplayName("Sleutel");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(formattedId);
        lore.add(uuid.toString());
        keyMeta.setLore(lore);

        key.setItemMeta(keyMeta);
        return key;
    }

}
