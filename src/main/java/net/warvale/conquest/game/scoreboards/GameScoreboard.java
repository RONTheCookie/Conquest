package net.warvale.conquest.game.scoreboards;

import com.google.common.collect.Maps;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.GameRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import java.util.Map;
import java.util.UUID;

public class GameScoreboard {

    private static GameScoreboard instance;

    public static GameScoreboard get() {
        if (instance == null) {
            instance = new GameScoreboard();
        }
        return instance;
    }

    //scoreboard map
    private Map<UUID, Scoreboard> scoreboards = Maps.newHashMap();

    public Map<UUID, Scoreboard> getScoreboards() {
        return scoreboards;
    }

    public void addScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("game", "dummy");

        objective.setDisplayName(ChatColor.DARK_GRAY + "» " + ChatColor.DARK_RED + "Warvale: Conquest"
                + ChatColor.DARK_GRAY + " «" );
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team time = scoreboard.registerNewTeam("GameTime");
        time.addEntry(ChatColor.AQUA + "Time:");
        time.setSuffix(" §7");

        Team playerCount = scoreboard.registerNewTeam("PlayerCount");
        playerCount.addEntry(ChatColor.AQUA + "Players:");
        playerCount.setSuffix(" §7");

        Team redHealth = scoreboard.registerNewTeam("redHealth");
        redHealth.addEntry(ChatColor.AQUA + "Red Core:");
        redHealth.setSuffix(" §7");

        Team blueHealth = scoreboard.registerNewTeam("blueHealth");
        blueHealth.addEntry(ChatColor.AQUA + "Blue Core:");
        blueHealth.setSuffix(" §7");


        scoreboards.put(player.getUniqueId(), scoreboard);
    }


    public void removeScoreboard(Player player) {
        if (scoreboards.containsKey(player.getUniqueId())) {
            scoreboards.get(player.getUniqueId()).clearSlot(DisplaySlot.SIDEBAR);
            scoreboards.remove(player.getUniqueId());
        }
    }

    public void shutdown() {
        for (Scoreboard scoreboard : scoreboards.values()) {
            for (Objective objective : scoreboard.getObjectives()) {
                objective.unregister();
            }
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        }
        scoreboards.clear();
    }

    public void newScoreboard(Player p) {

        //add the user to the scoreboard
        if (!getScoreboards().containsKey(p.getUniqueId())) {
            addScoreboard(p);
        }

        //update the scoreboard
        updateTime();
        updatePlayerCount();

        //update health
        updateRedHealth();
        updateBlueHealth();

        //update website
        updateWebsite();

        p.setScoreboard(getScoreboards().get(p.getUniqueId()));
    }

   public void updateTime() {

        for (Player online : Bukkit.getOnlinePlayers()) {

            Objective objective = getScoreboards().get(online.getUniqueId()).getObjective("game");
            Team time = getScoreboards().get(online.getUniqueId()).getTeam("GameTime");
            if (objective != null && time != null) {
                objective.getScore("    ").setScore(9);
                //objective.getScore("§8» §cTime:").setScore(7);

                time.setSuffix(" §7"+ convert(GameRunnable.getSeconds()));
                objective.getScore(ChatColor.AQUA + "Time:").setScore(8);
                objective.getScore("   ").setScore(7);

            }

        }

    }

    public void updatePlayerCount() {

        for (Player online : Bukkit.getOnlinePlayers()) {

            Objective objective = getScoreboards().get(online.getUniqueId()).getObjective("game");
            Team playerCount = getScoreboards().get(online.getUniqueId()).getTeam("PlayerCount");
            if (objective != null && playerCount != null) {

                playerCount.setSuffix(" §7"+ Bukkit.getOnlinePlayers().size() + "/" + Game.get().getMaxPlayer());

                objective.getScore(ChatColor.AQUA + "Players:").setScore(6);
                objective.getScore("  ").setScore(5);
            }

        }

    }

    public void updateRedHealth() {

        for (Player online : Bukkit.getOnlinePlayers()) {

            Objective objective = getScoreboards().get(online.getUniqueId()).getObjective("game");
            Team redHealth = getScoreboards().get(online.getUniqueId()).getTeam("redHealth");
            if (objective != null && redHealth != null) {

                redHealth.setSuffix(" §7" + 20);

                objective.getScore(ChatColor.AQUA + "Red Core:").setScore(4);
            }

        }

    }

    public void updateBlueHealth() {

        for (Player online : Bukkit.getOnlinePlayers()) {

            Objective objective = getScoreboards().get(online.getUniqueId()).getObjective("game");
            Team blueHealth = getScoreboards().get(online.getUniqueId()).getTeam("blueHealth");
            if (objective != null && blueHealth != null) {

                blueHealth.setSuffix(" §7" + 20);

                objective.getScore(ChatColor.AQUA + "Blue Core:").setScore(3);
                objective.getScore(" ").setScore(2);
            }

        }

    }

    public void updateWebsite() {

        for (Player online : Bukkit.getOnlinePlayers()) {

            Objective objective = getScoreboards().get(online.getUniqueId()).getObjective("game");
            if (objective != null) {

                objective.getScore(ChatColor.AQUA + "warvale.net").setScore(1);
            }

        }

    }

    private String convert(int n) {
        int n2 = n / 3600;
        int n3 = n - n2 * 3600;
        int n4 = n3 / 60;
        int n5 = n3 - n4 * 60;
        String string = "";
        if (n2 < 10) {
            string = string + "0";
        }
        string = string + n2 + ":";
        if (n4 < 10) {
            string = string + "0";
        }
        string = string + n4 + ":";
        if (n5 < 10) {
            string = string + "0";
        }
        string = string + n5;
        return string;
    }


}
