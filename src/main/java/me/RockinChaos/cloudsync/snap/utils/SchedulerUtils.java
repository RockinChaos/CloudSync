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

import com.velocitypowered.api.scheduler.ScheduledTask;
import me.RockinChaos.cloudsync.snap.Snap;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class SchedulerUtils {

    /**
     * Runs the task on the main thread
     *
     * @param runnable The task to be performed.
     */
    public static void run(final @Nonnull Runnable runnable) {
        if (Snap.getSnap().isBungee()) {
            ((net.md_5.bungee.api.ProxyServer) Snap.getSnap().getProxy()).getScheduler().schedule(((net.md_5.bungee.api.plugin.Plugin) Snap.getSnap().getPlugin()), runnable, 1L, TimeUnit.MILLISECONDS);
        } else {
            /* Velocity doesn't have a "main thread", tasks are ran async. */
            runAsync(runnable);
        }
    }

    /**
     * Runs the task on another thread.
     *
     * @param runnable The task to be performed.
     */
    public static void runAsync(final @Nonnull Runnable runnable) {
        if (Snap.getSnap().isBungee()) {
            ((net.md_5.bungee.api.ProxyServer) Snap.getSnap().getProxy()).getScheduler().runAsync(((net.md_5.bungee.api.plugin.Plugin) Snap.getSnap().getPlugin()), runnable);
        } else {
            ((com.velocitypowered.api.proxy.ProxyServer) Snap.getSnap().getProxy()).getScheduler().buildTask(Snap.getSnap().getPlugin(), runnable).schedule();
        }
    }

    /**
     * Cancels all scheduled tasks for the plugin.
     */
    public static void cancelTasks() {
        if (Snap.getSnap().isBungee()) {
            ((net.md_5.bungee.api.ProxyServer) Snap.getSnap().getProxy()).getScheduler().cancel(((net.md_5.bungee.api.plugin.Plugin) Snap.getSnap().getPlugin()));
        } else {
            for (final ScheduledTask task : ((com.velocitypowered.api.proxy.ProxyServer) Snap.getSnap().getProxy()).getScheduler().tasksByPlugin(Snap.getSnap().getPlugin())) {
                task.cancel();
            }
        }
    }
}