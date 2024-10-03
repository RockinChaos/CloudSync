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
package me.RockinChaos.cloudsync.bukkit;

import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Welcome to the magical land of make believe.
 * This class simply exists in the event someone can't read that this is only a proxy plugin!
 * Though... they probably won't read the console either.
 */
public class CloudSync extends JavaPlugin {

    /**
     * Called when the plugin is loaded.
     */
    @Override
    public void onLoad() {
        new Snap(this, this.getServer(), this.getDataFolder(), this.getFile(), this.getDescription().getVersion(), null);
    }

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        ServerUtils.logSevere("Woah there! This is not a proxy server!");
        ServerUtils.logSevere("I should only be ran on BungeeCord (or similar software) or Velocity... disabling...");
        this.getPluginLoader().disablePlugin(this);
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        ServerUtils.logWarn("has been Disabled.");
    }
}