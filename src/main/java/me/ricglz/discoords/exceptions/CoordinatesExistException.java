package me.ricglz.discoords.exceptions;

public class CoordinatesExistException extends Exception {
    public CoordinatesExistException() {
        super("Similar coordinates exists");
    }

    public CoordinatesExistException(final String message) {
        super(message);
    }
}
