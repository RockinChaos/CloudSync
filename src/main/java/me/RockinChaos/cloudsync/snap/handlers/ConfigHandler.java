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
package me.RockinChaos.cloudsync.snap.handlers;

import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import me.RockinChaos.cloudsync.snap.utils.StringUtils;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

@SuppressWarnings("unused")
public class ConfigHandler {

    private static ConfigHandler config;
    private final HashMap<String, Boolean> noSource = new HashMap<>();
    private final Map<String, Map<String, Object>> configFiles = new HashMap<>();
    private final Yaml yaml = new Yaml();

    /**
     * Gets the instance of the ConfigHandler.
     *
     * @return The ConfigHandler instance.
     */
    public static ConfigHandler getConfig() {
        if (config == null) {
            config = new ConfigHandler();
        }
        return config;
    }

    /**
     * Copies files into memory.
     */
    public void copyFiles() {
        final Map<String, Integer> configs = Snap.getSnap().getConfigs();
        for (final String config : configs.keySet()) {
            this.copyFile(config, config.replace(".yml", "") + "-Version", configs.get(config));
        }
    }

    /**
     * Gets the file from the specified path.
     *
     * @param path YamlThe File to be fetched.
     * @return The parsed YAML data as a Map.
     */
    public Map<String, Object> getFile(@Nonnull final String path) {
        final File file = new File(Snap.getSnap().getDataFolder(), path);
        final boolean hasPath = this.configFiles.containsKey(path);
        if (!hasPath) {
            this.getSource(path);
        }
        try {
            return this.getLoadedConfig(file, false);
        } catch (Exception e) {
            ServerUtils.sendSevereTrace(e);
            ServerUtils.logSevere("Cannot load " + file.getName() + " from disk!");
        }
        return null;
    }

    /**
     * Gets the source file from the specified path.
     *
     * @param path YamlThe File to be loaded.
     */
    public void getSource(@Nonnull final String path) {
        final File file = new File(Snap.getSnap().getDataFolder(), path);
        if (!file.exists()) {
            try {
                final InputStream source = getClass().getClassLoader().getResourceAsStream("files/configs/" + path);
                final File dataDir = Snap.getSnap().getDataFolder();
                if (!dataDir.exists()) {
                    final boolean madeDir = dataDir.mkdir();
                }
                if (source != null) {
                    Files.copy(source, file.toPath());
                }
            } catch (Exception e) {
                ServerUtils.sendSevereTrace(e);
                ServerUtils.logWarn("Cannot save " + path + " to disk!");
                this.noSource.put(path, true);
                return;
            }
        }
        try {
            final Map<String, Object> config = this.getLoadedConfig(file, true);
            this.noSource.put(path, false);
        } catch (Exception e) {
            ServerUtils.sendSevereTrace(e);
            ServerUtils.logSevere("Cannot load " + file.getName() + " from disk!");
            this.noSource.put(file.getName(), true);
        }
    }

    /**
     * Loads and returns the YAML configuration as a Map.
     *
     * @param file   YamlThe file to be loaded.
     * @param commit YamlIf the File should be committed to memory.
     * @return The loaded config file as a Map.
     */
    public Map<String, Object> getLoadedConfig(@Nonnull final File file, final boolean commit) throws Exception {
        if (commit) {
            try (final FileInputStream fis = new FileInputStream(file)) {
                final Map<String, Object> configData = yaml.load(fis);
                this.configFiles.put(file.getName(), configData);
                return configData;
            }
        } else {
            return this.configFiles.getOrDefault(file.getName(), null);
        }
    }

    /**
     * Copies the specified config file to the data folder.
     *
     * @param configFile YamlThe name and extension of the config file to be copied.
     * @param version    YamlThe version String to be checked in the config file.
     * @param id         YamlThe expected version id to be found in the config file.
     */
    private void copyFile(@Nonnull final String configFile, @Nonnull final String version, final int id) {
        this.getSource(configFile);
        final File file = new File(Snap.getSnap().getDataFolder(), configFile);
        final Map<String, Object> configData = this.getFile(configFile);
        if (file.exists() && !this.noSource.get(configFile) && (int) configData.getOrDefault(version, 0) != id) {
            try (final InputStream source = getClass().getClassLoader().getResourceAsStream("files/configs/" + configFile)) {
                if (source != null) {
                    final String[] namePart = configFile.split("\\.");
                    final String renameFile = namePart[0] + "-old-" + StringUtils.getRandom(1, 50000) + "." + namePart[1];
                    final File renamedFile = new File(Snap.getSnap().getDataFolder(), renameFile);
                    if (!renamedFile.exists() && file.renameTo(renamedFile)) {
                        if (file.delete() || !file.delete()) {
                            this.getSource(configFile);
                            ServerUtils.logWarn("Your " + configFile + " is out of date. Generating a new one!");
                        }
                    }
                }
            } catch (IOException e) {
                ServerUtils.logSevere("An error occurred while accessing the resource: " + e.getMessage());
            }
        } else if (this.noSource.get(configFile)) {
            ServerUtils.logSevere("Your " + configFile + " is not using proper YAML syntax!");
        }
    }

    /**
     * Saves the changed configuration data to the file.
     *
     * @param data YamlThe Map containing the YAML data.
     * @param file YamlThe File to save the data to.
     */
    public void saveFile(@Nonnull final Map<String, Object> data, @Nonnull final File file) {
        try (final FileWriter writer = new FileWriter(file)) {
            yaml.dump(data, writer);
            this.getSource(file.getName());
        } catch (Exception e) {
            ServerUtils.logSevere("Could not save data to the " + file.getName() + " data file!");
            ServerUtils.sendDebugTrace(e);
        }
    }

    /**
     * Gets the Boolean value from the Yaml Config at the specified Path.
     *
     * @param config The config file being accessed in the format of "*.yml".
     * @param path   The path of the value being accessed in the format of "hello.world"
     * @return The current Boolean value, if it is null (undefined) it will always be false.
     */
    public boolean getBoolean(final @Nonnull String config, final @Nonnull String path) {
        final String[] keys = path.split("\\.");
        Map<String, Object> currentMap = getFile(config);
        for (int i = 0; i < keys.length - 1; i++) {
            Object value = currentMap.get(keys[i]);
            currentMap = StringUtils.toStringMap(value);
            if (currentMap == null) {
                return false;
            }
        }
        final Object finalValue = currentMap.get(keys[keys.length - 1]);
        return finalValue instanceof Boolean ? (Boolean) finalValue : false;
    }

    /**
     * Gets the Integer value from the Yaml Config at the specified Path.
     *
     * @param config The config file being accessed in the format of "*.yml".
     * @param path   The path of the value being accessed in the format of "hello.world"
     * @return The current Integer value, if it is null (undefined) an error will be thrown.
     */
    public int getInt(final @Nonnull String config, final @Nonnull String path) {
        final String[] keys = path.split("\\.");
        Map<String, Object> currentMap = getFile(config);
        for (int i = 0; i < keys.length - 1; i++) {
            final Object value = currentMap.get(keys[i]);
            currentMap = StringUtils.toStringMap(value);
            if (currentMap == null) {
                throw new RuntimeException("Integer values cannot be null!");
            }
        }
        final Object finalValue = currentMap.get(keys[keys.length - 1]);
        if (finalValue instanceof Number) {
            return ((Number) finalValue).intValue();
        } else {
            throw new RuntimeException("Integer values cannot be null!");
        }
    }

    /**
     * Gets the Double value from the Yaml Config at the specified Path.
     *
     * @param config The config file being accessed in the format of "*.yml".
     * @param path   The path of the value being accessed in the format of "hello.world"
     * @return The current Double value, if it is null (undefined) an error will be thrown.
     */
    public double getDouble(final @Nonnull String config, final @Nonnull String path) {
        final String[] keys = path.split("\\.");
        Map<String, Object> currentMap = getFile(config);
        for (int i = 0; i < keys.length - 1; i++) {
            final Object value = currentMap.get(keys[i]);
            currentMap = StringUtils.toStringMap(value);
            if (currentMap == null) {
                throw new RuntimeException("Double values cannot be null!");
            }
        }
        final Object finalValue = currentMap.get(keys[keys.length - 1]);
        if (finalValue instanceof Number) {
            return ((Number) finalValue).doubleValue();
        } else {
            throw new RuntimeException("Double values cannot be null!");
        }
    }

    /**
     * Gets the String value from the Yaml Config at the specified Path.
     *
     * @param config The config file being accessed in the format of "*.yml".
     * @param path   The path of the value being accessed in the format of "hello.world"
     * @return The current String value, if it is null (undefined) it will return null.
     */
    public @Nullable String getString(final @Nonnull String config, final @Nonnull String path) {
        final String[] keys = path.split("\\.");
        Map<String, Object> currentMap = getFile(config);
        for (int i = 0; i < keys.length - 1; i++) {
            final Object value = currentMap.get(keys[i]);
            currentMap = StringUtils.toStringMap(value);
            if (currentMap == null) {
                return null;
            }
        }
        final Object finalValue = currentMap.get(keys[keys.length - 1]);
        return finalValue instanceof String ? (String) finalValue : null;
    }

    /**
     * Gets the StringList value from the Yaml Config at the specified Path.
     *
     * @param config The config file being accessed in the format of "*.yml".
     * @param path   The path of the value being accessed in the format of "hello.world"
     * @return The current StringList value, if it is null (undefined) it will return null.
     * If it is found to be a String instead of a list then it will still return the String value as a list.
     */
    public @Nullable List<String> getStringList(final @Nonnull String config, final @Nonnull String path) {
        final String[] keys = path.split("\\.");
        Map<String, Object> currentMap = getFile(config);
        for (int i = 0; i < keys.length - 1; i++) {
            final Object value = currentMap.get(keys[i]);
            currentMap = StringUtils.toStringMap(value);
            if (currentMap == null) {
                return null;
            }
        }
        final Object finalValue = currentMap.get(keys[keys.length - 1]);
        if (finalValue instanceof List<?>) {
            final List<?> list = (List<?>) finalValue;
            final List<String> stringList = new ArrayList<>();
            for (final Object item : list) {
                if (item instanceof String) {
                    stringList.add((String) item);
                }
            }
            return stringList;
        }
        final String foundValue = this.getString(config, path);
        return foundValue != null ? Collections.singletonList(foundValue) : null;
    }

    /**
     * Properly reloads the configuration files.
     */
    public void reloadFiles() {
        config = new ConfigHandler();
        config.copyFiles();
    }
}