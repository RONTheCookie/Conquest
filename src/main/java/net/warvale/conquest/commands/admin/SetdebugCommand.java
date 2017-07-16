package net.warvale.conquest.commands.admin;

import net.warvale.conquest.commands.AbstractCommand;
import net.warvale.conquest.config.ConfigManager;
import net.warvale.conquest.exceptions.CommandException;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetdebugCommand extends AbstractCommand {

    public SetdebugCommand() {
        super("setdebug", "<debug>");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        try {
            boolean debug = parseBoolean(args[0]);

            Game.get().setDebug(debug);
            MessageManager.broadcast(PrefixType.MAIN, "Debug mode is now set to " + debug);

        } catch (Exception ex) {
            throw new CommandException("Invalid option");
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> toReturn = new ArrayList<>();

        if (args.length == 1) {
            toReturn.add("true");
            toReturn.add("false");
        }

        return toReturn;
    }

}
