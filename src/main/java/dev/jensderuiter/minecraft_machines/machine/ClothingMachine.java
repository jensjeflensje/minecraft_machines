package dev.jensderuiter.minecraft_machines.machine;

import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.clothing.StartClothingMenu;
import org.bukkit.entity.Player;

@RegisterMachine
public class ClothingMachine extends BaseMachine {

    public ClothingMachine(int id, int locX, int locY, int locZ) {
        super(getName(), id, locX, locY, locZ);
    }

    public static String getName() {
        return "kleding";
    }

    @Override
    public String getTypePrefix() {
        return "K";
    }

    @Override
    public void openMenu(Player player) {
        new StartClothingMenu(player, ClothingMenuProperties.builder().build()).displayMenu();
    }

}
