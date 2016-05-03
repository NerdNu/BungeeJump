package nu.nerd.bungeejump.commands;

import nu.nerd.bungeejump.BungeeJump;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;


public class CommandInjector {


    private BungeeJump plugin;


    public CommandInjector() {
        this.plugin = BungeeJump.getInstance();
    }


    public void injectCommand(String name, CommandExecutor executor) {
        injectCommand(name, null, executor);
    }


    public void injectCommand(String name, String description, CommandExecutor executor) {
        injectCommand(name, description, null, executor);
    }


    public void injectCommand(String name, String description, String permission, CommandExecutor executor) {
        unregisterCommand(name);
        registerCommand(name);
        if (description != null) {
            plugin.getCommand(name).setDescription(description);
        }
        if (permission != null) {
            plugin.getCommand(name).setPermission(permission);
            plugin.getCommand(name).setPermissionMessage(ChatColor.RED + "You don't have permission to do that.");
        }
        plugin.getCommand(name).setExecutor(executor);
    }


    private void unregisterCommand(String name) {
        PluginCommand command = plugin.getServer().getPluginCommand(name);
        if (command != null) {
            command.unregister(getCommandMap());
            getKnownCommands((SimpleCommandMap)getCommandMap()).remove(name);
        }
    }


    private void registerCommand(String name) {
        PluginCommand command = getCommand(name, plugin);
        getCommandMap().register(plugin.getDescription().getName(), command);
    }


    private PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return command;
    }


    private static CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commandMap;
    }


    private Map<String, Command> getKnownCommands(SimpleCommandMap commandMap) {
        Map<String, Command> knownCommands = null;
        try {
            Field f = SimpleCommandMap.class.getDeclaredField("knownCommands");
            f.setAccessible(true);
            knownCommands = (Map<String, Command>) f.get(commandMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return knownCommands;
    }


}
