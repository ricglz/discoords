package me.ricglz.discoords.commands;

import me.ricglz.discoords.Discoords;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public abstract class GeneralCommand {
    protected Discoords discoords;

    public void setDiscoords(Discoords discoords) {
        this.discoords = discoords;
    }

    public void run(Player sender, Command command, String label, String[] args) throws Exception {
        throw new Exception("Command not yet implemented");
    }
}
