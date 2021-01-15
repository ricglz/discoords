package me.ricglz.discoords;

import java.util.List;

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

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        String token = (String) getConfig().get("token");
        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException e) {
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
                sender.sendMessage("It was not possible to login with Discord");
                return true;
            }
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                String locString = loc.toString();
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
        List<TextChannel> channels = jda.getTextChannelsByName("general", true);
        TextChannel channel = channels.get(0);
        String msg = String.format("%s - by %s", sentMessage, playerName);
        channel.sendMessage(msg).queue();
    }
}
