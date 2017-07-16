package net.warvale.conquest.listeners;

import net.md_5.bungee.api.ChatColor;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.game.scoreboards.GameScoreboard;
import net.warvale.conquest.game.scoreboards.LobbyScoreboard;
import net.warvale.conquest.game.teams.TeamBalancing;
import net.warvale.conquest.game.teams.TeamManager;
import net.warvale.conquest.maps.ConquestMap;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import net.warvale.conquest.spec.SpecManager;
import net.warvale.conquest.utils.mc.items.ItemStackBuilder;
import net.warvale.conquest.utils.world.LobbyUtils;
import net.warvale.staffcore.bossbar.BarManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class SessionListener implements Listener {

    public static ItemStack[] generateSpawnInventory(int inventorysize) {
        ItemStack[] is = new ItemStack[inventorysize];
        is[MAPSLOT] = mapSelection.build();
        is[CLASS_SLOT] = kitSelector.build();
        return is;
    }

    public static int KITSLOT = 0, MAPSLOT = 2, CLASS_SLOT = 6;

    private static ItemStackBuilder mapSelection = new ItemStackBuilder(Material.PAPER).withName(ChatColor.DARK_AQUA + "Map Voting").withLore(ChatColor.GRAY + "Click to vote for a map");
    private static ItemStackBuilder kitSelector = new ItemStackBuilder(Material.NETHER_STAR).withName(ChatColor.DARK_AQUA + "Kit Selector").withLore(ChatColor.GRAY + "Click to choose a kit");

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();


        event.setJoinMessage("");
        String playerName = event.getPlayer().getName();

        if (Game.get().isState(State.LOBBY)) {
            p.getInventory().clear();
            p.sendMessage(ChatColor.GRAY + "Welcome back to " + ChatColor.DARK_RED + "Warvale!");

            p.setGameMode(GameMode.ADVENTURE);

            if (!TeamManager.get().isOnRedTeam(p) && !TeamManager.get().isOnBlueTeam(p)) {
                TeamBalancing.assignTeam(p);
            }

            LobbyScoreboard.get().addScoreboard(event.getPlayer());
            LobbyScoreboard.get().newScoreboard(event.getPlayer());

            p.teleport(LobbyUtils.getSpawn()); //having null in all three returns the lobby center
            p.getInventory().setContents(generateSpawnInventory(4 * 9));

            int minPlayers = Game.get().getMinPlayers() - Bukkit.getOnlinePlayers().size();
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);
            BarManager.broadcast(BarColor.GREEN, ChatColor.RED +
                    String.valueOf(minPlayers) + ChatColor.DARK_GREEN +
                    " more players needed to start the game!");
        }

        if (Game.get().isState(State.INGAME)) {
            //put the player into spec mod and tp them to the center of the chosen map
            SpecManager.get().enable(p);
            p.teleport(new ConquestMap(Game.get().getChosenMap().getName(), Game.get().getChosenMap().getDisplay()).getCenter().toLocation(Bukkit.getWorld(Game.get().getChosenMap().getName())));
            GameScoreboard.get().addScoreboard(p);
        }

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (SpecManager.get().isInSpecMode(online)) {
                p.hidePlayer(online);
            }
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        String playerName = event.getPlayer().getName();
        event.getPlayer().getInventory().clear();

        if (SpecManager.get().isInSpecMode(event.getPlayer())) {
            SpecManager.get().getSpectators().remove(event.getPlayer().getUniqueId());
        }

        if (Game.get().isState(State.LOBBY)) {
            LobbyScoreboard.get().removeScoreboard(event.getPlayer());

            BarManager.broadcast(BarColor.RED, ChatColor.DARK_RED + ChatColor.BOLD.toString() + "[-] " + ChatColor.RESET + playerName);
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);

            int minPlayers = Game.get().getMinPlayers() - Bukkit.getOnlinePlayers().size();

            MessageManager.broadcast(PrefixType.MAIN, ChatColor.RED +
                    String.valueOf(minPlayers) + ChatColor.DARK_GREEN +
                    " more players needed to start the game!");
        }

        if (Game.get().isState(State.INGAME)) {
            GameScoreboard.get().removeScoreboard(event.getPlayer());
        }

    }

}
