package com.smartcampus.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class SensorUnavailableExceptionMapper
        implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "403");
        error.put("error", "Forbidden");
        error.put("message", e.getMessage());
        return Response.status(403)
                .type(MediaType.APPLICATION_JSON)
                .entity(error).build();
    }
}