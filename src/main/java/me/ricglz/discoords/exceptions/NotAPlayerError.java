package me.ricglz.discoords.exceptions;

public class NotAPlayerError extends Exception {
    private static final long serialVersionUID = 1L;

    public NotAPlayerError() {
        super("You must be a player!");
    }

    public NotAPlayerError(final String message) {
        super(message);
    }
}
