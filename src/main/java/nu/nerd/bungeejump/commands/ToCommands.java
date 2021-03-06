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
        CommandInjector injector = plugin.getCommandInjector();
        injector.injectCommand("to-server", "Send a player to another server", "bungeejump.admin", this);
        injector.injectCommand("all-to-server", "Send everyone to another server", "bungeejump.admin", this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        if (cmd.getName().equalsIgnoreCase("to-server")) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /to-server <player> <server>");
                return true;
            }
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Could not find player");
                return true;
            }
            plugin.switchServer(target, args[1]);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("all-to-server")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /all-to-server <server>");
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
