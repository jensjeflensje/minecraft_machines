package dev.jensderuiter.minecraft_machines.menu.clothing;

import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import dev.jensderuiter.minecraft_machines.util.MenuUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;

public class ColorClothingMenu extends BaseClothingMenu {

    Menu menu;

    ItemStack colorPreview;
    LeatherArmorMeta colorPreviewMeta;
    Slot colorPreviewSlot;

    public ColorClothingMenu(Player player, ClothingMenuProperties properties) {
        super(player, properties);
    }

    @Override
    public String getMenuName() {
        return "kleuren";
    }

    @Override
    public void displayMenu() {
        menu = this.getMenu();

        colorPreviewSlot = menu.getSlot(10);

        reload();
    }

    private void reload() {

        colorPreview = new ItemStack(Material.LEATHER_CHESTPLATE);
        colorPreviewMeta = (LeatherArmorMeta) colorPreview.getItemMeta();
        colorPreviewMeta.setDisplayName(MessageUtil.color("&c&lPreview"));
        colorPreviewMeta.setColor(Color.fromRGB(
                properties.getRed(),
                properties.getGreen(),
                properties.getBlue()
        ));

        colorPreview.setItemMeta(colorPreviewMeta);

        colorPreviewSlot.setItem(colorPreview);

        ItemStack redButton = MenuUtil.getSkull("&cRood = " + this.properties.getRed(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjA2MmQ4ZDcyZjU4OTFjNzFmYWIzMGQ1MmUwNDgxNzk1YjNkMmQzZDJlZDJmOGI5YjUxN2Q3ZDI4MjFlMzVkNiJ9fX0=");
        ItemStack greenButton = MenuUtil.getSkull("&2Groen = " + this.properties.getGreen(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNlOWY0ZGJhZGRlMGY3MjdjNTgwM2Q3NWQ4YmIzNzhmYjlmY2I0YjYwZDMzYmVjMTkwOTJhM2EyZTdiMDdhOSJ9fX0=");
        ItemStack blueButton = MenuUtil.getSkull("&3Blauw = " + this.properties.getBlue(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I1MTA2YjA2MGVhZjM5ODIxNzM0OWYzY2ZiNGYyYzdjNGZkOWEwYjAzMDdhMTdlYmE2YWY3ODg5YmUwZmJlNiJ9fX0=");
        ItemStack plusButton = MenuUtil.getSkull("&a+10", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWFjYTg5MWE3MDE1Y2JiYTA2ZTYxYzYwMDg2MTA2OWZhNzg3MGRjZjliMzViNGZlMTU4NTBmNGIyNWIzY2UwIn19fQ==");
        ItemStack minusButton = MenuUtil.getSkull("&4-10", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmZiN2Y1NThiNmY2N2U4NzI1MzI2YTY5YTg1ZGEyNmFlNWIyN2M0NDczZDI1ZDVlOWNhYWM1NzZiNGQ5NjQzMyJ9fX0=");
        ItemStack nextButton = MenuUtil.getNextButton();

        menu.getSlot(12).setItem(redButton);
        menu.getSlot(3).setItem(plusButton);
        menu.getSlot(3).setClickHandler(this::addRed);
        menu.getSlot(21).setItem(minusButton);
        menu.getSlot(21).setClickHandler(this::removeRed);

        menu.getSlot(13).setItem(greenButton);
        menu.getSlot(4).setItem(plusButton);
        menu.getSlot(4).setClickHandler(this::addGreen);
        menu.getSlot(22).setItem(minusButton);
        menu.getSlot(22).setClickHandler(this::removeGreen);

        menu.getSlot(14).setItem(blueButton);
        menu.getSlot(5).setItem(plusButton);
        menu.getSlot(5).setClickHandler(this::addBlue);
        menu.getSlot(23).setItem(minusButton);
        menu.getSlot(23).setClickHandler(this::removeBlue);

        menu.getSlot(17).setItem(nextButton);
        menu.getSlot(17).setClickHandler(this::next);

        menu.open(this.player);
    }

    public void next(Player player, ClickInformation clickInformation) {
        new OptionsClothingMenu(player, this.properties).displayMenu();
    }

    public void addRed(Player player, ClickInformation clickInformation) {
        this.properties.red += 10;
        if (this.properties.red > 255) this.properties.red = 255;
        reload();
    }

    public void removeRed(Player player, ClickInformation clickInformation) {
        this.properties.red -= 10;
        if (this.properties.red < 0) this.properties.red = 0;
        reload();
    }

    public void addGreen(Player player, ClickInformation clickInformation) {
        this.properties.green += 10;
        if (this.properties.green > 255) this.properties.green = 255;
        reload();
    }

    public void removeGreen(Player player, ClickInformation clickInformation) {
        this.properties.green -= 10;
        if (this.properties.green < 0) this.properties.green = 0;
        reload();
    }

    public void addBlue(Player player, ClickInformation clickInformation) {
        this.properties.blue += 10;
        if (this.properties.blue > 255) this.properties.blue = 255;
        reload();
    }

    public void removeBlue(Player player, ClickInformation clickInformation) {
        this.properties.blue -= 10;
        if (this.properties.blue < 0) this.properties.blue = 0;
        reload();
    }
}
