package com.rougeux.projet.auction.mapper;

import org.springframework.http.HttpStatus;

public final class HttpMapper {

    public static HttpStatus toHttpStatus(String code) {
        if (code.startsWith("7")) {
            return HttpStatus.BAD_REQUEST;
        }

        return switch (code) {
            case "400" -> HttpStatus.BAD_REQUEST;
            case "401" -> HttpStatus.UNAUTHORIZED;
            case "403" -> HttpStatus.FORBIDDEN;
            case "404" -> HttpStatus.NOT_FOUND;
            case "500" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.OK;
        };
    }
}
