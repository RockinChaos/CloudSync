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
package me.RockinChaos.cloudsync.velocity;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.api.VelocityMetrics;
import me.RockinChaos.cloudsync.snap.handlers.ConfigHandler;
import me.RockinChaos.cloudsync.snap.utils.SchedulerUtils;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import me.RockinChaos.cloudsync.velocity.listeners.Messages;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;

/**
 * Loaded when the server is Velocity.
 */
public class CloudSync {
    private static final LegacyChannelIdentifier PLUGIN_CHANNEL = new LegacyChannelIdentifier("plugin:cloudsync");
    final VelocityMetrics.Factory metrics;
    private final ProxyServer proxyServer;
    private final CommandManager commandManager;

    /**
     * Called when the Plugin is registered.
     */
    @Inject
    public CloudSync(final @Nonnull ProxyServer proxyServer, final @Nonnull VelocityMetrics.Factory metrics, final @Nonnull PluginDescription pluginDescription, @DataDirectory @Nonnull final Path folder, final @Nonnull CommandManager commandManager) {
        this.proxyServer = proxyServer;
        this.commandManager = commandManager;
        this.metrics = metrics;
        new Snap(this, this.proxyServer, folder.toFile(), new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()), pluginDescription.getVersion().isPresent() ? pluginDescription.getVersion().get() : "", ImmutableMap.of("config.yml", 0));
    }

    /**
     * Gets the static instance of the plugin channel for CloudSync.
     *
     * @return The plugin channel for CloudSync.
     */
    public static @Nonnull LegacyChannelIdentifier getPluginChannel() {
        return PLUGIN_CHANNEL;
    }

    /**
     * Called when the Plugin is enabled.
     */
    @Subscribe
    public void onProxyInitialize(final @Nonnull ProxyInitializeEvent event) {
        this.proxyServer.getChannelRegistrar().register(PLUGIN_CHANNEL);
        this.proxyServer.getEventManager().register(this, new Messages());
        this.commandManager.register(this.commandManager.metaBuilder("cloudsync").aliases("cs").plugin(this).build(), new ChatExecutor());
        ConfigHandler.getConfig().reloadFiles();
        Snap.getSnap().getUpdates();
        if (Snap.getSnap().metricsLogging()) {
            this.metrics.make(this, 23502);
        }
        ServerUtils.logDebug("has been Enabled.");
    }

    @Subscribe
    public void onProxyShutdown(final @Nonnull ProxyShutdownEvent event) {
        SchedulerUtils.cancelTasks();
        this.proxyServer.getChannelRegistrar().unregister(PLUGIN_CHANNEL);
        ServerUtils.logDebug("has been Disabled.");
    }
}