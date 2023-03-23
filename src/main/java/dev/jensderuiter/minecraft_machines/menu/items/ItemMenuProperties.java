package dev.jensderuiter.minecraft_machines.menu.items;

import dev.jensderuiter.minecraft_machines.menu.BaseMenuProperties;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@Builder
public class ItemMenuProperties extends BaseMenuProperties {

    @Builder.Default
    int amount = 1;

    @Builder.Default
    ItemStack itemStack = null;

}
