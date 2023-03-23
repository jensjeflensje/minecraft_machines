package dev.jensderuiter.minecraft_machines.machine;

import org.bukkit.entity.Player;

public interface Machine {
    String getTypePrefix();
    int getId();
    String getFormattedId();
    void openMenu(Player player);
    void save();
    void remove();
    void addPlayer(Player player);

    static String getName() {
        return null;
    }

}
