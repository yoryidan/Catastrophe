package eu.atanasio;

import eu.atanasio.middleware.Executioner;

public class Main {


    public static void main(String[] args) {
        Executioner exe = new Executioner();
        exe.setUp();
        exe.init();
    }



    public int executeCommand(String[] command){
        switch (command[3].split("\\d")[0]) {//agent

            case "DRONE":
                try {
                    Drone cls = (Drone) Drone.class.getDeclaredField("drone"+command[3].split("[a-zA-Z]+")[1]).get(this);
                    switch (command[1]){
                        case "FLY":
                            //Waypoint x = Waypoint.class.getDeclaredField("r"+command[4].split("[a-zA-Z]")[0]).get(this);
                            Waypoint y = (Waypoint) Waypoint.class.getDeclaredField("r"+command[5].split("[a-zA-Z]")[1]).get(this);
                            cls.fly(y);
                            return 0;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (DroneOperationException e) {
                    e.printStackTrace();
                }

        }
        return -1;
    }
}
