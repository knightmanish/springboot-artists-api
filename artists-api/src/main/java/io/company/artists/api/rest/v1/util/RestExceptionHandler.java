package io.company.artists.api.rest.v1.util;

import io.company.artists.api.lib.Error;
import io.company.artists.api.lib.RestError;
import io.company.artists.api.services.exceptions.DuplicateException;
import io.company.artists.api.services.exceptions.EmptyPayloadException;
import io.company.artists.api.services.exceptions.InvalidAttributeException;
import io.company.artists.api.services.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity notFound(Exception ex, WebRequest request) {
        return createAndLogException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PropertyReferenceException.class, EmptyPayloadException.class,
            DuplicateException.class, InvalidAttributeException.class})
    protected ResponseEntity propertyNotFound(Exception ex, WebRequest request) {
        return createAndLogException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleAllExceptions(Exception ex, WebRequest request) {
        return createAndLogException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity createAndLogException(Exception ex, WebRequest request, HttpStatus status) {
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();

        String methodType = httpServletRequest.getMethod();
        String uriString = httpServletRequest.getRequestURL().toString();

        String queryString = httpServletRequest.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            queryString = uriString + "?" + queryString;
        }

        RestError restError = new RestError();
        restError.setMethod(methodType);
        restError.setStatus(Integer.toUnsignedLong(status.value()));
        restError.setRequestURI(uriString);
        restError.setRequestQuery(queryString);

        Error error = new Error();
        error.setTitle(getErrorTitle(ex));
        error.setMessage(getErrorMessage(ex));
        restError.addErrorsItem(error);

        log.info(ex.getClass() + ": message=" + ex.getMessage());

        return handleExceptionInternal(ex, restError, new HttpHeaders(), status, request);
    }

    private String getErrorTitle(Exception ex) {
        String title;
        if (ex instanceof ResourceNotFoundException) {
            title = "Not found";
        } else if (ex instanceof PropertyReferenceException || ex instanceof EmptyPayloadException
                || ex instanceof DuplicateException || ex instanceof InvalidAttributeException) {
            title = "Bad Request / Validation Error";
        } else if (ex instanceof BadCredentialsException) {
            title = "Unauthorized";
        } else if (ex instanceof AsyncRequestTimeoutException) {
            title = "Server Unavailable";
        } else {
            title = "Internal Server Error";
        }
        return title;
    }

    private String getErrorMessage(Exception ex) {
        String message;
        if (ex instanceof ResourceNotFoundException) {
            message = "We could not locate the resource based on the specified URL. " +
                    "The requested resource could not be found but may be available in the future. Subsequent requests by the client are permissible.";
        } else if (ex instanceof PropertyReferenceException || ex instanceof EmptyPayloadException) {
            message = "The server cannot or will not process the request due to an apparent client error " +
                    "(e.g., malformed request syntax, size too large, invalid request message framing, or deceptive request routing).";
        } else if (ex instanceof DuplicateException) {
            message = String.format("%s %s %s", "An Artist with the username", ((DuplicateException) ex).getIdentifier(), "already exists in the system.");
        } else if (ex instanceof InvalidAttributeException) {
            message = String.format("%s %s %s %s",
                    "An Artist with the",
                    ((InvalidAttributeException) ex).getAttribute(),
                    ((InvalidAttributeException) ex).getIdentifier()
                    , "is not allowed in the system.");
        } else if (ex instanceof BadCredentialsException) {
            message = "Authentication credentials are required to the resource. All requests must be authenticated.";
        } else if (ex instanceof AsyncRequestTimeoutException) {
            message = "The server is currently unavailable (because it is overloaded or down for maintenance). Generally, this is a temporary state.";
        } else {
            message = "A generic error message, given when an unexpected condition was encountered and no more specific message is suitable.";
        }

        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            if (!message.isEmpty()) {
                message = String.format("%s %s", message, ex.getMessage());
            } else {
                message = ex.getMessage();
            }
        }
        return message;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity badRequest(ConstraintViolationException ex, WebRequest request) {

        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        String methodType = httpServletRequest.getMethod();

        String queryString = httpServletRequest.getQueryString();
        String uriString = httpServletRequest.getRequestURL().toString();

        RestError restError = new RestError();
        restError.setMethod(methodType);
        restError.setStatus(Integer.toUnsignedLong(HttpStatus.BAD_REQUEST.value()));
        restError.setRequestURI(uriString);
        restError.setRequestQuery(queryString);


        return handleExceptionInternal(ex, restError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
