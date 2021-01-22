package me.ricglz.discoords.exceptions;

public class CoordinatesNotFoundException extends Exception {
    public CoordinatesNotFoundException() {
        super("Coordinates with that label were not found");
    }

    public CoordinatesNotFoundException(final String message) {
        super(message);
    }
}
