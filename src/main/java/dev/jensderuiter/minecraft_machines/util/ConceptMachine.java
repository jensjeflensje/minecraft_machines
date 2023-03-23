package dev.jensderuiter.minecraft_machines.util;

import dev.jensderuiter.minecraft_machines.machine.BaseMachine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class ConceptMachine {

    Player player;
    Class<? extends BaseMachine> machineType;

}
