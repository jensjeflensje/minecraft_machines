package dev.jensderuiter.minecraft_machines.menu;

import org.bukkit.entity.Player;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.type.ChestMenu;

public abstract class BaseMachineMenu implements MachineMenu {

    protected Player player;
    protected BaseMenuProperties properties;

    public BaseMachineMenu(Player player, BaseMenuProperties properties) {
        this.player = player;
        this.properties = properties;
    }

    public ChestMenu getMenu() {
        return ChestMenu.builder(3)
                .title(this.getMainName() + " - " + this.getMenuName())
                .build();
    }

}
