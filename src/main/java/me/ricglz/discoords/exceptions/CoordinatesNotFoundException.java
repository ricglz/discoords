package me.ricglz.discoords.exceptions;

public class CoordinatesNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public CoordinatesNotFoundException() {
        super("Coordinates with that label were not found");
    }

    public CoordinatesNotFoundException(final String message) {
        super(message);
    }
}
