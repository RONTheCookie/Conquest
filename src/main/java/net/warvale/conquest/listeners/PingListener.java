package net.warvale.conquest.listeners;

import net.md_5.bungee.api.ChatColor;
import net.warvale.conquest.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        // Pixelific wanted me to disable those. -RONTheCookie
        e.setMaxPlayers(Game.get().getMaxPlayer());

        e.setMotd(ChatColor.DARK_RED + "Warvale" + ChatColor.DARK_GRAY + " ┃ " + ChatColor.GRAY + "Play. Compete. Repeat.");

    }

}
