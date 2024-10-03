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

import me.RockinChaos.cloudsync.snap.handlers.CommandHandler;
import me.RockinChaos.cloudsync.snap.handlers.CommandHandler.Execute;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ChatExecutor extends Command implements TabExecutor {

    /**
     * Creates a new ChatExecutor instance.
     */
    public ChatExecutor() {
        super("cloudsync", null, "cs");
    }

    /**
     * Called when the CommandSender executes a command.
     *
     * @param sender Source of the command.
     * @param args   Passed command arguments.
     */
    @Override
    public void execute(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        CommandHandler.executeCommand(sender, args);
    }

    /**
     * Called when the Command Source tries to TabComplete.
     *
     * @param sender Source of the command.
     * @param args   Passed command arguments.
     * @return The Iterable List of TabComplete commands.
     */
    @Override
    public @Nonnull Iterable<String> onTabComplete(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        final Set<String> suggestions = new HashSet<>();
        if (args.length <= 1) {
            for (final Execute executor : Execute.values()) {
                if (!executor.command().trim().isEmpty() && (args.length == 0 || executor.command().trim().startsWith(args[0].trim()))) {
                    suggestions.add(executor.command().trim());
                }
            }
        }
        return suggestions;
    }
}
