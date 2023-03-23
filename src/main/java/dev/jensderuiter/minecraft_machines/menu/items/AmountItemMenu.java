package dev.jensderuiter.minecraft_machines.menu.items;

import dev.jensderuiter.minecraft_machines.menu.clothing.BaseClothingMenu;
import dev.jensderuiter.minecraft_machines.menu.clothing.ClothingMenuProperties;
import dev.jensderuiter.minecraft_machines.menu.clothing.OptionsClothingMenu;
import dev.jensderuiter.minecraft_machines.util.MenuUtil;
import dev.jensderuiter.minecraft_machines.util.MessageUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;
import org.ipvp.canvas.type.MenuHolder;

import java.util.List;

public class AmountItemMenu extends BaseItemMenu {

    ChestMenu menu;
    Slot colorPreviewSlot;
    int canBeAdded = 0;
    int added = 0;

    public AmountItemMenu(Player player, ItemMenuProperties properties) {
        super(player, properties);
    }

    @Override
    public String getMenuName() {
        return "hoeveelheid";
    }

    @Override
    public void displayMenu() {
        menu = this.getMenu();

        colorPreviewSlot = menu.getSlot(10);

        int[] filledSlots = new int[]{0, 1, 2, 9, 11, 18, 19, 20};

        ItemStack filledSlotItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta filledSlotItemMeta = filledSlotItem.getItemMeta();
        filledSlotItemMeta.setDisplayName(MessageUtil.color("&c&lPlaats hierin je item"));
        filledSlotItem.setItemMeta(filledSlotItemMeta);

        for (int filledSlot : filledSlots) {
            menu.getSlot(filledSlot).setItem(filledSlotItem);
        }

        menu.getSlot(10).setClickOptions(ClickOptions.ALLOW_ALL);
        menu.getSlot(10).setClickHandler(this::addItem);

        if (this.properties.itemStack != null) {
            menu.getSlot(10).setItem(this.properties.itemStack);
            this.canBeAdded = 64 - this.properties.itemStack.getAmount();
            this.added = this.properties.itemStack.getAmount();
        }

        String plusButtonB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWFjYTg5MWE3MDE1Y2JiYTA2ZTYxYzYwMDg2MTA2OWZhNzg3MGRjZjliMzViNGZlMTU4NTBmNGIyNWIzY2UwIn19fQ==";
        String minusButtonB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmZiN2Y1NThiNmY2N2U4NzI1MzI2YTY5YTg1ZGEyNmFlNWIyN2M0NDczZDI1ZDVlOWNhYWM1NzZiNGQ5NjQzMyJ9fX0=";

        ItemStack plusOneButton = MenuUtil.getSkull("&a+1", plusButtonB64);
        ItemStack plusEightButton = MenuUtil.getSkull("&a+8", plusButtonB64);
        ItemStack minusOneButton = MenuUtil.getSkull("&4-1", minusButtonB64);
        ItemStack minusEightButton = MenuUtil.getSkull("&4-8", minusButtonB64);

        menu.getSlot(4).setItem(plusOneButton);
        menu.getSlot(4).setClickHandler(this::plusOne);
        menu.getSlot(5).setItem(plusEightButton);
        menu.getSlot(5).setClickHandler(this::plusEight);

        menu.getSlot(22).setItem(minusOneButton);
        menu.getSlot(22).setClickHandler(this::minusOne);
        menu.getSlot(23).setItem(minusEightButton);
        menu.getSlot(23).setClickHandler(this::minusEight);

        ItemStack nextButton = MenuUtil.getNextButton();
        menu.getSlot(16).setItem(nextButton);
        menu.getSlot(16).setClickHandler(this::next);

        reload();
    }

    public void next(Player player, ClickInformation clickInformation) {
        if (added == 0 || canBeAdded == 0) return;
        willGetItemBack = false;
        new OptionsItemMenu(player, this.properties).displayMenu();
    }

    private void reload() {
        List<ItemStack> numberRepr = MenuUtil.representNumber(this.properties.amount);
        if (numberRepr.size() == 1) {
            menu.getSlot(14).setItem(numberRepr.get(0));
            menu.getSlot(13).setItem(null);
        } else {
            menu.getSlot(13).setItem(numberRepr.get(0));
            menu.getSlot(14).setItem(numberRepr.get(1));
        }
        menu.open(this.player);
    }

    public void plusOne(Player player, ClickInformation clickInformation) {
        this.properties.amount += 1;
        if (this.properties.amount > canBeAdded) this.properties.amount = canBeAdded;
        reload();
    }

    public void plusEight(Player player, ClickInformation clickInformation) {
        this.properties.amount += 8;
        if (this.properties.amount > canBeAdded) this.properties.amount = canBeAdded;
        reload();
    }

    public void minusOne(Player player, ClickInformation clickInformation) {
        this.properties.amount -= 1;
        if (this.properties.amount < added)
            this.properties.amount = added;
        reload();
    }

    public void minusEight(Player player, ClickInformation clickInformation) {
        this.properties.amount -= 8;
        if (this.properties.amount < added)
            this.properties.amount = added;
        reload();
    }

    public void addItem(Player player, ClickInformation clickInformation) {
        if (clickInformation.getClickedSlot().getIndex() != 10) return;
        if (clickInformation.getClickType().isRightClick() && this.properties.itemStack == null) {
            this.properties.itemStack = clickInformation.getAddingItem().clone();
            this.properties.itemStack.setAmount(1);
            return;
        }
        ItemStack oldItemStack = null;
        for (MenuHolder holder : menu.getHolders()) {
            oldItemStack = holder.getInventory().getItem(10);
            break;
        }
        if (oldItemStack != null) {
            if (clickInformation.isAddingItem()) {
                if (clickInformation.getClickType().isRightClick()) {
                    this.properties.itemStack.setAmount(this.properties.itemStack.getAmount() + 1);
                } else {
                    this.properties.itemStack.setAmount(this.properties.itemStack.getAmount() + oldItemStack.getAmount());
                }
            } else {
                if (clickInformation.getClickType().isRightClick()) {
                    this.properties.itemStack.setAmount(this.properties.itemStack.getAmount() -
                            (int) Math.round(Math.ceil(this.properties.itemStack.getAmount() / 2)));
                } else {
                    this.properties.itemStack.setAmount(this.properties.itemStack.getAmount() - oldItemStack.getAmount());
                }
            }
        } else {
            if (clickInformation.getClickType().isRightClick()) {
                this.properties.itemStack.setAmount((int) Math.round(Math.ceil(this.properties.itemStack.getAmount() / 2)));
            } else {
                if (clickInformation.isAddingItem()) {
                    this.properties.itemStack = clickInformation.getAddingItem().clone();
                }
            }
        }

        this.canBeAdded = 64 - this.properties.itemStack.getAmount();
        this.added = this.properties.itemStack.getAmount();
    }
}
