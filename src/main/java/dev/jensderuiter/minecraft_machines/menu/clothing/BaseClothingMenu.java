package dev.jensderuiter.minecraft_machines.menu.clothing;

import dev.jensderuiter.minecraft_machines.menu.BaseMachineMenu;
import dev.jensderuiter.minecraft_machines.menu.BaseMenuProperties;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseClothingMenu extends BaseMachineMenu {

    public ClothingMenuProperties properties;

    public BaseClothingMenu(Player player, ClothingMenuProperties properties) {
        super(player, properties);
        this.properties = properties;
    }

    @Override
    public String getMainName() {
        return "Kledingmachine";
    }

    protected ItemStack getClothingPieceWithProperties(Material piece) {
        ItemStack colorPreview = new ItemStack(piece);
        LeatherArmorMeta colorPreviewMeta = (LeatherArmorMeta) colorPreview.getItemMeta();
        colorPreviewMeta.setDisplayName(this.properties.getName());
        colorPreviewMeta.setColor(Color.fromRGB(
                properties.getRed(),
                properties.getGreen(),
                properties.getBlue()
        ));
        List<String> lore = new ArrayList<>();
        lore.add(this.properties.getLore());
        colorPreviewMeta.setLore(lore);

        if (this.properties.isGlowing()) {
            colorPreviewMeta.addEnchant( Enchantment.LURE, 1, false );
            colorPreviewMeta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        }

        if (this.properties.isUnbreakable()) {
            colorPreviewMeta.spigot().setUnbreakable(true);
        }

        colorPreview.setItemMeta(colorPreviewMeta);

        return colorPreview;
    }
}
