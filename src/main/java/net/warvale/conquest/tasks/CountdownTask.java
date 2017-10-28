package net.warvale.conquest.tasks;

import net.warvale.conquest.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {
    @Override
    public void run() {
        if (Game.get().isCountdownEnabled() && Game.get().getCountdown() > 0) {
            Game.get().setCountdown(Game.get().getCountdown() - 1);
            if (Game.get().getCountdown() == 0) Game.get().start(); else {
                Bukkit.broadcastMessage(ChatColor.RED + "Countdown: " + ChatColor.AQUA + String.valueOf(Game.get().getCountdown()));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, 5);

                }
            }
        }
    }
}
