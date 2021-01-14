package me.ricglz.discoords;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Discoords extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("[Discoords] - Enabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discoords")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location loc = player.getLocation();
                sender.sendMessage(loc.toString());
            } else {
                sender.sendMessage("You must be a player!");
            }
            return true;
        }
        return false;
    }
}
