package me.ricglz.discoords;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

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
        this.getCommand("discoords").setExecutor(new DiscoordsCommandExecutor(channel));
    }
}
