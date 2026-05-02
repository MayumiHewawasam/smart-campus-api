package com.smartcampus.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper
        implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "500");
        error.put("error", "Internal Server Error");
        error.put("message", e.getMessage());
        error.put("cause", e.getClass().getName());
        return Response.status(500)
                .type(MediaType.APPLICATION_JSON)
                .entity(error).build();
    }
}