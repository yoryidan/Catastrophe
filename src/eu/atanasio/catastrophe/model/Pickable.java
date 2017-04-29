package eu.atanasio.catastrophe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by victorperez on 14/04/17.
 */
@Data
@EqualsAndHashCode(exclude={"position"})
public class Pickable {
    private Waypoint position;
    private String name;

    public Pickable(String name) {
        this.name = name;
    }

    public boolean atDump(){
        return this.getPosition().isDump();
    }

}
