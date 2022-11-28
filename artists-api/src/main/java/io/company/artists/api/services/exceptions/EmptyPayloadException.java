package io.company.artists.api.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmptyPayloadException extends RuntimeException {
    private String resource;
}
