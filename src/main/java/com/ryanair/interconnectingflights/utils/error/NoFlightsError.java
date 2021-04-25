package com.ryanair.interconnectingflights.utils.error;

import com.ryanair.interconnectingflights.utils.exception.BusinessException;

public class NoFlightsError extends RestError {
    private String messageKey;
    private String[] arguments;

    private NoFlightsError() {
    }

    public NoFlightsError(String messageKey) {
        this.messageKey = messageKey;
    }

    public NoFlightsError(String messageKey, String... arguments) {
        this.messageKey = messageKey;
        this.arguments = arguments;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getArguments() {
        return arguments;
    }

    @Override
    public BusinessException getException() {
        return new BusinessException(messageKey, arguments);
    }
}
