package me.ricglz.discoords.commands;

import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SaveCoordsCommand extends GeneralCommand {
    public SaveCoordsCommand() {
        super("save-coords");
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) throws Exception {
        if (args.length == 0) {
            throw new InvalidAmountOfArgumentsException();
        }
    }
}
