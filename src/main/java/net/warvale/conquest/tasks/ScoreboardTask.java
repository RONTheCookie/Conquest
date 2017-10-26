package net.warvale.conquest.tasks;


import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.game.scoreboards.GameScoreboard;
import net.warvale.conquest.game.scoreboards.LobbyScoreboard;
import net.warvale.conquest.utils.dates.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardTask extends BukkitRunnable {

    private static ScoreboardTask instance;

    public static ScoreboardTask getInstance() {
        if (instance == null) {
            instance = new ScoreboardTask();
        }
        return instance;
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            if (Game.get().isState(State.COUNTDOWN)) {
                if (BossbarCountdownTask.getCountdown() >= 60) {
                    LobbyScoreboard.get().newScoreboard(player, ChatColor.RED + "Warvale");
                } else {
                    LobbyScoreboard.get().newScoreboard(player, ChatColor.WHITE + "Starting in " + ChatColor.GREEN +
                            DateUtils.secondsToString(BossbarCountdownTask.getCountdown()));
                }
            } else if (Game.get().isState(State.INGAME)) {
                GameScoreboard.get().newScoreboard(player);
            } else {
                LobbyScoreboard.get().newScoreboard(player);
            }
        }
    }

}
