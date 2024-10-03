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
package me.RockinChaos.cloudsync.snap;

import me.RockinChaos.cloudsync.snap.handlers.ConfigHandler;
import me.RockinChaos.cloudsync.snap.handlers.UpdateHandler;
import me.RockinChaos.cloudsync.snap.utils.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Snap {

    private static Snap snap;
    private final Object plugin;
    private final Object proxy;
    private final File dataFolder;
    private final File jarFile;
    private final String version;
    private final Map<String, Integer> configs;

    /**
     * Creates a new Snap instance.
     *
     * @param plugin     The plugin instance creating the Snap.
     * @param proxy      The ProxyServer of the Plugin.
     * @param dataFolder The Data Folder of the Plugin.
     * @param jarFile    The Jar File of the Plugin.
     * @param version    The version of the Plugin.
     */
    public Snap(final @Nonnull Object plugin, final @Nonnull Object proxy, final @Nonnull File dataFolder, final @Nonnull File jarFile, final @Nonnull String version, final @Nullable Map<String, Integer> configs) {
        this.plugin = plugin;
        this.proxy = proxy;
        this.dataFolder = dataFolder;
        this.jarFile = jarFile;
        this.version = version;
        this.configs = configs != null && !configs.isEmpty() ? configs : new HashMap<>();
        snap = this;
    }

    /**
     * Gets the static instance of the main class for Snap.
     * Notice: This class is not the actual API class, this is the secondary main class for the Plugin.
     *
     * @return Snap instance.
     */
    public static @Nonnull Snap getSnap() {
        if (snap == null) {
            throw new RuntimeException("{Snap} Tried to access Snap#getSnap but it is not initialized!");
        }
        return snap;
    }

    /**
     * Gets the Plugin instance of the Plugin.
     *
     * @return The Plugin instance of the Plugin.
     */
    public Object getPlugin() {
        return this.plugin;
    }

    /**
     * Gets the ProxyServer for the Plugin.
     *
     * @return The ProxyServer for the Plugin.
     */
    public Object getProxy() {
        return this.proxy;
    }

    /**
     * Checks if the Proxy is BungeeCord (or a similar fork).
     *
     * @return If the Proxy is BungeeCord.
     */
    public boolean isBungee() {
        return !StringUtils.containsIgnoreCase(this.proxy.getClass().getName(), "velocitypowered");
    }

    /**
     * Gets the Logger of the Plugin.
     *
     * @return The Logger of the Plugin.
     */
    public @Nonnull Logger getLogger() {
        return Logger.getLogger(getName());
    }

    /**
     * Gets the Version of the Plugin.
     *
     * @return The Version of the Plugin.
     */
    public @Nonnull String getVersion() {
        return this.version;
    }

    /**
     * Gets the Name of the Plugin.
     *
     * @return The Name of the Plugin.
     */
    public @Nonnull String getName() {
        return this.plugin.getClass().getSimpleName();
    }

    /**
     * Gets the Data Folder of the Plugin.
     *
     * @return The Data Folder of the Plugin.
     */
    public File getDataFolder() {
        return this.dataFolder;
    }

    /**
     * Gets the Jar File of the Plugin.
     *
     * @return The Jar File of the Plugin.
     */
    public File getJarFile() {
        return this.jarFile;
    }

    /**
     * Gets the Map of available configs and their version numbers.
     *
     * @return The Map of available configs.
     */
    public @Nonnull Map<String, Integer> getConfigs() {
        return this.configs;
    }

    /**
     * Checks if Debugging is enabled.
     *
     * @return If Debugging is enabled.
     */
    public boolean debugEnabled() {
        return ConfigHandler.getConfig().getBoolean("config.yml", "General.Debugging");
    }

    /**
     * Checks if the plugin can check for updates.
     *
     * @return If update checking is enabled.
     */
    public boolean checkForUpdates() {
        return ConfigHandler.getConfig().getBoolean("config.yml", "General.CheckforUpdates");
    }

    /**
     * Checks if the plugin can send metrics logs.
     *
     * @return If metrics logging is enabled.
     */
    public boolean metricsLogging() {
        return ConfigHandler.getConfig().getBoolean("config.yml", "General.Metrics-Logging");
    }

    /**
     * Gets the UpdateHandler for the Plugin.
     *
     * @return The cached UpdateHandler.
     */
    public @Nonnull UpdateHandler getUpdates() {
        return UpdateHandler.getUpdater(this.jarFile, this.checkForUpdates());
    }
}