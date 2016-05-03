package nu.nerd.bungeejump;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BungeeJumpListener implements Listener {


    private BungeeJump plugin;


    public BungeeJumpListener() {
        plugin = BungeeJump.getInstance();
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String server = plugin.getSwitching().get(event.getPlayer().getUniqueId());
        String msg = plugin.getConfiguration().getSwitchMessage();
        if (server != null && msg != null) {
            plugin.getSwitching().remove(event.getPlayer().getUniqueId());
            msg = msg.replace("%p", event.getPlayer().getName());
            msg = msg.replace("%s", server);
            event.setQuitMessage(msg);
        }
    }


}
