package dev.jensderuiter.minecraft_machines.menu.items;

import dev.jensderuiter.minecraft_machines.menu.BaseMachineMenu;
import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemMenu extends BaseMachineMenu {

    public ItemMenuProperties properties;
    public boolean willGetItemBack = true;

    public BaseItemMenu(Player player, ItemMenuProperties properties) {
        super(player, properties);
        this.properties = properties;
    }

    @Override
    public String getMainName() {
        return "Item machine";
    }

    @Override
    public ChestMenu getMenu() {
        ChestMenu menu = super.getMenu();
        menu.setCloseHandler(this::close);
        return menu;
    }

    public void close(Player player, Menu oldMenu) {
        if (this.properties.itemStack != null && this.willGetItemBack)
            player.getInventory().addItem(this.properties.itemStack);
    }

    public ItemStack getProduct() {
        ItemStack product = this.properties.itemStack.clone();
        product.setAmount(this.properties.itemStack.getAmount()
                + this.properties.amount);
        ItemMeta productMeta = product.getItemMeta();
        productMeta.setDisplayName(this.properties.getName());
        List<String> lore = new ArrayList<>();
        lore.add(this.properties.getLore());
        productMeta.setLore(lore);

        if (this.properties.isGlowing()) {
            productMeta.addEnchant( Enchantment.LURE, 1, false );
            productMeta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        }

        if (this.properties.isUnbreakable()) {
            productMeta.spigot().setUnbreakable(true);
        }

        product.setItemMeta(productMeta);

        return product;
    }

}
