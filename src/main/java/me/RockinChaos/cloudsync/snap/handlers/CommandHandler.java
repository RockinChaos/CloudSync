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

import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import me.RockinChaos.cloudsync.snap.Snap;
import me.RockinChaos.cloudsync.snap.utils.ServerUtils;
import me.RockinChaos.cloudsync.snap.utils.StringUtils;
import net.md_5.bungee.UserConnection;

import javax.annotation.Nonnull;

public class CommandHandler {

    /**
     * Called when the CommandSender executes a command.
     *
     * @param sender Source of the command.
     * @param args   Passed command arguments.
     */
    public static void executeCommand(final @Nonnull Object sender, final @Nonnull String[] args) {
        final Execute executor = matchExecutor(args);
        if (Execute.DEFAULT.accept(sender, args)) {
            ServerUtils.messageSender(sender, ("&bCloudSync v" + Snap.getSnap().getVersion() + "&e by RockinChaos"), false);
            ServerUtils.messageSender(sender, "&aType &a&l/CloudSync Help&a for the help menu.", false);
        } else if (Execute.HELP.accept(sender, args)) {
            ServerUtils.messageSender(sender, "", false);
            ServerUtils.messageSender(sender, "&a&l&m]-----------------&a&l[&b CloudSync &a&l]&a&l&m----------------[", false);
            ServerUtils.messageSender(sender, ("&aCloudSync v" + Snap.getSnap().getVersion() + "&e by RockinChaos"), false);
            ServerUtils.messageSender(sender, "&a&l/CloudSync Help &7- &eThis help menu.", false);
            ServerUtils.messageSender(sender, "&a&l/CloudSync Updates &7- &eChecks for plugin updates.", false);
            ServerUtils.messageSender(sender, "&a&l/CloudSync Upgrade &7- &eUpdates to latest version.", false);
            ServerUtils.messageSender(sender, "&aFound a bug? Report it @", false);
            ServerUtils.messageSender(sender, "&ahttps://github.com/RockinChaos/CloudSync/issues", false);
            ServerUtils.messageSender(sender, "&a&l&m]---------------&a&l[&b Help Menu 1/1 &a&l]&a&l&m--------------[", false);
            ServerUtils.messageSender(sender, "", false);
        } else if (Execute.RELOAD.accept(sender, args)) {
            ConfigHandler.getConfig().reloadFiles();
            ServerUtils.messageSender(sender, "&aConfiguration(s) Reloaded!", true);
        } else if (Execute.UPDATE.accept(sender, args)) {
            Snap.getSnap().getUpdates().checkUpdates(sender, true);
        } else if (Execute.UPGRADE.accept(sender, args)) {
            Snap.getSnap().getUpdates().forceUpdates(sender);
        } else if (executor == null) {
            ServerUtils.messageSender(sender, "&cUnknown command, See &l/CloudSync Help &cfor a list of commands.", true);
        } else if (!executor.hasPermission(sender)) {
            ServerUtils.messageSender(sender, "&cYou do not have permission to use that command!", true);
        }
    }

    /**
     * Called when the Sender executes a command.
     *
     * @param args Passed command arguments.
     * @return The found Executor.
     */
    private static Execute matchExecutor(final @Nonnull String[] args) {
        for (Execute command : Execute.values()) {
            if (command.acceptArgs(args)) {
                return command;
            }
        }
        return null;
    }

    /**
     * Defines the config Command type for the command.
     */
    public enum Execute {
        DEFAULT("", "cloudsync.use", true),
        HELP("help", "cloudsync.use", true),
        RELOAD("rl, reload", "cloudsync.reload", false),
        UPDATE("update, updates", "cloudsync.updates", false),
        UPGRADE("upgrade", "cloudsync.upgrade", false);
        private final String command;
        private final String permission;
        private final boolean permissionDefault;

        /**
         * Creates a new Execute instance.
         *
         * @param command           The expected command argument.
         * @param permission        The expected command permission requirement.
         * @param permissionDefault If the permission is not set, then the permission requirement will be ignored.
         */
        Execute(final @Nonnull String command, final @Nonnull String permission, final boolean permissionDefault) {
            this.command = command;
            this.permission = permission;
            this.permissionDefault = permissionDefault;
        }

        /**
         * Gets the command as a String.
         *
         * @return The command as a String.
         */
        public String command() {
            final String[] commands = this.command.split(",");
            return commands[commands.length - 1];
        }

        /**
         * Called when the Sender executes a command.
         *
         * @param sender Source of the command.
         * @param args   Passed command arguments.
         * @return If the command execution is accepted.
         */
        public boolean accept(final @Nonnull Object sender, final @Nonnull String[] args) {
            return (((args.length == 0 && this.equals(Execute.DEFAULT)) || (args.length == 1 && StringUtils.splitIgnoreCase(this.command, args[0], ","))) && this.hasPermission(sender));
        }

        /**
         * Checks if the executed command is the same as the executor.
         *
         * @param args Passed command arguments.
         * @return If the passed arguments are valid.
         */
        public boolean acceptArgs(final @Nonnull String[] args) {
            return (args.length == 0 || StringUtils.splitIgnoreCase(this.command, args[0], ","));
        }

        /**
         * Checks if the sender has permission to execute the Command.
         *
         * @param sender Source of the command.
         * @return If the sender has permission to execute the command.
         */
        public boolean hasPermission(final @Nonnull Object sender) {
            switch (sender.getClass().getSimpleName()) {
                case "ConsoleCommandSender":
                case "VelocityConsole":
                    return true;
                case "UserConnection":
                    final UserConnection userConnection = ((UserConnection) sender);
                    return userConnection.hasPermission(this.permission) || (this.permissionDefault && !userConnection.getPermissions().contains(this.permission)) || this.isDeveloper(userConnection.getUniqueId().toString());
                case "ConnectedPlayer":
                    final ConnectedPlayer connectedPlayer = ((ConnectedPlayer) sender);
                    return connectedPlayer.hasPermission(this.permission) || (this.permissionDefault && connectedPlayer.getPermissionValue(this.permission).toString().equals("UNDEFINED")) || this.isDeveloper(connectedPlayer.getUniqueId().toString());
                default:
                    return false;
            }
        }

        /**
         * If Debugging Mode is enabled, the plugin developer will be allowed to execute ONLY these plugins commands for help and support purposes.
         *
         * @param uniqueId The UUID String of the player executing the plugin command.
         * @return If the command sender is the developer of the plugin.
         */
        public boolean isDeveloper(final @Nonnull String uniqueId) {
            if (Snap.getSnap().debugEnabled()) {
                return uniqueId.equalsIgnoreCase("ad6e8c0e-6c47-4e7a-a23d-8a2266d7baee");
            }
            return false;
        }
    }
}