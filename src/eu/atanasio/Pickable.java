package eu.atanasio;

import lombok.Data;

/**
 * Created by victorperez on 14/04/17.
 */
@Data
public class Pickable {
    private Waypoint position;

    public boolean atDump(){
        return this.getPosition().isDump();
    }
}
