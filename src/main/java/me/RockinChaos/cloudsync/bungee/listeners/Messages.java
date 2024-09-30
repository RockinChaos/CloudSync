/*
 * CloudSync
 * Copyright (C) CraftationGaming <https://www.craftationgaming.com/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package src.main.java.me.RockinChaos.cloudsync.bungee.listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import src.main.java.me.RockinChaos.cloudsync.bungee.utils.ServerUtils;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import src.main.java.me.RockinChaos.cloudsync.bungee.CloudSync;

public class Messages implements Listener {

   /**
    * Called a message is sent to the specified CHANNEL.
    * 
    * @param event - PluginMessageEvent
    */
	@EventHandler()
    public void onPluginMessage(final PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase(CloudSync.getInstance().PLUGIN_CHANNEL)) {
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                final String type = stream.readUTF();
                final String command = stream.readUTF();
                if (type.equals("c")) {
                    CloudSync.getInstance().getProxy().getPluginManager().dispatchCommand(CloudSync.getInstance().getProxy().getConsole(), command);
                } else {
                    CloudSync.getInstance().getProxy().getPluginManager().dispatchCommand(CloudSync.getInstance().getProxy().getPlayer(type), command);
                }
                this.sendConfirmation(CloudSync.getInstance().getProxy().getPlayer(event.getReceiver().toString()).getServer().getInfo());
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
	    server.sendData(CloudSync.getInstance().PLUGIN_CHANNEL, out.toByteArray());
	}
}