package me.ricglz.discoords;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the Discoords plugin where the listeners are declared
 */
public final class Discoords extends JavaPlugin {
    JDA jda;
    TextChannel channel;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        String token = (String) getConfig().get("token");
        String channelID = (String) getConfig().get("channel-id");
        String welcomeMessage = (String) getConfig().get("welcome-message");
        try {
            jda = JDABuilder.createDefault(token).build().awaitReady();
            channel = jda.getTextChannelById(channelID);
            if (channel == null) {
                getLogger().warning("The channel id is incorrect");
                return;
            }
            channel.sendMessage(welcomeMessage).queue();
        } catch (LoginException | InterruptedException e) {
            getLogger().warning("There was a problem building the bot. It may be due to the token");
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discoords")) {
            if (args.length > 1) {
                sender.sendMessage("The command doesn't accept as many arguments");
                return false;
            }
            if (jda == null || channel == null) {
                sender.sendMessage("Discoord is not available");
                return true;
            }
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
                String msg = args.length == 0 ? locString : String.format("%s - %s", locString, args[0]);
                sender.sendMessage(msg);
                sendLocation(msg, player.getDisplayName());
            } else {
                sender.sendMessage("You must be a player!");
            }
            return true;
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
