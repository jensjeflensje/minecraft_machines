package dev.jensderuiter.minecraft_machines.event;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import dev.jensderuiter.minecraft_machines.util.ConceptMachine;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.NoSuchElementException;

public class MachinePlaceListener implements Listener {

    @EventHandler
    public void onMachinePlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ConceptMachine concept = MachinesPlugin.machineConcepts.get(player);
        Block block = event.getBlockPlaced();
        if (block.getType() != Material.OBSERVER
            || concept == null) {
            return;
        }

        int lastId;
        ConfigurationSection machinesSection = MachinesPlugin.plugin.getConfig().getConfigurationSection("machines");
        try {
            lastId = Integer.parseInt(Collections.max(machinesSection.getKeys(false)));
        } catch (NoSuchElementException ignored) {
            lastId = 0;
        }


        int machineId = lastId + 1;

        Machine machine;
        try {
            machine = concept.getMachineType().getConstructor(int.class, int.class, int.class, int.class)
                    .newInstance(
                        machineId,
                        block.getLocation().getBlockX(),
                        block.getLocation().getBlockY(),
                        block.getLocation().getBlockZ()
            );
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        machine.save();

        MachinesPlugin.activeMachines.put(
                machineId,
                machine
        );

        MachinesPlugin.machineConcepts.remove(player);

        MessageUtil.sendSuccess(player, "Gelukt! Het id van deze machine is " + machine.getFormattedId());

    }

}
