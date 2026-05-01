package com.smartcampus.resources;

import com.smartcampus.DataStore;
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/sensor")
public class SensorResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSenssors(@QueryParam("type")String type){
        List<Sensor> sensorList = new ArrayList<>(DataStore.sensors.values());
        if (type != null){
            List<Sensor>filtered = new ArrayList<>();
            for (Sensor s : sensorList){
                if (s.getType().equalsIgnoreCase(type)) {
                    filtered.add(s);
                }
            }
            return Response.ok(filtered).build();
        }
        return Response.ok(sensorList).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor){
        if (!DataStore.rooms.containsKey(sensor.getRoomId())){
            return Response.status(422)
                    .entity("Room not font for given roomId").build();
        }
        DataStore.sensors.put(sensor.getId(), sensor);
        DataStore.readings.put(sensor.getId(),new ArrayList<>());
        Room room = DataStore.rooms.get(sensor.getRoomId());
        room.getSensorIds().add(sensor.getId());
        return Response.status(201).entity(sensor).build();
    }
    
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(
            @PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
