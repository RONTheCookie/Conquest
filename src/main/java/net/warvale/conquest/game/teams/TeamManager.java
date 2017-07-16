package net.warvale.conquest.game.teams;

import net.md_5.bungee.api.ChatColor;
import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class TeamManager {

    private static TeamManager instance;

    private Set<Player> blueTeam;
    private Set<Player> redTeam;
    private Set<Player> players;

    public static TeamManager get() {
        if (instance == null) {
            instance = new TeamManager();
        }
        return instance;
    }

    public void setup() {

        blueTeam = new HashSet<>();
        redTeam = new HashSet<>();
        players = new HashSet<>();

        ConquestCore.get().getLogger().log(Level.INFO, "Teams have been setup!");
    }

    public Set<Player> getBlueTeam() {
        return blueTeam;
    }

    public Set<Player> getRedTeam() {
        return redTeam;
    }

    public boolean isOnBlueTeam(Player player) {
        return getBlueTeam().contains(player);
    }

    public boolean isOnRedTeam(Player player) {
        return getRedTeam().contains(player);
    }

    public void joinBlueTeam(Player player) {
        getBlueTeam().add(player);
        player.sendMessage(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GRAY + "You have joined the " + ChatColor.DARK_AQUA + "blue" + ChatColor.GRAY + " team");
    }

    public void joinRedTeam(Player player) {
        getRedTeam().add(player);
        player.sendMessage(MessageManager.getPrefix(PrefixType.MAIN) + ChatColor.GRAY + "You have joined the " + ChatColor.DARK_RED + "red" + ChatColor.GRAY + " team");

    }

    public Set<Player> getPlayers() {
        return players;
    }

}
