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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        if (DataStore.rooms.containsKey(room.getId())) {
            return Response.status(409).entity("Room already exists").build();
        }
        DataStore.rooms.put(room.getId(), room);
        return Response.status(201).entity(room).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);
        if (room == null) {
            return Response.status(404).entity("Room not found").build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = DataStore.rooms.get(id);
        if (room == null) {
            return Response.status(404).entity("Room not found").build();
        }
        if (!room.getSensorIds().isEmpty()) {
            return Response.status(409)
                    .entity("Cannot delete room with active sensors").build();
        }
        DataStore.rooms.remove(id);
        return Response.ok("Room deleted successfully").build();
    }
}

