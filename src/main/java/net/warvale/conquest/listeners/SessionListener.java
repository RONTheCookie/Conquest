package net.warvale.conquest.listeners;

import net.warvale.conquest.Conquest;
import net.warvale.conquest.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SessionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.RED+event.getPlayer().getName()+ChatColor.AQUA+" joined!");
        if (Bukkit.getServer().getOnlinePlayers().size() >= Conquest.get().getConfig().getInt("min_players")) {
            Game.get().setCountdownEnabled(true);
        } else {
            event.setJoinMessage(ChatColor.RED+event.getPlayer().getName()+ChatColor.AQUA+" joined, Need "+ChatColor.RED +
                    String.valueOf(Conquest.get().getConfig().getInt("min_players")-Bukkit.getOnlinePlayers().size())
            + ChatColor.AQUA + " more players to start the game!");
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.RED+event.getPlayer().getName()+ChatColor.AQUA+" left!");
        if (Bukkit.getServer().getOnlinePlayers().size() <= Conquest.get().getConfig().getInt("min_players") && Game.get().isCountdownEnabled()) {
            Bukkit.broadcastMessage(ChatColor.RED+"Not enough players! Cancelling countdown.");
            Game.get().setCountdownEnabled(false);
            Game.get().resetCountdown();
        }
    }
}
