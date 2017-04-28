package eu.atanasio;

import lombok.Data;

/**
 * Created by victorperez on 14/04/17.
 */
@Data
public class PointMap {
    Waypoint[] waypoints;
    Pickable[] participants;
    Pickable[] finalState;

    public PointMap(Waypoint[] waypoints, Pickable[] participants, Pickable[] finalState) {
        this.waypoints = waypoints;
        this.participants = participants;
        this.finalState = finalState;
    }
}
