package nu.nerd.bungeejump;

import nu.nerd.bungeejump.commands.CommandInjector;
import nu.nerd.bungeejump.commands.MCBouncerCommands;
import nu.nerd.bungeejump.commands.ServerCommands;
import nu.nerd.bungeejump.commands.ToCommands;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public class BungeeJump extends JavaPlugin {


    private static BungeeJump instance;
    private Configuration configuration;
    private CommandInjector commandInjector;
    private HashMap<UUID, String> switching;


    public void onEnable() {
        BungeeJump.instance = this;
        this.configuration = new Configuration().load();
        this.commandInjector = new CommandInjector();
        this.switching = new HashMap<UUID, String>();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(new BungeeJumpListener(), this);
        new ServerCommands();
        new ToCommands();
        if (getConfiguration().isMcbouncer()) {
            new MCBouncerCommands();
        }
    }


    public void sendBungeeMessage(Player player, String... args) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            for (String arg : args) {
                out.writeUTF(arg);
            }
            player.sendPluginMessage(this, "BungeeCord", stream.toByteArray());
            debugPrint("Sent to Bungee: " + stream.toString());
        } catch (IOException ex) {
            getLogger().warning(String.format("Error sending message \"%s\" to Bungee: %s", stream.toString(), ex.getMessage()));
        }
    }


    public void switchServer(Player player, String server) {
        server = server.toLowerCase();
        for (String s : getConfiguration().getAliases().keySet()) {
            if (s.equals(server)) {
                getSwitching().put(player.getUniqueId(), server);
                break;
            }
        }
        sendBungeeMessage(player, "Connect", server);
    }


    public void debugPrint(String message) {
        if (configuration.isDebug()) {
            getLogger().info(message);
        }
    }


    public static BungeeJump getInstance() {
        return instance;
    }


    public Configuration getConfiguration() {
        return configuration;
    }


    public CommandInjector getCommandInjector() {
        return commandInjector;
    }


    public HashMap<UUID, String> getSwitching() {
        return switching;
    }


}
