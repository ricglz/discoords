package me.ricglz.discoords.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.ricglz.discoords.exceptions.DiscordNotAvailableException;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandDiscoords extends GeneralCommand {
    TextChannel channel;

    @Override
    public void run(Player sender, Command command, String label, String[] args) throws DiscordNotAvailableException {
        channel = discoords.getTextChannel();
        Location loc = sender.getLocation();
        String coordinateValues = String.join(" ", args);
        String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
        String msg = coordinateValues.isEmpty() ? locString : String.format("%s - %s", locString, coordinateValues);
        sender.sendMessage(msg);
        sendLocation(msg, ChatColor.stripColor(sender.getDisplayName()));
    }

    /**
     * Sends a mesage about the current location and the player name to the
     * designated discord channel
     * 
     * @param sentMessage Message that was sent to the player in Minecraft
     * @param playerName  Name of the player who executed the command
     */
    private void sendLocation(String sentMessage, String playerName) {
        String msg = String.format("%s - by %s", sentMessage, playerName);
        channel.sendMessage(msg).queue();
    }
}
