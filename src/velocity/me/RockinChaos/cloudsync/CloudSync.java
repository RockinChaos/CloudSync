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
package velocity.me.RockinChaos.cloudsync;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;

import bungee.me.RockinChaos.cloudsync.handlers.UpdateHandler;
import bungee.me.RockinChaos.cloudsync.utils.api.SnapAPI;
import velocity.me.RockinChaos.cloudsync.listeners.Messages;
import velocity.me.RockinChaos.cloudsync.utils.api.MetricsAPI;

@Plugin(id = "cloudsync", name = "CloudSync", authors = "RockinChaos", description = "A spigot-bungee handshake.", 
		url = "https://www.spigotmc.org/resources/cloudsync.93382/", version = "(PROJECT_VERSION)-b(BUILD_NUMBER)")
public class CloudSync {
    private static ProxyServer proxy;
    private final MetricsAPI.Factory metricsFactory;
    private static LegacyChannelIdentifier PLUGIN_CHANNEL;

   /**
    * Called when the plugin is registered.
    * 
    */
    @Inject
    public CloudSync(final ProxyServer prox, final Logger log, final MetricsAPI.Factory metricsFactory) {
        proxy = prox;
        this.metricsFactory = metricsFactory;
    }

   /**
    * Called when the plugin is enabled.
    * 
    */
    @Subscribe
    public void onProxyInitialize(final ProxyInitializeEvent event) {
        PLUGIN_CHANNEL = new LegacyChannelIdentifier("plugin:cloudsync");
    	SnapAPI.setLogger(java.util.logging.Logger.getLogger(this.getClass().getName()));
    	SnapAPI.setName("CloudSync");
    	SnapAPI.setVersion("$(PROJECT_VERSION)-b(BUILD_NUMBER)");
    	SnapAPI.setServerCount(proxy.getAllServers().size());
    	SnapAPI.setOnlineCount(proxy.getPlayerCount());
    	SnapAPI.setOnline(proxy.getConfiguration().isOnlineMode());
    	UpdateHandler.getUpdater(true);
        metricsFactory.make(this, 10829);
        proxy.getChannelRegistrar().register(PLUGIN_CHANNEL);
        proxy.getEventManager().register(this, new Messages());
    }
    
   /**
    * Gets the static instance of the ProxyServer for CloudSync. 
    *
    * @return The ProxyServer for CloudSync.
    */  
  	public static ProxyServer getProxy() {
  		return proxy;
  	}
  
   /**
    * Gets the static instance of the plugin channel for CloudSync. 
    *
    * @return The plugin channel for CloudSync.
    */  
  	public static LegacyChannelIdentifier getPluginChannel() {
  		return PLUGIN_CHANNEL;
  	}
}