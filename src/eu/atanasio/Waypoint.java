package eu.atanasio;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by victorperez on 14/04/17.
 */
public class Waypoint {
    private String name;
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

    public Waypoint(String name) {
        this.name = name;
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

        return name != null ? name.equals(waypoint.name) : waypoint.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
