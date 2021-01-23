package me.ricglz.discoords.exceptions;

public class DiscordNotAvailableException extends Exception {
    private static final long serialVersionUID = 1L;

    public DiscordNotAvailableException() {
        super("Discord is not available");
    }

    public DiscordNotAvailableException(final String message) {
        super(message);
    }
}
