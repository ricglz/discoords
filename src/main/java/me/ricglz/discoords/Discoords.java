package me.ricglz.discoords;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.ricglz.discoords.commands.GeneralCommand;
import me.ricglz.discoords.exceptions.InvalidAmountOfArgumentsException;
import me.ricglz.discoords.exceptions.NotAPlayerError;

/**
 * Main class for the Discoords plugin where the listeners are declared
 */
public final class Discoords extends JavaPlugin {
    JDA jda;
    TextChannel channel;
    ClassLoader classLoader;
    Coordinates coordinates;

    static final String COMMAND_PATH = "me.ricglz.discoords.commands";

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        coordinates = new Coordinates(getServer(), getDataFolder());
        coordinates.reloadConfig();
        enableDiscordAPI();
    }

    private void enableDiscordAPI() {
        String token = (String) getConfig().get("token");
        String channelID = (String) getConfig().get("channel-id");
        String welcomeMessage = (String) getConfig().get("welcome-message");
        try {
            jda = JDABuilder.createDefault(token).build().awaitReady();
            channel = jda.getTextChannelById(channelID);
        } catch (LoginException | InterruptedException e) {
            getLogger().warning("There was a problem building the bot. It may be due to the token");
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        if (channel == null) {
            getLogger().warning("The channel id is incorrect");
            return;
        }
        channel.sendMessage(welcomeMessage).queue();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private void sendError(CommandSender sender, String error) {
        sender.sendMessage(ChatColor.RED + String.format("[Error] %s", error));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GeneralCommand cmd = null;
        try {
            cmd = (GeneralCommand) classLoader.loadClass(COMMAND_PATH + command.getName()).newInstance();
        } catch (final Exception e) {
            sendError(sender, "Command was not loaded");
            getLogger().severe("Command was not loaded");
        }
        if (cmd != null) {
            try {
                if (sender instanceof Player) {
                    cmd.run(((Player) sender), command, label, args);
                } else {
                    throw new NotAPlayerError();
                }
            } catch (final InvalidAmountOfArgumentsException ex) {
                sender.sendMessage(command.getDescription());
                sender.sendMessage(command.getUsage().replace("<command>", label));
                if (!ex.getMessage().isEmpty()) {
                    sender.sendMessage(ex.getMessage());
                }
            } catch (final Exception ex) {
                sendError(sender, ex.getMessage());
            }
        }
        return true;
    }
}
