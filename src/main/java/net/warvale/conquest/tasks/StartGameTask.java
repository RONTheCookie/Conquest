package net.warvale.conquest.tasks;

import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.GameRunnable;
import net.warvale.conquest.game.State;
import net.warvale.conquest.game.scoreboards.GameScoreboard;
import net.warvale.conquest.game.scoreboards.LobbyScoreboard;
import net.warvale.conquest.game.teams.TeamManager;
import net.warvale.conquest.hooks.DisguiseHook;
import net.warvale.conquest.maps.ConquestMap;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import net.warvale.conquest.spec.SpecManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class StartGameTask extends BukkitRunnable {

    private static ConquestMap map = (ConquestMap) Game.get().getChosenMap();

    @Override
    public void run() {

        try {
            MessageManager.broadcast(PrefixType.MAIN, ChatColor.GRAY + "The game has begun on " + ChatColor.RED + BossbarCountdownTask.map.getName() + ChatColor.GRAY + "!");
//            BarManager.getAnnounceBar().setVisible(false);

            TeamManager.get().getPlayers().addAll(TeamManager.get().getRedTeam());
            TeamManager.get().getPlayers().addAll(TeamManager.get().getBlueTeam());

            for (Player player : TeamManager.get().getPlayers()) {
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.JUMP);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 1, true, false));
                player.setGameMode(GameMode.SURVIVAL);
            }

            //clear inventories
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                online.getInventory().clear();
            }

            for (Player player : TeamManager.get().getBlueTeam()) {
                //teleport player
                player.teleport(map.getBlueSpawn().toLocation(map.getWorld()));
            }
            for (Player player : TeamManager.get().getRedTeam()) {
                //teleport player
                player.teleport(map.getRedSpawn().toLocation(map.getWorld()));
            }

            //teleport specs to center of chosen map
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (SpecManager.get().isInSpecMode(online)) {
                    online.teleport(map.getCenter().toLocation(map.getWorld()));
                }
            }

        } catch (Exception ex) {
            ConquestCore.get().getLogger().log(Level.SEVERE, "Could not teleport players to chosen map", ex);
        }

        //configure scoreboards
        for (Player player : Bukkit.getOnlinePlayers()) {
            LobbyScoreboard.get().removeScoreboard(player);
            GameScoreboard.get().addScoreboard(player);
        }

        //undisguise after teleporting
        if (DisguiseHook.getInstance().isEnabled()) {

            DisguiseHook.getInstance().getAPI().undisguiseAll();

            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                online.sendMessage(MessageManager.getPrefix(PrefixType.MAIN) + "You are now undisguised");
            }

        }

        Game.get().setState(State.INGAME);
        new GameRunnable(this).runTaskTimer(ConquestCore.get(), 20, 20);
    }


}
