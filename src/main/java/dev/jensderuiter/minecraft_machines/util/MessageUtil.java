package dev.jensderuiter.minecraft_machines.util;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static void sendInfo(CommandSender player, String msg) {
        sendPrefixedMessage(player, MachinesPlugin.infoColor + msg);
    }

    public static void sendSuccess(CommandSender player, String msg) {
        sendPrefixedMessage(player, MachinesPlugin.successColor + msg);
    }

    public static void sendError(CommandSender player, String msg) {
        sendPrefixedMessage(player, MachinesPlugin.errorColor + msg);
    }


    public static void sendPrefixedMessage(CommandSender player, String msg) {
        player.sendMessage(color(MachinesPlugin.prefix + msg));
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
