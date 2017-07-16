package net.warvale.conquest.commands.admin;

import net.warvale.conquest.commands.AbstractCommand;
import net.warvale.conquest.exceptions.CommandException;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import net.warvale.conquest.utils.world.LobbyUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetLobbyCommand extends AbstractCommand {

    public SetLobbyCommand() {
        super("setlobby", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {

        if (!(sender instanceof Player)) {
            throw new CommandException("Only players can execute this command.");
        }

        Player player = (Player) sender;

        //set the lobby
        LobbyUtils.setSpawn(player);

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
