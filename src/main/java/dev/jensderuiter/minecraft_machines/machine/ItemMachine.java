package dev.jensderuiter.minecraft_machines.machine;

import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.clothing.StartClothingMenu;
import dev.jensderuiter.minecraft_machines.menu.items.ItemMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.items.StartItemMenu;
import org.bukkit.entity.Player;

@RegisterMachine
public class ItemMachine extends BaseMachine {

    public ItemMachine(int id, int locX, int locY, int locZ) {
        super(getName(), id, locX, locY, locZ);
    }

    public static String getName() {
        return "item";
    }

    @Override
    public String getTypePrefix() {
        return "I";
    }

    @Override
    public void openMenu(Player player) {
        new StartItemMenu(player, ItemMenuProperties.builder().build()).displayMenu();
    }

}
