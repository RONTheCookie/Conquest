package net.warvale.conquest.spec;

import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SpecManager {

    private static SpecManager instance;
    private Set<UUID> spectators = new HashSet<>();
    private final Map<UUID, ItemStack[]> previousInventory = new HashMap<>();
    private final Map<UUID, ItemStack[]> previousArmor = new HashMap<>();

    public static SpecManager get() {
        if (instance == null) {
            instance = new SpecManager();
        }
        return instance;
    }

    public Set<UUID>  getSpectators() {
        return spectators;
    }

    public void enable(Player player) {

        saveItems(player);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setGameMode(GameMode.CREATIVE);


        //hide the player
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(player);
        }

        spectators.add(player.getUniqueId());

        //tell players they have vanished
        player.sendMessage(MessageManager.getPrefix(PrefixType.MAIN) + "You are now vanished!");

    }

    public void disable(Player player) {

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        loadItems(player);

        player.setFlying(false);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.SURVIVAL);

        //unhide the player
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.showPlayer(player);
        }

        spectators.remove(player.getUniqueId());

        //tell players they are no longer vanished
        player.sendMessage(MessageManager.getPrefix(PrefixType.MAIN) + "You are no longer vanished!");
    }

    public void loadItems(Player player)
    {
        if (!previousInventory.containsKey(player.getUniqueId()) || !previousArmor.containsKey(player.getUniqueId())) {
            return;
        }
        player.getInventory().setArmorContents(previousArmor.get(player.getUniqueId()));
        player.getInventory().setContents(previousInventory.get(player.getUniqueId()));
        previousArmor.remove(player.getUniqueId());
        previousInventory.remove(player.getUniqueId());
    }

    public void saveItems(Player player)
    {
        previousArmor.put(player.getUniqueId(), player.getInventory().getArmorContents());
        previousInventory.put(player.getUniqueId(), player.getInventory().getContents());
    }

    public boolean isInSpecMode(Player player)
    {
        return spectators.contains(player.getUniqueId());
    }

    public boolean isInSpecMode(UUID uuid)
    {
        return spectators.contains(uuid);
    }


}
