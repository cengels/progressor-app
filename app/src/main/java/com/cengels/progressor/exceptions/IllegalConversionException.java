package com.cengels.progressor.exceptions;

public class IllegalConversionException extends RuntimeException {
    public IllegalConversionException(final String from, final String to) {
        super("Illegal conversion from " + from + " to " + to);
    }

}
