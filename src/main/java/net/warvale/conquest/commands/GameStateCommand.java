package net.warvale.conquest.commands;

import net.warvale.conquest.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameStateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (command.getName().equalsIgnoreCase("gamestate")) {
            sender.sendMessage(ChatColor.AQUA+"Game state: "+Game.get().getState().name());
            return true;
        }
        return false;
    }
}
