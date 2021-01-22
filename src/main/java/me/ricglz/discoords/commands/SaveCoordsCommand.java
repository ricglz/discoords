package me.ricglz.discoords.commands;

import me.ricglz.discoords.Coordinates;
import me.ricglz.discoords.exceptions.CoordinatesExistException;
import me.ricglz.discoords.exceptions.CoordinatesNotFoundException;
import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;
import me.ricglz.discoords.exceptions.InvalidWorldException;
import me.ricglz.discoords.exceptions.NotAPlayerError;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveCoordsCommand extends GeneralCommand {
    public SaveCoordsCommand() {
        super("save-coords");
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) throws Exception {
        if (!(sender instanceof Player)) {
            throw new NotAPlayerError();
        }
        if (args.length == 0) {
            throw new InvalidAmountOfArgumentsException();
        }
        final Coordinates coords = discoords.getCoordinates();
        Location loc = null;

        try {
            loc = coords.getCoordinates(args[0]);
        } catch (final CoordinatesNotFoundException | InvalidWorldException ex) {
            discoords.getLogger().info("[Save] Coords were not found or invalid world");
        }

        if (loc != null) {
            throw new CoordinatesExistException();
        }

        coords.setCoordinates(args[0], ((Player) sender).getLocation());
    }
}
