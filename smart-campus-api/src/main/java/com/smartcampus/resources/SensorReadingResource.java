package com.smartcampus.resources;

import com.smartcampus.DataStore;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class SensorReadingResource {
    private String sensorId;
    
    public SensorReadingResource (String sensorId){
        this.sensorId = sensorId;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings(){
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }
        List<SensorReading> list = DataStore.readings.get(sensorId);
        return Response.ok(list).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }
        if (sensor.getStatus().equals("MAINTENANCE")) {
            return Response.status(403)
                    .entity("Sensor is under maintenance").build();
        }
        SensorReading newReading = new SensorReading(reading.getValue());
        DataStore.readings.get(sensorId).add(newReading);
        sensor.setCurrentValue(reading.getValue());
        return Response.status(201).entity(newReading).build();
    }
    }
}