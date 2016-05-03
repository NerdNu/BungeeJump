package nu.nerd.bungeejump;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;


public class Configuration {


    private BungeeJump plugin;
    private boolean debug;
    private boolean mcbouncer;
    private String switchMessage;
    private HashMap<String, String> aliases;


    public Configuration() {
        plugin = BungeeJump.getInstance();
        load();
    }


    public Configuration load() {

        plugin.saveDefaultConfig();

        debug = plugin.getConfig().getBoolean("debug", false);
        mcbouncer = plugin.getConfig().getBoolean("mcbouncer", false);
        switchMessage = plugin.getConfig().getString("switchMessage", "§e%p has switched to %s");
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


    public boolean isMcbouncer() {
        return mcbouncer;
    }

    public String getSwitchMessage() {
        return switchMessage;
    }


    public HashMap<String, String> getAliases() {
        return aliases;
    }


}
