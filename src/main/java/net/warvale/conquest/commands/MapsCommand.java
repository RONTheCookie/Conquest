package net.warvale.conquest.commands;

import net.warvale.conquest.maps.GameMap;
import net.warvale.conquest.maps.MapManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class MapsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (command.getName().equalsIgnoreCase("maps")) {
            sender.sendMessage(ChatColor.AQUA+"Showing "+String.valueOf(MapManager.get().getRotation().size()+" maps currently in Rotation: "));
            ArrayList<GameMap> rotation = MapManager.get().getRotation();
            int i = 1;
            for (GameMap gameMap : rotation) {
                sender.sendMessage(ChatColor.AQUA+"#"+String.valueOf(i)+": "+gameMap.getName()+" by "+gameMap.getAuthor()+"."
                + ((MapManager.get().getCurrentRotationPosition()== (i-1)) ? ChatColor.GOLD+" (Current)" : ""));
                i++;
            }
            return true;
        }
        return false;
    }
}
