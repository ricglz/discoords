package me.ricglz.discoords.commands;

import me.ricglz.discoords.Coordinates;
import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Server;
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

    @Override
    protected List<String> getTabCompleteOptions(Server server, Player sender, String label, String[] args) {
        return args.length == 1 ? new ArrayList<>(discoords.getCoordinates().getList()) : new ArrayList<String>();
    }
}
