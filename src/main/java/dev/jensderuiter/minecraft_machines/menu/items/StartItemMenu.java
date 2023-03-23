package dev.jensderuiter.minecraft_machines.menu.items;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.menu.clothing.BaseClothingMenu;
import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.clothing.ColorClothingMenu;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class StartItemMenu extends BaseItemMenu {

    public StartItemMenu(Player player, ItemMenuProperties properties) {
        super(player, properties);
    }

    @Override
    public String getMenuName() {
        return "starten";
    }

    public void displayMenu() {
        Menu menu = this.getMenu();

        ItemStack mainButton = new ItemStack(Material.ITEM_FRAME);
        ItemMeta mainButtonMeta = mainButton.getItemMeta();
        mainButtonMeta.setDisplayName(MessageUtil.color("&c&lItems maken"));
        List<String> lore = new ArrayList<>();
        lore.add("Dit kost €" + MachinesPlugin.plugin.getConfig().getInt("pricing.item") + ",-");
        mainButtonMeta.setLore(lore);
        mainButton.setItemMeta(mainButtonMeta);

        Slot mainButtonSlot = menu.getSlot(13);
        mainButtonSlot.setItem(mainButton);
        mainButtonSlot.setClickHandler(this::clickMainButton);

        menu.open(this.player);
    }

    public void clickMainButton(Player player, ClickInformation clickInformation) {
        willGetItemBack = false;
        new AmountItemMenu(player, this.properties).displayMenu();
    }

}
