package net.warvale.conquest.commands;

import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceStartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (command.getName().equalsIgnoreCase("forcestart")) {
            if (!sender.hasPermission("warvale.forcestart")) {
                sender.sendMessage(ChatColor.RED+"No permission!");
                return true;
            }
            if (Game.get().getState().equals(GameState.STARTED)) {
                sender.sendMessage(ChatColor.RED+"Game is already started.");
                return true;
            }
            sender.sendMessage(ChatColor.AQUA+"Force starting game...");
            Bukkit.broadcastMessage(ChatColor.GOLD+"The game was force-started by "+sender.getName()+"!");
            Game.get().start();
            return true;
        }
        return false;
    }
}
