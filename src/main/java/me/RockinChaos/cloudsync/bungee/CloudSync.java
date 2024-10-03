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
package me.RockinChaos.cloudsync.bungee;

import com.google.common.collect.ImmutableMap;
import me.RockinChaos.cloudsync.bungee.listeners.Messages;
import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.api.BungeeMetrics;
import me.RockinChaos.cloudsync.snap.handlers.ConfigHandler;
import me.RockinChaos.cloudsync.snap.utils.SchedulerUtils;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Loaded when the server is BungeeCord (or similar software).
 */
public class CloudSync extends Plugin {

    public static final String PLUGIN_CHANNEL = "plugin:cloudsync";

    /**
     * Gets the static instance of the plugin channel for CloudSync.
     *
     * @return The plugin channel for CloudSync.
     */
    public static String getPluginChannel() {
        return PLUGIN_CHANNEL;
    }

    /**
     * Called when the plugin is loaded.
     */
    @Override
    public void onLoad() {
        new Snap(this, this.getProxy(), this.getDataFolder(), this.getFile(), this.getDescription().getVersion(), ImmutableMap.of("config.yml", 0));
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        this.getProxy().registerChannel(PLUGIN_CHANNEL);
        this.getProxy().getPluginManager().registerListener(this, new Messages());
        this.getProxy().getPluginManager().registerCommand(this, new ChatExecutor());
        ConfigHandler.getConfig().reloadFiles();
        Snap.getSnap().getUpdates();
        if (Snap.getSnap().metricsLogging()) {
            new BungeeMetrics(this, 23502);
        }
        ServerUtils.logDebug("has been Enabled.");
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        SchedulerUtils.cancelTasks();
        this.getProxy().unregisterChannel(PLUGIN_CHANNEL);
        ServerUtils.logDebug("has been Disabled.");
    }
}