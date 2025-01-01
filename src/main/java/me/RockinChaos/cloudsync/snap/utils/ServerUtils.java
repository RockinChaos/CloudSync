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
package me.RockinChaos.cloudsync.snap.utils;

import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import com.velocitypowered.proxy.console.VelocityConsole;
import me.RockinChaos.cloudsync.snap.Snap;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.command.ConsoleCommandSender;
import org.apache.logging.log4j.jul.CoreLogger;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class ServerUtils {

    /**
     * Sends a low priority log message as the plugin header.
     *
     * @param message The un-formatted message text to be sent.
     */
    public static void logInfo(@Nonnull String message) {
        String prefix = Snap.getSnap().isBungee() ? "[" + Snap.getSnap().getName() + "] " : "";
        message = prefix + message;
        Snap.getSnap().getLogger().info(message);
    }

    /**
     * Sends a warning message as the plugin header.
     *
     * @param message The un-formatted message text to be sent.
     */
    public static void logWarn(@Nonnull String message) {
        String prefix = Snap.getSnap().isBungee() ? "[" + Snap.getSnap().getName() + "_WARN] " : "";
        message = prefix + message;
        Snap.getSnap().getLogger().warning(message);
    }

    /**
     * Sends a developer warning message as the plugin header.
     *
     * @param message The un-formatted message text to be sent.
     */
    public static void logDev(@Nonnull String message) {
        String prefix = Snap.getSnap().isBungee() ? "[" + Snap.getSnap().getName() + "_DEVELOPER] " : "";
        message = prefix + message;
        Snap.getSnap().getLogger().warning(message);
    }

    /**
     * Sends a error message as the plugin header.
     *
     * @param message The un-formatted message text to be sent.
     */
    public static void logSevere(@Nonnull String message) {
        String prefix = Snap.getSnap().isBungee() ? "[" + Snap.getSnap().getName() + "_ERROR] " : "";
        if (message.isEmpty()) {
            message = "";
        }
        Snap.getSnap().getLogger().severe(prefix + message);
    }

    /**
     * Sends a debug message as a loggable warning as the plugin header.
     *
     * @param message The un-formatted message text to be sent.
     */
    public static void logDebug(@Nonnull String message) {
        if (Snap.getSnap().debugEnabled()) {
            String prefix = Snap.getSnap().isBungee() ? "[" + Snap.getSnap().getName() + "_DEBUG] " : "";
            message = prefix + message;
            Snap.getSnap().getLogger().info(message);
        }
    }

    /**
     * Sends the StackTrace of an Exception if debugging is enabled.
     *
     * @param e The exception to be sent.
     */
    public static void sendDebugTrace(final @Nonnull Exception e) {
        if (Snap.getSnap().debugEnabled()) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the StackTrace of an Exception if it is Severe.
     *
     * @param e The exception to be sent.
     */
    public static void sendSevereTrace(final @Nonnull Exception e) {
        e.printStackTrace();
    }

    /**
     * Sends the StackTrace of a Throwable if it is Severe.
     *
     * @param e The exception to be sent.
     */
    public static void sendSevereThrowable(final @Nonnull Throwable e) {
        e.printStackTrace();
    }

    /**
     * Sends a chat message to the specified sender.
     *
     * @param sender  The entity to have the message sent.
     * @param message The un-formatted message text to be sent.
     * @param prefix  If the plugin prefix should be added to the message.
     */
    public static void messageSender(final @Nonnull Object sender, @Nonnull String message, boolean prefix) {
        String pluginPrefix = (prefix ? "&7[&b" + Snap.getSnap().getName() + "&7] " : "");
        if (message.contains("blankmessage") || message.isEmpty()) {
            message = "";
        }
        final String prefixMessage = !message.isEmpty() ? pluginPrefix + message : "";
        switch (sender.getClass().getSimpleName()) {
            case "ConsoleCommandSender":
                ((ConsoleCommandSender) sender).sendMessage(ChatColor.stripColor(prefixMessage.replaceAll("&[0-9a-fk-or]", "")));
                break;
            case "UserConnection":
                ((UserConnection) sender).sendMessage(ChatColor.translateAlternateColorCodes('&', prefixMessage));
                break;
            case "ConnectedPlayer":
                ((ConnectedPlayer) sender).sendPlainMessage(prefixMessage.replaceAll("&([0-9a-fk-or])", "§$1"));
                break;
            case "VelocityConsole":
                ((VelocityConsole) sender).sendPlainMessage(message.replaceAll("&[0-9a-fk-or]", ""));
                break;
            case "CoreLogger":
                ((CoreLogger) sender).info(message.replaceAll("&[0-9a-fk-or]", ""));
                break;
            case "Logger":
                ((Logger) sender).info(Snap.getSnap().isBungee() ? ChatColor.stripColor(prefixMessage.replaceAll("&[0-9a-fk-or]", "")) : message.replaceAll("&[0-9a-fk-or]", ""));
                break;
            default:
                Snap.getSnap().getLogger().info(Snap.getSnap().isBungee() ? ChatColor.stripColor(prefixMessage.replaceAll("&[0-9a-fk-or]", "")) : message.replaceAll("&[0-9a-fk-or]", ""));
                break;
        }
    }
}