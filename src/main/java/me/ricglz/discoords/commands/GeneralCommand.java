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

    /**
     * Sets the environmental instance of the plugin
     * 
     * @param discoords
     */
    public void setDiscoords(Discoords discoords) {
        this.discoords = discoords;
    }

    /**
     * Function that runs the main logic of the command. If for any reason there was
     * an error due to arguments, to missing data, etc. the function should throw an
     * exception that later on will be managed respectively.
     * 
     * This function should be overriden by the decendants of the abstract class of
     * GeneralCommand
     * 
     * @param sender
     * @param command
     * @param label   If the command has an alias the label could be either the
     *                explicit name or the alias
     * @param args
     * @throws Exception
     */
    public void run(Player sender, Command command, String label, String[] args) throws Exception {
        throw new Exception("Command not yet implemented");
    }

    /**
     * Function to provide of possibilities to autocompletition when using tab
     * 
     * @param server
     * @param sender
     * @param label  If the command has an alias the label could be either the
     *               explicit name or the alias
     * @param args
     * @return
     */
    public List<String> tabComplete(Server server, Player sender, String label, String[] args) {
        if (args.length == 0) {
            return Collections.emptyList();
        }
        return getAndSanitizeOptions(server, sender, label, args);
    }

    /**
     * Returns the santized options recommended when using any of our commands.
     * 
     * @param server
     * @param sender
     * @param label
     * @param args
     * @return
     */
    private List<String> getAndSanitizeOptions(Server server, Player sender, String label, String[] args) {
        final List<String> options = getTabCompleteOptions(server, sender, label, args);
        if (options == null) {
            return Collections.emptyList();
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], options, new ArrayList<String>());
    }

    /**
     * Get the recommended options of the command without sanitizing. By default it
     * was decided that nothing was recommended
     * 
     * This is the function that should be overriden if the command can recommend
     * any options to autocomplete
     */
    protected List<String> getTabCompleteOptions(Server server, Player sender, String label, String[] args) {
        return Collections.emptyList();
    }
}
