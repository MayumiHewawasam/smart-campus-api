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
}
