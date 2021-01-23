package me.ricglz.discoords.commands;

import me.ricglz.discoords.Coordinates;
import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CommandSearchCoords extends GeneralCommand {
    @Override
    public void run(Player sender, Command command, String label, String[] args) throws Exception {
        if (args.length == 0) {
            throw new InvalidAmountOfArgumentsException();
        }
        final Coordinates coords = discoords.getCoordinates();
        Location loc = coords.getCoordinates(args[0]);
        String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
        sender.sendMessage(String.format("%s - %s", args[0], locString));
    }
}
