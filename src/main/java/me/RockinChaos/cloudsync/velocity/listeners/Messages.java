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
package me.RockinChaos.cloudsync.velocity.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import me.RockinChaos.cloudsync.velocity.CloudSync;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Messages {

    /**
     * Called a message is sent to the specified CHANNEL.
     *
     * @param event PluginMessageEvent
     */
    @Subscribe
    public void onPluginMessage(final PluginMessageEvent event) {
        if (event.getIdentifier().getId().equalsIgnoreCase(CloudSync.getPluginChannel().getId())) {
            final DataInputStream stream = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                final String identifier = stream.readUTF();
                final String command = stream.readUTF();
                if (identifier.equals("c") || identifier.equals("console")) {
                    ((ProxyServer) Snap.getSnap().getProxy()).getCommandManager().executeImmediatelyAsync(((ProxyServer) Snap.getSnap().getProxy()).getConsoleCommandSource(), command);
                    this.sendConfirmation((ServerConnection) event.getSource());
                } else if (((ProxyServer) Snap.getSnap().getProxy()).getPlayer(identifier).isPresent()) {
                    ((ProxyServer) Snap.getSnap().getProxy()).getCommandManager().executeImmediatelyAsync(((ProxyServer) Snap.getSnap().getProxy()).getPlayer(identifier).get(), command);
                    this.sendConfirmation((ServerConnection) event.getSource());
                }
            } catch (Exception e) {
                ServerUtils.sendSevereTrace(e);
            }
        }
    }

    /**
     * Sends a confirmation data packet.
     *
     * @param connection The Server to send the confirmation.
     */
    @SuppressWarnings("UnstableApiUsage")
    private void sendConfirmation(final @Nonnull ServerConnection connection) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Confirmation");
        connection.sendPluginMessage(CloudSync.getPluginChannel(), out.toByteArray());
    }
}