package nu.nerd.bungeejump.commands;

import nu.nerd.bungeejump.BungeeJump;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;


public class ServerCommands implements CommandExecutor {


    private BungeeJump plugin;


    public ServerCommands() {

        this.plugin = BungeeJump.getInstance();
        plugin.getCommandInjector().injectCommand("server", "Switch servers", this);

        for (Map.Entry<String, String> entry : plugin.getConfiguration().getAliases().entrySet()) {
            plugin.getCommandInjector().injectCommand(entry.getKey(), "Switch servers", this);
        }

    }


    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Console cannot change servers!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("server")) {
            if (args.length == 1) {
                plugin.switchServer(player, args[0]);
                return true;
            } else {
                List<String> servers = plugin.getConfiguration().getServers();
                StringBuilder sb = new StringBuilder();
                for (String s : servers) {
                    sb.append(s);
                    if (servers.indexOf(s) < servers.size()-1) {
                        sb.append("|");
                    }
                }
                player.sendMessage(String.format("Usage: /server <%s>", sb.toString()));
                return true;
            }
        } else {
            String serverName = name.toLowerCase();
            for (Map.Entry<String, String> entry : plugin.getConfiguration().getAliases().entrySet()) {
                if (serverName.equalsIgnoreCase(entry.getKey())) {
                    plugin.switchServer(player, serverName);
                    break;
                }
            }
        }

        return false;

    }


}
