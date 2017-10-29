package net.warvale.conquest.commands;

import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceEndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (command.getName().equalsIgnoreCase("forceend")) {
            if (!sender.hasPermission("warvale.forceend")) {
                sender.sendMessage(ChatColor.RED+"No permission!");
                return true;
            }
            if (Game.get().getState().equals(GameState.WAITING)) {
                sender.sendMessage(ChatColor.RED+"Game did not start yet.");
                return true;
            }
            sender.sendMessage(ChatColor.AQUA+"Force ending game...");
            Bukkit.broadcastMessage(ChatColor.GOLD+"The game was force-ended by "+sender.getName()+"!");
            Game.get().end();
            return true;
        }
        return false;
    }
}
