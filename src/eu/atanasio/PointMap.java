package eu.atanasio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.File;
import java.io.IOException;

/**
 * Created by victorperez on 14/04/17.
 */
@Data
@JsonDeserialize(using = PointMapDeserializer.class)
public class PointMap {
    private static Configuration conf = Configuration.getInstance();
    private static PointMap instance = null;
    private Waypoint[] waypoints;
    private Pickable[] participants;
    private Pickable[] finalState;

    private PointMap() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            PointMap aux = mapper.readValue(new File(conf.getProperties().getProperty("map")), PointMap.class);
            this.setWaypoints(aux.getWaypoints());
            this.setParticipants(aux.getParticipants());
            this.setFinalState(aux.getFinalState());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public PointMap(Waypoint[] waypoints, Pickable[] participants, Pickable[] finalState) {
        this.waypoints = waypoints;
        this.participants = participants;
        this.finalState = finalState;
    }

    public void save(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(conf.getProperties().getProperty("map")), instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PointMap getInstance(){
        if (instance == null){
            instance = new PointMap();
        }
        return instance;
    }


}
