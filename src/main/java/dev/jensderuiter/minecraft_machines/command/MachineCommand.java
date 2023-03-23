package dev.jensderuiter.minecraft_machines.command;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.machine.BaseMachine;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import dev.jensderuiter.minecraft_machines.util.ConceptMachine;
import dev.jensderuiter.minecraft_machines.util.MachineUtil;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MachineCommand implements CommandExecutor {

    private void sendHelp(Player player) {
        player.sendMessage("MACHINES HELP");
        player.sendMessage("/machine create <machine_type>");
        player.sendMessage("/machine givekey <machine_id> <speler_naam>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageUtil.sendError(sender, "Je moet dit commando uitvoeren als speler.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("machine.admin")) {
            MessageUtil.sendError(sender, "Je hebt hier geen permission voor.");
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0]) {
            case "create":
                if (args.length != 2) {
                    MessageUtil.sendError(player, "/machine create <machine_type>");
                    return true;
                }

                Class<? extends BaseMachine> machineType = MachinesPlugin.machineTypes.get(args[1]);

                if (machineType == null) {
                    MessageUtil.sendError(player, "Machine type bestaat niet.");
                    StringBuilder machineStringTypes = new StringBuilder();
                    boolean first = true;
                    for (String machineStringType : MachinesPlugin.machineTypes.keySet()) {
                        if (!first) {
                            machineStringTypes.append(", ");
                        } else {
                            first = false;
                        }
                        machineStringTypes.append(machineStringType);
                    }
                    MessageUtil.sendInfo(player, "Lijst van beschikbare types: " + machineStringTypes);
                    return true;
                }

                MachinesPlugin.machineConcepts.put(player, new ConceptMachine(
                        player,
                        machineType
                ));
                MessageUtil.sendSuccess(player, "Gelukt! Plaats een observer om de machine af te maken.");
                break;
            case "givekey":
                if (args.length != 3) {
                    MessageUtil.sendError(player, "/machine addowner <machine_id> <speler_naam>");
                    return true;
                }

                String[] machineIdParts = args[1].split("#");
                int machineId;
                try {
                    machineId = Integer.parseInt(machineIdParts[machineIdParts.length - 1]);
                } catch (NumberFormatException e) {
                    MessageUtil.sendError(player, "Dit is geen geldig machine id.");
                    return true;
                }


                Machine machine = MachinesPlugin.activeMachines.get(machineId);

                if (machine == null) {
                    MessageUtil.sendError(player, "Deze machine bestaat niet.");
                    return true;
                }

                Player playerForKey = Bukkit.getPlayer(args[2]);

                if (playerForKey == null) {
                    MessageUtil.sendError(player, "Deze speler bestaat niet.");
                    return true;
                }

                ItemStack key = MachineUtil.getKey(machine.getFormattedId(), playerForKey.getUniqueId());

                player.getInventory().addItem(key);

                machine.addPlayer(playerForKey);

                MessageUtil.sendSuccess(player, "Gelukt! Geef nu de sleutel aan de speler.");
                break;
            default:
                sendHelp(player);
        }

        return true;
    }

}
