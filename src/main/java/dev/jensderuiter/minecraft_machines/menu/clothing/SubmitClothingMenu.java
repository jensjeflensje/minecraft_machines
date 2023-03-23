package dev.jensderuiter.minecraft_machines.menu.clothing;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.util.MenuUtil;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.Menu;

public class SubmitClothingMenu extends BaseClothingMenu implements Listener {

    Menu menu;

    boolean waitingForName = false;
    boolean waitingForLore = false;

    public SubmitClothingMenu(Player player, ClothingMenuProperties properties) {
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
        menu.close(player);
        double price = MachinesPlugin.plugin.getConfig().getInt("pricing.kleding");
        if (MachinesPlugin.econ.getBalance(player.getName())
                < price) {
            MessageUtil.sendError(player, "Je hebt hier niet genoeg geld voor.");
            return;
        }
        MachinesPlugin.econ.withdrawPlayer(player, price);
        givePiece(Material.LEATHER_HELMET);
        givePiece(Material.LEATHER_CHESTPLATE);
        givePiece(Material.LEATHER_LEGGINGS);
        givePiece(Material.LEATHER_BOOTS);
        MessageUtil.sendSuccess(player, "De kleding is geproduceerd.");
    }

    public void back(Player player, ClickInformation clickInformation) {
        new OptionsClothingMenu(player, this.properties).displayMenu();
    }

    public void givePiece(Material piece) {
        player.getInventory().addItem(this.getClothingPieceWithProperties(piece));
    }


}
