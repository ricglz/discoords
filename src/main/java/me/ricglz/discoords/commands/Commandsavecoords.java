package me.ricglz.discoords.commands;

import me.ricglz.discoords.Coordinates;
import me.ricglz.discoords.exceptions.CoordinatesExistException;
import me.ricglz.discoords.exceptions.CoordinatesNotFoundException;
import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;
import me.ricglz.discoords.exceptions.InvalidWorldException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CommandSaveCoords extends GeneralCommand {
    @Override
    public void run(Player sender, Command command, String label, String[] args) throws Exception {
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

        coords.setCoordinates(args[0], sender.getLocation());
        sender.sendMessage("Coordinates saved!");
    }
}
