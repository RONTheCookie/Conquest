package net.warvale.conquest;

import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.GameState;
import net.warvale.conquest.listeners.SessionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Conquest extends JavaPlugin {
    public void onEnable(){

        saveDefaultConfig();


        new Game().setState(GameState.WAITING); // Make a instance of game and set it's state.

        //Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new SessionListener(), this);
    }
    public void onDisable(){

    }
}
