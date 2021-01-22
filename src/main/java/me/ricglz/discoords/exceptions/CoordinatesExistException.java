package me.ricglz.discoords.exceptions;

public class CoordinatesExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public CoordinatesExistException() {
        super("Similar coordinates exists");
    }

    public CoordinatesExistException(final String message) {
        super(message);
    }
}
