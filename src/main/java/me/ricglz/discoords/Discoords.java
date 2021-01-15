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

public final class Discoords extends JavaPlugin {
    JDABuilder builder;
    JDA jda;
    TextChannel channel;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        String token = (String) getConfig().get("token");
        String channelName = (String) getConfig().get("channel");
        try {
            jda = JDABuilder.createDefault(token).build().awaitReady();
            channel = jda.getTextChannelsByName(channelName, true).get(0);
            channel.sendMessage("Server is up weebs!").queue();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discoords")) {
            if (args.length > 1) {
                sender.sendMessage("The command doesn't accept as many arguments");
                return false;
            }
            if (jda == null) {
                sender.sendMessage("Discoord is not available");
                return true;
            }
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                String locString = String.format("(%d, %d, %d)", (int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
                String msg = args.length == 0 ? locString : String.format("%s - %s", locString, args[0]);
                sender.sendMessage(msg);
                sendMessage(msg, player.getDisplayName());
            } else {
                sender.sendMessage("You must be a player!");
            }
            return true;
        }
        return true;
    }

    private void sendMessage(String sentMessage, String playerName) {
        String msg = String.format("%s - by %s", sentMessage, playerName);
        channel.sendMessage(msg).queue();
    }
}
