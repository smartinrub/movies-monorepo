package org.smartinrub.videoservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(YoutubeServiceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleServiceUnavailable() {
        log.error("Youtube service not responding!");
    }
}
