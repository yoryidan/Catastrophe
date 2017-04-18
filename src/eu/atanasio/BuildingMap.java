package eu.atanasio;

import lombok.Data;

/**
 * Created by victorperez on 14/04/17.
 */
@Data
public class BuildingMap {
    Waypoint[] waypoints;

    public BuildingMap(Waypoint[] Waypoints) {
        this.waypoints = Waypoints;
    }
}
