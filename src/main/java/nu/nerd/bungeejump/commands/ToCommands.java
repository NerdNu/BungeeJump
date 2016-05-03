package nu.nerd.bungeejump.commands;

import nu.nerd.bungeejump.BungeeJump;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ToCommands implements CommandExecutor {


    private BungeeJump plugin;


    public ToCommands() {
        this.plugin = BungeeJump.getInstance();
        plugin.getCommandInjector().injectCommand("to-server", "Send a player to another server", this);
        plugin.getCommandInjector().injectCommand("all-to-server", "Send everyone to another server", this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        if (!sender.hasPermission("bungeejump.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("to-server")) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (args.length != 2) {
                sender.sendMessage("Usage: /to-server <player> <server>");
                return true;
            }
            if (target == null) {
                sender.sendMessage("Could not find player");
                return true;
            }
            plugin.switchServer(target, args[1]);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("all-to-server")) {
            if (args.length != 1) {
                sender.sendMessage("Usage: /all-to-server <server>");
                return true;
            }
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                plugin.switchServer(player, args[0]);
            }
            return true;
        }

        return false;

    }


}
