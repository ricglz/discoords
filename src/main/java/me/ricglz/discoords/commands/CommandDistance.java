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
        getDistanceAndSendMessage(point1, point2, sender);
    }

    /**
     * Helper function to create the location based on the arguments that the
     * command received. The arguments should be in the order x,y,z to be able to
     * work
     * 
     * @param world
     * @param args  Arguments of the command (have already checked that the length
     *              is correct)
     * @param start The position in which the x value as String is
     * @return
     */
    private Location createLocation(World world, String[] args, int start) {
        double x = Double.parseDouble(args[start]);
        double y = Double.parseDouble(args[start + 1]);
        double z = Double.parseDouble(args[start + 2]);
        return new Location(world, x, y, z);
    }

}
