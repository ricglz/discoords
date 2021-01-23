package me.ricglz.discoords.exceptions;

public class InvalidAmountOfArgumentsException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidAmountOfArgumentsException() {
        super("It was sent an invalid amount of arguments to the command");
    }
}
