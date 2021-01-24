package me.ricglz.discoords.commands;

import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CommandDistance extends GeneralDistanceCommand {
    @Override
    public void run(Player sender, Command command, String label, String[] args)
            throws InvalidAmountOfArgumentsException {
        if (args.length == 0 || args.length % 3 != 0 || args.length > 6) {
            throw new InvalidAmountOfArgumentsException();
        }

        World world = sender.getWorld();
        Location point1 = createLocation(world, args, 0);
        Location point2 = args.length == 3 ? sender.getLocation() : createLocation(world, args, 3);
        int distance = getDistance(point1, point2);
        String message = String.format("The distance between the 2 points is %d units", distance);
        sender.sendMessage(message);
    }

    private Location createLocation(World world, String[] args, int start) {
        double x = Double.parseDouble(args[start]);
        double y = Double.parseDouble(args[start + 1]);
        double z = Double.parseDouble(args[start + 2]);
        return new Location(world, x, y, z);
    }

}
