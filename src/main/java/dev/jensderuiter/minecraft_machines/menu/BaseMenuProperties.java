package dev.jensderuiter.minecraft_machines.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class BaseMenuProperties {

    @Builder.Default
    boolean unbreakable = false;
    @Builder.Default
    boolean glowing = false;
    @Builder.Default
    String name = "Kies een naam";
    @Builder.Default
    String lore = "";

}
