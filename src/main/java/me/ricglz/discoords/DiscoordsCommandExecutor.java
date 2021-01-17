package me.ricglz.discoords;

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage("[Error] The command doesn't accept as many arguments");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("[Error] You must be a player!");
            return true;
        }
        if (channel == null) {
            sender.sendMessage("[Error] Discoord is not available");
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();
        String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
        String msg = args.length == 0 ? locString : String.format("%s - %s", locString, args[0]);
        sender.sendMessage(msg);
        sendLocation(msg, player.getDisplayName());

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
