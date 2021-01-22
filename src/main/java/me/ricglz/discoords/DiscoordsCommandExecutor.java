package me.ricglz.discoords;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dv8tion.jda.api.entities.TextChannel;

public class DiscoordsCommandExecutor implements CommandExecutor {
    TextChannel channel;

    DiscoordsCommandExecutor(TextChannel channel) {
        this.channel = channel;
    }

    private void sendError(CommandSender sender, String error) {
        sender.sendMessage(ChatColor.RED + String.format("[Error] %s", error));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sendError(sender, "You must be a player!");
        } else if (channel == null) {
            sendError(sender, "Discord is not available");
        } else {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            String coordinateValues = String.join(" ", args);
            String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
            String msg = coordinateValues.isEmpty() ? locString : String.format("%s - %s", locString, coordinateValues);
            sender.sendMessage(msg);
            sendLocation(msg, ChatColor.stripColor(player.getDisplayName()));
        }
        return true;
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
