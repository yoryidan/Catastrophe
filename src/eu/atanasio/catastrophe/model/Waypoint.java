package eu.atanasio.catastrophe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by victorperez on 14/04/17.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Waypoint {

    @JsonProperty
    private String id;
    private Set<Waypoint> connectedWaypoints;
    private Set<Waypoint> connectedWaypointsByLand;
    private Set<Waypoint> connectedWaypointsByFlight;
    private boolean dump;

    public Waypoint(Set<Waypoint> connectedWaypointsByLand, Set<Waypoint> connectedWaypointsByFlight) {
        this.connectedWaypointsByLand = connectedWaypointsByLand;
        this.connectedWaypointsByFlight = connectedWaypointsByFlight;
        this.connectedWaypoints.addAll(connectedWaypointsByLand);
        this.connectedWaypoints.addAll(connectedWaypointsByFlight);
    }

    public Waypoint(String id) {
        this.id = id;
        connectedWaypoints = new HashSet<>();
        dump = false;
    }

    public void fixWaypoints(){
        this.connectedWaypoints.addAll(connectedWaypointsByLand);
        this.connectedWaypoints.addAll(connectedWaypointsByFlight);
    }

    public Set<Waypoint> getConnectedWaypoints() {
        return connectedWaypoints;
    }

    public void setConnectedWaypoints(Set<Waypoint> connectedWaypoints) {
        this.connectedWaypoints = connectedWaypoints;
    }

    public Set<Waypoint> getConnectedWaypointsByLand() {
        return connectedWaypointsByLand;
    }

    public void setConnectedWaypointsByLand(Set<Waypoint> connectedWaypointsByLand) {
        this.connectedWaypointsByLand = connectedWaypointsByLand;
    }

    public Set<Waypoint> getConnectedWaypointsByFlight() {
        return connectedWaypointsByFlight;
    }

    public void setConnectedWaypointsByFlight(Set<Waypoint> connectedWaypointsByFlight) {
        this.connectedWaypointsByFlight = connectedWaypointsByFlight;
    }

    public boolean isDump() {
        return dump;
    }

    public void setDump(boolean dump) {
        this.dump = dump;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Waypoint waypoint = (Waypoint) o;

        return id != null ? id.equals(waypoint.id) : waypoint.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return id;
    }
}
