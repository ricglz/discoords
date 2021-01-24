package me.ricglz.discoords.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.ricglz.discoords.Coordinates;
import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;

public class CommandDistanceLabels extends GeneralDistanceCommand {
    @Override
    public void run(Player sender, Command command, String label, String[] args) throws Exception {
        if (args.length == 0 || args.length > 2) {
            throw new InvalidAmountOfArgumentsException();
        }

        final Coordinates coords = discoords.getCoordinates();
        Location point1 = coords.getCoordinates(args[0]);
        Location point2 = args.length == 1 ? sender.getLocation() : coords.getCoordinates(args[1]);
        getDistanceAndSendMessage(point1, point2, sender);
    }

    @Override
    protected List<String> getTabCompleteOptions(Server server, Player sender, String label, String[] args) {
        if (args.length > 2) {
            return Collections.emptyList();
        }
        Coordinates coordinates = discoords.getCoordinates();
        ArrayList<String> list = new ArrayList<>(coordinates.getList());
        if (args.length == 2) {
            list.remove(args[0]);
        }
        return list;
    }
}
