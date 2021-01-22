package me.ricglz.discoords.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ricglz.discoords.exceptions.DiscordNotAvailableException;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscoordsCommand extends GeneralCommand {
    TextChannel channel;

    public DiscoordsCommand() {
        super("discoords");
    }

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args)
            throws DiscordNotAvailableException {
        if (channel == null) {
            throw new DiscordNotAvailableException();
        }
        Player player = (Player) sender;
        Location loc = player.getLocation();
        String coordinateValues = String.join(" ", args);
        String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
        String msg = coordinateValues.isEmpty() ? locString : String.format("%s - %s", locString, coordinateValues);
        sender.sendMessage(msg);
        sendLocation(msg, ChatColor.stripColor(player.getDisplayName()));
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
