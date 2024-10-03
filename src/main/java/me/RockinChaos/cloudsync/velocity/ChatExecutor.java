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

import com.velocitypowered.api.command.SimpleCommand;
import me.RockinChaos.cloudsync.snap.handlers.CommandHandler;
import me.RockinChaos.cloudsync.snap.handlers.CommandHandler.Execute;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ChatExecutor implements SimpleCommand {

    /**
     * Called when the Command Source executes a command.
     *
     * @param invocation The invoker of the command referencing the source and arguments.
     */
    @Override
    public void execute(final @Nonnull Invocation invocation) {
        CommandHandler.executeCommand(invocation.source(), invocation.arguments());
    }

    /**
     * Called when the Command Source tries to TabComplete.
     *
     * @param invocation The invoker of the command referencing the source and arguments.
     */
    @Override
    public @Nonnull List<String> suggest(final @Nonnull Invocation invocation) {
        final List<String> suggestions = new ArrayList<>();
        if (invocation.arguments().length <= 1) {
            for (final Execute executor : Execute.values()) {
                if (!executor.command().trim().isEmpty() && (invocation.arguments().length == 0 || executor.command().trim().startsWith(invocation.arguments()[0].trim()))) {
                    suggestions.add(executor.command().trim());
                }
            }
        }
        return suggestions;
    }
}