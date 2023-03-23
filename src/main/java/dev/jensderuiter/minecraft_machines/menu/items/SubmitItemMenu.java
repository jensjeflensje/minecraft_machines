package dev.jensderuiter.minecraft_machines.menu.items;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.menu.clothing.BaseClothingMenu;
import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.clothing.OptionsClothingMenu;
import dev.jensderuiter.minecraft_machines.util.MenuUtil;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.Menu;

public class SubmitItemMenu extends BaseItemMenu implements Listener {

    Menu menu;

    public SubmitItemMenu(Player player, ItemMenuProperties properties) {
        super(player, properties);
    }

    @Override
    public String getMenuName() {
        return "bevestigen";
    }

    public void displayMenu() {
        menu = this.getMenu();

        ItemStack submitButton = MenuUtil.getSubmitButton();
        ItemStack backButton = MenuUtil.getBackButton();

        menu.getSlot(13).setItem(submitButton);
        menu.getSlot(13).setClickHandler(this::submit);

        menu.getSlot(18).setItem(backButton);
        menu.getSlot(18).setClickHandler(this::back);

        menu.open(player);
    }

    public void submit(Player player, ClickInformation clickInformation) {

        double price = MachinesPlugin.plugin.getConfig().getInt("pricing.item");
        if (MachinesPlugin.econ.getBalance(player.getName())
                < price) {
            MessageUtil.sendError(player, "Je hebt hier niet genoeg geld voor.");
            return;
        }
        willGetItemBack = false;
        menu.close(player);
        MachinesPlugin.econ.withdrawPlayer(player, price);
        player.getInventory().addItem(this.getProduct());
        MessageUtil.sendSuccess(player, "De items zijn geproduceerd.");
    }

    public void back(Player player, ClickInformation clickInformation) {
        willGetItemBack = false;
        new OptionsItemMenu(player, this.properties).displayMenu();
    }


}
