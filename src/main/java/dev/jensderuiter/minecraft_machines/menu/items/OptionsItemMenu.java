package dev.jensderuiter.minecraft_machines.menu.items;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.menu.clothing.BaseClothingMenu;
import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.clothing.ColorClothingMenu;
import dev.jensderuiter.minecraft_machines.menu.clothing.SubmitClothingMenu;
import dev.jensderuiter.minecraft_machines.util.MenuUtil;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;

public class OptionsItemMenu extends BaseItemMenu implements Listener {

    Menu menu;

    Slot previewSlot;
    Slot unbreakableSlot;
    Slot glowingSlot;
    Slot nameSlot;
    Slot loreSlot;

    boolean waitingForName = false;
    boolean waitingForLore = false;

    public OptionsItemMenu(Player player, ItemMenuProperties properties) {
        super(player, properties);
    }

    @Override
    public String getMenuName() {
        return "instellingen";
    }

    public void displayMenu() {
        menu = this.getMenu();

        previewSlot = menu.getSlot(10);
        unbreakableSlot = menu.getSlot(12);
        glowingSlot = menu.getSlot(13);
        nameSlot = menu.getSlot(14);
        loreSlot = menu.getSlot(15);

        ItemStack nextButton = MenuUtil.getNextButton();

        menu.getSlot(17).setItem(nextButton);
        menu.getSlot(17).setClickHandler(this::next);

        ItemStack backButton = MenuUtil.getBackButton();

        menu.getSlot(18).setItem(backButton);
        menu.getSlot(18).setClickHandler(this::back);

        reload();

    }

    public void next(Player player, ClickInformation clickInformation) {
        willGetItemBack = false;
        new SubmitItemMenu(player, this.properties).displayMenu();
    }

    public void back(Player player, ClickInformation clickInformation) {
        willGetItemBack = false;
        new AmountItemMenu(player, this.properties).displayMenu();
    }

    public void reload() {
        ItemStack previewItem = this.getProduct();
        previewSlot.setItem(previewItem);

        createButton(unbreakableSlot, Material.LAVA_BUCKET, "&9&lUnbreakable", this.properties.isUnbreakable(),
                this::toggleUnbreakable);
        createButton(glowingSlot, Material.GLOWSTONE, "&9&lGlow",  this.properties.isGlowing(),
                this::toggleGlowing);
        createButton(nameSlot, Material.LAVA_BUCKET, "&9&lNaam = " + this.properties.getName(),
                !this.properties.getName().equals("Kies een naam"), this::setName);
        createButton(loreSlot, Material.BOOK, "&9&lLore = " + this.properties.getLore(),
                !this.properties.getLore().equals(""), this::setLore);

        menu.open(this.player);
    }

    private void createButton(Slot slot, Material material, String name, boolean glowing, Slot.ClickHandler clickHandler) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(MessageUtil.color(name));

        if (glowing) {
            buttonMeta.addEnchant( Enchantment.LURE, 1, false );
            buttonMeta.addItemFlags( ItemFlag.HIDE_ENCHANTS );
        }

        button.setItemMeta(buttonMeta);
        slot.setItem(button);
        slot.setClickHandler(clickHandler);
    }

    public void toggleUnbreakable(Player player, ClickInformation clickInformation) {
        this.properties.setUnbreakable(!this.properties.isUnbreakable());
        reload();
    }

    public void toggleGlowing(Player player, ClickInformation clickInformation) {
        this.properties.setGlowing(!this.properties.isGlowing());
        reload();
    }

    public void setName(Player player, ClickInformation clickInformation) {
        waitingForName = true;
        menu.close(player);
        Bukkit.getPluginManager().registerEvents(this, MachinesPlugin.plugin);
        MessageUtil.sendInfo(player, "Typ nu een naam in de chat.");
    }

    public void setLore(Player player, ClickInformation clickInformation) {
        waitingForLore = true;
        menu.close(player);
        Bukkit.getPluginManager().registerEvents(this, MachinesPlugin.plugin);
        MessageUtil.sendInfo(player, "Typ nu een lore in de chat.");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (waitingForName) {
            properties.setName(MessageUtil.color(event.getMessage()));
            waitingForName = false;
            HandlerList.unregisterAll(this);
            event.setCancelled(true);
            displayMenu();
        }
        if (waitingForLore) {
            properties.setLore(MessageUtil.color(event.getMessage()));
            waitingForLore = false;
            HandlerList.unregisterAll(this);
            event.setCancelled(true);
            displayMenu();
        }
    }

}
