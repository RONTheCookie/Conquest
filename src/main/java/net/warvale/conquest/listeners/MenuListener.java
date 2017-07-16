package net.warvale.conquest.listeners;

import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.maps.VoteMenu;
import net.warvale.conquest.utils.mc.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;


public class MenuListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if(inv != null && e.getClickedInventory() != null) {
            for (Menu menu : ConquestCore.get().listMenu()) {
                if (menu.getInventory().equals(inv)) {
                    e.setCancelled(true);
                    if (e.getClickedInventory().equals(inv)) {
                        menu.click(player, e.getClick(), e.getSlot(), e.getCurrentItem());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (Game.get().isState(State.LOBBY) || Game.get().isState(State.COUNTDOWN)) {
            Player p = e.getPlayer();
            int slot = p.getInventory().getHeldItemSlot();
            if (slot == SessionListener.MAPSLOT) {
                VoteMenu.getMenu(p).show(p);
            } else if (slot == SessionListener.KITSLOT) {
                //Main.get().getClassMenu().show(p);

            }
        }
    }


}
