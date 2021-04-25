package com.ryanair.interconnectingflights.utils.error;

import com.ryanair.interconnectingflights.utils.exception.BusinessException;

public class RoutesProviderError extends RestError {
    private String messageKey;
    private String[] arguments;

    private RoutesProviderError() {
    }

    public RoutesProviderError(String messageKey) {
        this.messageKey = messageKey;
    }

    public RoutesProviderError(String messageKey, String... arguments) {
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
