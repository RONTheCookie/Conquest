package net.warvale.conquest.listeners;

import net.warvale.conquest.game.Core;
import net.warvale.conquest.game.CoreSet;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class WorldListener implements Listener {
    @EventHandler
    public void onCoreBreak(BlockBreakEvent event) {
        //TODO: Protect against team core griefing.

        if (event.getBlock().getType().equals(Material.EMERALD_BLOCK)) {
            CoreSet coreSet = Game.get().getCoreSet();
            Core core;
            if (coreSet.getRedCore().getLocation().equals(event.getBlock().getLocation())) {
                // red core
                core = coreSet.getRedCore();
            } else {
                // blue core
                core = coreSet.getBlueCore();
            }
            Bukkit.broadcastMessage(ChatColor.RED+event.getPlayer().getName()+ChatColor.AQUA+" damaged "+ (core.getTeam().equals(Team.RED) ? ChatColor.RED+"Red" : ChatColor.BLUE+"Blue")+ChatColor.AQUA+"'s core! ("
            + "("+ChatColor.RED+String.valueOf(core.getDurability())+ChatColor.AQUA+").");
            core.setDurability(core.getDurability() - 1);
        }
    }
}
