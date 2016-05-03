package nu.nerd.bungeejump;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;


public class Configuration {


    private BungeeJump plugin;
    private boolean debug;
    private String switchMessage;
    private List<String> servers;
    private HashMap<String, String> aliases;


    public Configuration() {
        plugin = BungeeJump.getInstance();
        load();
    }


    public Configuration load() {

        plugin.saveDefaultConfig();

        debug = plugin.getConfig().getBoolean("debug");
        switchMessage = plugin.getConfig().getString("switchMessage", "§%p has switched to %s");
        servers = plugin.getConfig().getStringList("servers");
        aliases = new HashMap<String, String>();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("aliases");
        for (String key : section.getKeys(false)) {
            if (section.getString(key) != null) {
                aliases.put(key, section.getString(key));
            }
        }

        return this;

    }


    public boolean isDebug() {
        return debug;
    }


    public String getSwitchMessage() {
        return switchMessage;
    }


    public List<String> getServers() {
        return servers;
    }


    public HashMap<String, String> getAliases() {
        return aliases;
    }


}
