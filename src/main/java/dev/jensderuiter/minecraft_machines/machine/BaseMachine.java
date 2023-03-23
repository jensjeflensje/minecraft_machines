package dev.jensderuiter.minecraft_machines.machine;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.util.MachineUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BaseMachine implements Machine {

    public String type;
    public int id;
    public int locX;
    public int locY;
    public int locZ;

    public BaseMachine(String type, int id, int locX, int locY, int locZ) {
        this.type = type;
        this.id = id;
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;
        MachinesPlugin.activeMachines.put(id, this);
        MachinesPlugin.locationMachineIdMap.put(locX + "_" + locY + "_"+ locZ, id);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFormattedId() {
        return this.getTypePrefix() + "#" + this.getId();
    }

    @Override
    public void addPlayer(Player player) {
        ConfigurationSection machinesSection = MachinesPlugin.plugin.getConfig().getConfigurationSection("machines");
        ConfigurationSection machineSection = machinesSection.getConfigurationSection(String.valueOf(this.getId()));
        List<String> addedPlayers = machineSection.getStringList("players");
        addedPlayers.add(player.getUniqueId().toString());
        machineSection.set("players", addedPlayers);
        MachinesPlugin.plugin.saveConfig();
    }

    @Override
    public void remove() {
        ConfigurationSection machinesSection = MachinesPlugin.plugin.getConfig().getConfigurationSection("machines");
        machinesSection.set(String.valueOf(this.getId()), null);
        MachinesPlugin.plugin.saveConfig();
        MachinesPlugin.locationMachineIdMap.remove(locX + "_" + locY + "_"+ locZ);
        MachinesPlugin.activeMachines.remove(this);
    }

    @Override
    public void save() {
        ConfigurationSection machinesSection = MachinesPlugin.plugin.getConfig().getConfigurationSection("machines");
        ConfigurationSection machineSection = machinesSection.getConfigurationSection(String.valueOf(this.getId()));
        if (machineSection == null) {
            machineSection = machinesSection.createSection(String.valueOf(this.getId()));
        }
        machineSection.set("type", this.type);
        machineSection.set("loc.x", this.locX);
        machineSection.set("loc.y", this.locY);
        machineSection.set("loc.z", this.locZ);
        MachinesPlugin.plugin.saveConfig();
    }
}
