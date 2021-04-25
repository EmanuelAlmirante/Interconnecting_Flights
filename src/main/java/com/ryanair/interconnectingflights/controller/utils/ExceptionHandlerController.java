package com.ryanair.interconnectingflights.controller.utils;

import com.ryanair.interconnectingflights.utils.error.BusinessError;
import com.ryanair.interconnectingflights.utils.error.NoFlightsError;
import com.ryanair.interconnectingflights.utils.error.RestError;
import com.ryanair.interconnectingflights.utils.error.RoutesProviderError;
import com.ryanair.interconnectingflights.utils.error.SchedulesProviderError;
import com.ryanair.interconnectingflights.utils.error.TechnicalError;
import com.ryanair.interconnectingflights.utils.exception.BusinessException;
import com.ryanair.interconnectingflights.utils.exception.NoFlightsException;
import com.ryanair.interconnectingflights.utils.exception.RoutesProviderException;
import com.ryanair.interconnectingflights.utils.exception.SchedulesProviderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestError handleBusinessRuleValidationError(
            HttpServletRequest request, HttpServletResponse response, Exception exception) {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;

            return new BusinessError(businessException.getMessageKey(), businessException.getArguments());
        } else if (exception instanceof RoutesProviderException) {
            RoutesProviderException routesProviderException = (RoutesProviderException) exception;

            return new RoutesProviderError(routesProviderException.getMessageKey(),
                                           routesProviderException.getArguments());
        } else if (exception instanceof SchedulesProviderException) {
            SchedulesProviderException schedulesProviderException = (SchedulesProviderException) exception;

            return new SchedulesProviderError(schedulesProviderException.getMessageKey(),
                                              schedulesProviderException.getArguments());
        } else if (exception instanceof NoFlightsException) {
            NoFlightsException noFlightsException = (NoFlightsException) exception;

            return new NoFlightsError(noFlightsException.getMessageKey(), noFlightsException.getArguments());
        } else {
            return new TechnicalError(exception);
        }
    }
}
