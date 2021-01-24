package me.ricglz.discoords.commands;

import me.ricglz.discoords.Discoords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public abstract class GeneralCommand {
    protected Discoords discoords;

    public void setDiscoords(Discoords discoords) {
        this.discoords = discoords;
    }

    public void run(Player sender, Command command, String label, String[] args) throws Exception {
        throw new Exception("Command not yet implemented");
    }

    public List<String> tabComplete(Server server, Player sender, String label, String[] args) {
        if (args.length == 0) {
            return Collections.emptyList();
        }
        return getAndSanitizeOptions(server, sender, label, args);
    }

    private List<String> getAndSanitizeOptions(Server server, Player sender, String label, String[] args) {
        final List<String> options = getTabCompleteOptions(server, sender, label, args);
        if (options == null) {
            return Collections.emptyList();
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], options, new ArrayList<String>());
    }

    protected List<String> getTabCompleteOptions(Server server, Player sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
