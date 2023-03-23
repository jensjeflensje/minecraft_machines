package dev.jensderuiter.minecraft_machines;

import dev.jensderuiter.minecraft_machines.command.MachineCommand;
import dev.jensderuiter.minecraft_machines.event.MachineClickListener;
import dev.jensderuiter.minecraft_machines.event.MachinePlaceListener;
import dev.jensderuiter.minecraft_machines.machine.BaseMachine;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import dev.jensderuiter.minecraft_machines.util.ConceptMachine;
import dev.jensderuiter.minecraft_machines.util.MachineUtil;
import dev.jensderuiter.minecraft_machines.util.ReflectionUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

import java.util.HashMap;

public final class MachinesPlugin extends JavaPlugin {

    public static MachinesPlugin plugin;
    public static Economy econ = null;
    public static String prefix;
    public static String infoColor;
    public static String successColor;
    public static String errorColor;

    public static HashMap<String, Class<? extends BaseMachine>> machineTypes = new HashMap<>();
    public static HashMap<Integer, Machine> activeMachines = new HashMap<>();
    public static HashMap<String, Integer> locationMachineIdMap = new HashMap<>();
    public static HashMap<Player, ConceptMachine> machineConcepts = new HashMap<>();


    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        getServer().getPluginManager().registerEvents(new MachinePlaceListener(), this);
        getServer().getPluginManager().registerEvents(new MachineClickListener(), this);

        getCommand("machine").setExecutor(new MachineCommand());

        if (!setupEconomy() ) {
            getLogger().severe("You have to install vault before you can use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        prefix = this.getConfig().getString("prefix");
        infoColor = this.getConfig().getString("info");
        successColor = this.getConfig().getString("success");
        errorColor = this.getConfig().getString("error");

        ReflectionUtil.registerMachineTypes();
        MachineUtil.loadMachines();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
