package com.ryanair.interconnectingflights.utils.exception;

import java.util.Arrays;

public class NoFlightsException extends RuntimeException {
    private String messageKey;
    private String[] arguments;

    public NoFlightsException() {
        super("There are no flights for the route and/or dates selected.");
    }

    public NoFlightsException(String messageKey, String... arguments) {
        super(messageKey);
        this.messageKey = messageKey;
        this.arguments = arguments == null ? new String[0] : arguments;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return super.toString() + ", arguments=" + Arrays.toString(arguments);
    }
}
