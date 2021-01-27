package me.ricglz.discoords.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GeneralDistanceCommand extends GeneralCommand {
    /**
     * Gets distance between 2 points using the ecludian distance function
     * 
     * @param point1
     * @param point2
     * @return
     */
    protected int getDistance(Location point1, Location point2) {
        double x = point1.getX() - point2.getX();
        double y = point1.getY() - point2.getY();
        double z = point1.getZ() - point2.getZ();

        return (int) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * In addition to calculate the distance it sends a message to the sender of the
     * function about the distance between those points
     * 
     * @param point1
     * @param point2
     * @param sender
     */
    protected void getDistanceAndSendMessage(Location point1, Location point2, Player sender) {
        int distance = getDistance(point1, point2);
        String message = String.format("The distance between the 2 points is %d units", distance);
        sender.sendMessage(message);
    }
}
