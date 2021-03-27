package me.RockinChaos.itemjoin.listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.RockinChaos.itemjoin.ItemJoin;
import me.RockinChaos.itemjoin.utils.ServerUtils;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessage implements Listener {

   /**
    * Called a message is sent to the specified CHANNEL.
    * 
    * @param event - PluginMessageEvent
    */
	@EventHandler()
    public void onPluginMessage(final PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase(ItemJoin.getInstance().PLUGIN_CHANNEL)) {
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                final ProxiedPlayer player = ItemJoin.getInstance().getProxy().getPlayer(stream.readUTF());
                final String command = stream.readUTF();
                this.sendConfirmation(ItemJoin.getInstance().getProxy().getPlayer(event.getReceiver().toString()).getServer().getInfo());
                ItemJoin.getInstance().getProxy().getPluginManager().dispatchCommand(player, command);
            } catch (Exception e) { ServerUtils.sendSevereTrace(e); }
        }
    }
	
   /**
    * Sends a confirmation data packet.
    * 
    * @param server - The Server to send the confirmation.
    */
	private void sendConfirmation(final ServerInfo server) {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Confirmation");
	    server.sendData(ItemJoin.getInstance().PLUGIN_CHANNEL, out.toByteArray());
	}
}