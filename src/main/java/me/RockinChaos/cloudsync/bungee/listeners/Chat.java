package src.main.java.me.RockinChaos.cloudsync.bungee.listeners;

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Chat implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerChat(ChatEvent event) {
	  event.setCancelled(false);
  }
}