package com.smartcampus.resources;

import com.smartcampus.DataStore;
import com.smartcampus.model.Room;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/room")
public class RoomResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms(){
        List<Room> roomList = new ArrayList<>(DataStore.rooms.values());
        return Response.ok(roomList).build();
    }
}