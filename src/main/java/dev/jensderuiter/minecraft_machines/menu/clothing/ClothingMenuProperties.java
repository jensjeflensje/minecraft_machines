package dev.jensderuiter.minecraft_machines.menu.clothing;

import dev.jensderuiter.minecraft_machines.menu.BaseMenuProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class ClothingMenuProperties extends BaseMenuProperties {

    @Builder.Default
    int red = 0;
    @Builder.Default
    int green = 0;
    @Builder.Default
    int blue = 0;

}
