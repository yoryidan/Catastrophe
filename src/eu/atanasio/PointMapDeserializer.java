package eu.atanasio;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by victorperez on 29/04/17.
 */
public class PointMapDeserializer extends JsonDeserializer<PointMap> {

    @Override
    public PointMap deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Waypoint[] waypoints;
        Pickable[] participants;
        Pickable[] finalState;

        ArrayList<Waypoint> waypointList = new ArrayList<>();
        ArrayList<Pickable> participantList = new ArrayList<>();
        ArrayList<Pickable> finalStateList = new ArrayList<>();
        for (int i = 0; i < node.get("waypoints").size(); i++){
            Waypoint w = new Waypoint(node.get("waypoints").get(i).get("id").asText());
            w.setDump(node.get("waypoints").get(i).get("dump").asBoolean());
            waypointList.add(w);
        }
        for (int i = 0; i < node.get("waypoints").size(); i++){
            waypointList.get(i).setConnectedWaypointsByFlight(new HashSet<>());
            for (int z = 0; z < node.get("waypoints").get(i).get("connectedWaypointsByFlight").size(); z++){
                Waypoint aux = new Waypoint(node.get("waypoints").get(i).get("connectedWaypointsByFlight").get(z).asText());
                for(Waypoint w : waypointList){
                    if(aux.equals(w)){
                        waypointList.get(i).getConnectedWaypointsByFlight().add(w);
                    }
                }
            }
        }
        for (int i = 0; i < node.get("waypoints").size(); i++){
            waypointList.get(i).setConnectedWaypointsByLand(new HashSet<>());
            for (int z = 0; z < node.get("waypoints").get(i).get("connectedWaypointsByLand").size(); z++){
                Waypoint aux = new Waypoint(node.get("waypoints").get(i).get("connectedWaypointsByLand").get(z).asText());
                for(Waypoint w : waypointList){
                    if(aux.equals(w)){
                        waypointList.get(i).getConnectedWaypointsByLand().add(w);
                    }
                }
            }
            waypointList.get(i).fixWaypoints(); //This makes the array of the total automatically
        }
        for (int i = 0; i < node.get("participants").size(); i++){
            Pickable p;
            switch (node.get("participants").get(i).get("class").asText()){
                case "Drone":
                    Drone d = new Drone(node.get("participants").get(i).get("name").asText(),node.get("participants").get(i).get("deathCounter").asInt());
                    d.setBroken(node.get("participants").get(i).get("broken").asBoolean());
                    for(Waypoint w : waypointList){
                        Waypoint aux = new Waypoint(node.get("participants").get(i).get("position").asText());
                        if(aux.equals(w)){
                            d.setPosition(w);
                        }
                    }
                    p = d;
                    break;
                case "Cleaner":
                    Cleaner c = new Cleaner(node.get("participants").get(i).get("name").asText(),node.get("participants").get(i).get("deathCounter").asInt());
                    c.setBroken(node.get("participants").get(i).get("broken").asBoolean());
                    for(Waypoint w : waypointList){
                        Waypoint aux = new Waypoint(node.get("participants").get(i).get("position").asText());
                        if(aux.equals(w)){
                            c.setPosition(w);
                        }
                    }
                    p = c;
                    break;
                case "Rubble":
                    Rubble r = new Rubble(node.get("participants").get(i).get("name").asText());
                    r.setRadioactive(node.get("participants").get(i).get("radioactive").asBoolean());
                    r.setAssessed(node.get("participants").get(i).get("assessed").asBoolean());
                    for(Waypoint w : waypointList){
                        Waypoint aux = new Waypoint(node.get("participants").get(i).get("position").asText());
                        if(aux.equals(w)){
                            r.setPosition(w);
                        }
                    }
                    p = r;
                    break;
                default:
                    throw new RuntimeException("Cannot recognize some of the classes.");
            }
            participantList.add(p);
        }
        for (int i = 0; i < node.get("finalState").size(); i++){
            Pickable p;
            switch (node.get("finalState").get(i).get("class").asText()){
                case "Drone":
                    Drone d = new Drone(node.get("finalState").get(i).get("name").asText(),node.get("finalState").get(i).get("deathCounter").asInt());
                    d.setBroken(node.get("finalState").get(i).get("broken").asBoolean());
                    for(Waypoint w : waypointList){
                        Waypoint aux = new Waypoint(node.get("finalState").get(i).get("position").asText());
                        if(aux.equals(w)){
                            d.setPosition(w);
                        }
                    }
                    p = d;
                    break;
                case "Cleaner":
                    Cleaner c = new Cleaner(node.get("finalState").get(i).get("name").asText(),node.get("finalState").get(i).get("deathCounter").asInt());
                    c.setBroken(node.get("finalState").get(i).get("broken").asBoolean());
                    for(Waypoint w : waypointList){
                        Waypoint aux = new Waypoint(node.get("finalState").get(i).get("position").asText());
                        if(aux.equals(w)){
                            c.setPosition(w);
                        }
                    }
                    p = c;
                    break;
                case "Rubble":
                    Rubble r = new Rubble(node.get("finalState").get(i).get("name").asText());
                    r.setRadioactive(node.get("finalState").get(i).get("radioactive").asBoolean());
                    r.setAssessed(node.get("finalState").get(i).get("assessed").asBoolean());
                    for(Waypoint w : waypointList){
                        Waypoint aux = new Waypoint(node.get("finalState").get(i).get("position").asText());
                        if(aux.equals(w)){
                            r.setPosition(w);
                        }
                    }
                    p = r;
                    break;
                default:
                    throw new RuntimeException("Cannot recognize some of the classes.");
            }
            finalStateList.add(p);
        }

        waypoints = waypointList.toArray(new Waypoint[waypointList.size()]);
        participants = participantList.toArray(new Pickable[participantList.size()]);
        finalState = finalStateList.toArray(new Pickable[finalStateList.size()]);


        return new PointMap(waypoints,participants,finalState);
    }
}
