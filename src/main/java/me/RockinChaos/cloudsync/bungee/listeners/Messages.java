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
package me.RockinChaos.cloudsync.bungee.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.RockinChaos.cloudsync.bungee.CloudSync;
import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Messages implements Listener {

    /**
     * Called a message is sent to the specified CHANNEL.
     *
     * @param event PluginMessageEvent
     */
    @EventHandler()
    public void onPluginMessage(final PluginMessageEvent event) {
        if (event.getTag().equalsIgnoreCase(CloudSync.getPluginChannel())) {
            final DataInputStream stream = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                final String identifier = stream.readUTF();
                final String command = stream.readUTF();
                final ProxiedPlayer receiver = ((Plugin) Snap.getSnap().getPlugin()).getProxy().getPlayer(event.getReceiver().toString());
                if (identifier.equals("c") || identifier.equals("console")) {
                    ((Plugin) Snap.getSnap().getPlugin()).getProxy().getPluginManager().dispatchCommand(((Plugin) Snap.getSnap().getPlugin()).getProxy().getConsole(), command);
                    this.sendConfirmation(receiver.getServer().getInfo());
                } else if (((Plugin) Snap.getSnap().getPlugin()).getProxy().getPlayer(identifier) != null) {
                    ((Plugin) Snap.getSnap().getPlugin()).getProxy().getPluginManager().dispatchCommand(((Plugin) Snap.getSnap().getPlugin()).getProxy().getPlayer(identifier), command);
                    this.sendConfirmation(receiver.getServer().getInfo());
                }
            } catch (Exception e) {
                ServerUtils.sendSevereTrace(e);
            }
        }
    }

    /**
     * Sends a confirmation data packet.
     *
     * @param server The Server to send the confirmation.
     */
    @SuppressWarnings("UnstableApiUsage")
    private void sendConfirmation(final @Nonnull ServerInfo server) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Confirmation");
        server.sendData(CloudSync.getPluginChannel(), out.toByteArray());
    }
}