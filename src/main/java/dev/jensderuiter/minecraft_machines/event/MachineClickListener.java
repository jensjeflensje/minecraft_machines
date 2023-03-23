package dev.jensderuiter.minecraft_machines.event;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import dev.jensderuiter.minecraft_machines.util.ConceptMachine;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class MachineClickListener implements Listener {

    private static Logger logger = Bukkit.getLogger();

    private void sendKeyNeeded(Player player) {
        MessageUtil.sendError(player, "Je hebt hier een sleutel voor nodig.");
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onMachineClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK
                || block.getType() != Material.OBSERVER) {
            return;
        }

        Integer machineId = MachinesPlugin.locationMachineIdMap.get(
                block.getX() + "_" + block.getY() + "_" + block.getZ()
        );

        if (machineId == null) {
            return;
        }

        ItemStack key = event.getPlayer().getItemInHand();
        if (key == null || key.getType() != Material.TRIPWIRE_HOOK) {
            if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
            sendKeyNeeded(player);
            return;
        }
        if (!key.hasItemMeta()) {
            sendKeyNeeded(player);
            return;
        }
        int keyId;
        String keyUUID;
        try {
            String keyFirstLore = key.getItemMeta().getLore().get(0);
            String[] keyFirstLoreParts = keyFirstLore.split("#");
            keyId = Integer.parseInt(keyFirstLoreParts[keyFirstLoreParts.length - 1]);
            keyUUID = key.getItemMeta().getLore().get(1);
        } catch (IndexOutOfBoundsException ignored) {
            sendKeyNeeded(player);
            return;
        }

        if (keyId != machineId || !keyUUID.equals(player.getUniqueId().toString())) {
            MessageUtil.sendError(player, "Je hebt de verkeerde sleutel.");
            return;
        }

        Machine machine = MachinesPlugin.activeMachines.get(machineId);

        if (machine == null) {
            logger.warning("Machine in cache doesn't have an active machine: " + machineId);
            return;
        }

        event.setCancelled(true);
        machine.openMenu(player);


    }

}
