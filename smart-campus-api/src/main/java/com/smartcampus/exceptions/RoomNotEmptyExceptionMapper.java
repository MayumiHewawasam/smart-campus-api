package com.smartcampus.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

public class RoomNotEmptyExceptionMapper
        implements ExceptionMapper<RoomNotEmptyException> {
    
    public Response toResponse(RoomNotEmptyException e){
        Map<String, String> error = new HashMap<>();
        error.put("status","409");
        error.put("error", "Conflict");
        error.put("message", e.getMessage());
        return Response.status(409)
                .type(MediaType.APPLICATION_JSON)
                .entity(error).build();
    }
}