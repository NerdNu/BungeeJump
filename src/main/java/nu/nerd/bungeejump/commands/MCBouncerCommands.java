package nu.nerd.bungeejump.commands;


import nu.nerd.bungeejump.BungeeJump;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MCBouncerCommands implements CommandExecutor {


    private BungeeJump plugin;


    public MCBouncerCommands() {
        this.plugin = BungeeJump.getInstance();
        CommandInjector injector = plugin.getCommandInjector();
        injector.injectCommand("kick", "Kick a user from the server", "bungeejump.mcbouncer.kick", this);
        injector.injectCommand("ban", "Ban a username", "bungeejump.mcbouncer.ban", this);
        injector.injectCommand("unban", "Unban a username", "bungeejump.mcbouncer.unban", this);
        injector.injectCommand("lookup", "See all bans and notes for a username", "bungeejump.mcbouncer.lookup", this);
        injector.injectCommand("addnote", "Adds a note to a player", "bungeejump.mcbouncer.addnote", this);
        injector.injectCommand("delnote", "Removes a note", "bungeejump.mcbouncer.delnote", this);
        injector.injectCommand("removenote", "Removes a note", "bungeejump.mcbouncer.removenote", this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Console cannot do that!");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("kick")) {
            if (args.length >= 1) {
                String msg = joinArray(" ", Arrays.copyOfRange(args, 1, args.length));
                plugin.sendBungeeMessage(player, "MCBouncer", "kick", args[0], msg);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /kick <player> [message]");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (args.length >= 1) {
                String msg = joinArray(" ", Arrays.copyOfRange(args, 1, args.length));
                if (!msg.contains("nerd.nu/appeal")) {
                    msg = msg + " nerd.nu/appeal";
                }
                plugin.sendBungeeMessage(player, "MCBouncer", "ban", args[0], msg);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /ban <player> [message]");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("unban")) {
            if (args.length == 1) {
                plugin.sendBungeeMessage(player, "MCBouncer", "unban", args[0]);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /unban <player>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("lookup")) {
            if (args.length == 1) {
                plugin.sendBungeeMessage(player, "MCBouncer", "lookup", args[0]);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /lookup <player>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("addnote")) {
            if (args.length > 1) {
                String msg = joinArray(" ", Arrays.copyOfRange(args, 1, args.length));
                plugin.sendBungeeMessage(player, "MCBouncer", "addnote", args[0], msg);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /addnote <player> <note>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("delnote") || cmd.getName().equalsIgnoreCase("removenote")) {
            if (args.length == 1) {
                plugin.sendBungeeMessage(player, "MCBouncer", "delnote", args[0]);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /delnote <#>");
            }
            return true;
        }

        return false;

    }


    private static String joinArray(String separator, String[] arr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String s : arr) {
            sb.append(s);
            if (i < (arr.length - 1)) {
                sb.append(separator);
            }
            i++;
        }
        return sb.toString().trim();
    }


}
