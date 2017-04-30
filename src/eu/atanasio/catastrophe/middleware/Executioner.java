package eu.atanasio.catastrophe.middleware;

import eu.atanasio.catastrophe.model.Cleaner;
import eu.atanasio.catastrophe.Exceptions.CleanerOperationException;
import eu.atanasio.catastrophe.Exceptions.CommandExecutionException;
import eu.atanasio.catastrophe.model.Drone;
import eu.atanasio.catastrophe.Exceptions.DroneOperationException;
import eu.atanasio.catastrophe.Exceptions.NotFoundInMapException;
import eu.atanasio.catastrophe.model.Pickable;
import eu.atanasio.catastrophe.singletons.Configuration;
import eu.atanasio.catastrophe.singletons.PointMap;
import eu.atanasio.catastrophe.model.Rubble;
import eu.atanasio.catastrophe.model.Waypoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by victorperez on 18/04/17.
 */
public class Executioner {
    static Configuration conf = Configuration.getInstance();

    public void runPlanner(){
        TroubleMaker.make();

        try {
            Process p=Runtime.getRuntime().exec("./run-map.sh -d " + conf.getProperty("output") + "/domain.pddl -p " + conf.getProperty("output") + "/p01.pddl -o output -A cmap -s mingoals -P private -M nil  -a lama-unit-cost -r lama-unit-cost -g subsets -y nil -Y lama-second -t 1800 -C t",
                    null, new File(conf.getProperty("cmap")));
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        runPlanner();

        try {
            BufferedReader br = new BufferedReader(new FileReader(conf.getProperty("cmap") + "/output"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                if(!line.startsWith(";;")){
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    if (executeCommand(line.split(":? +\\(?|\\)"))>0){
                        br.close();
                        runPlanner();
                        br = new BufferedReader(new FileReader(conf.getProperty("cmap") + "/output"));
                    }
                }
                line = br.readLine();
            }
            try {
                PrintWriter printer = new PrintWriter(conf.getProperty("output") + "/simulation_results");
                printer.print(sb.toString());
                printer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CommandExecutionException e) {
            e.printStackTrace();
        }
    }

    public int executeCommand(String[] command) throws CommandExecutionException {
        PointMap map = PointMap.getInstance();
        switch (command[3].split("\\d")[0]) {//agent

            case "DRONE":
                try {
                    Drone cls;
                    String name;
                    name = command[3].toLowerCase();
                    try {
                        cls = map.getParticipant(Drone.class, new Drone(name));
                    } catch (NotFoundInMapException e) {
                        throw new CommandExecutionException("Could not find drone: " + name);
                    }
                    Waypoint y;
                    Rubble r;
                    switch (command[2]){
                        case "FLY":
                            name = command[5].toLowerCase();
                            try {
                                y = map.getWaypoint(name);
                            } catch (NotFoundInMapException e) {
                                throw new CommandExecutionException("Could not find waypoint: " + name);
                            }
                            cls.fly(y);
                            return 0;
                        case "ASSESS":
                            name = command[4].toLowerCase();
                            try {
                                r = map.getParticipant(Rubble.class, new Rubble(name));
                            } catch (NotFoundInMapException e) {
                                throw new CommandExecutionException("Could not find rubble: " + name);
                            }
                            cls.assess(r);
                            return 1;
                    }
                } catch (DroneOperationException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return -1;
            case "CLEANER":
                try {
                    Cleaner cls;
                    String name = command[3].toLowerCase();
                    try {
                        cls = map.getParticipant(Cleaner.class, new Cleaner(name));
                    } catch (NotFoundInMapException e) {
                        throw new CommandExecutionException("Could not find cleaner: " + name);
                    }
                    Waypoint y;
                    Pickable r;
                    switch (command[2]){
                        case "WALK":
                            name = command[5].toLowerCase();
                            try {
                                y = map.getWaypoint(name);
                            } catch (NotFoundInMapException e) {
                                throw new CommandExecutionException("Could not find waypoint: " + name);
                            }
                            cls.walk(y);
                            return 0;
                        case "PICKUP_RUBBLE":
                            name = command[4].toLowerCase();
                            try {
                                r = map.getParticipant(Rubble.class, new Rubble(name));
                            } catch (NotFoundInMapException e) {
                                throw new CommandExecutionException("Could not find rubble: " + name);
                            }
                            cls.pickUp(r);
                            return 0;
                        case "PICKUP_MACHINE":
                            name = command[4].toLowerCase();
                            switch (command[4].split("\\d")[0]){
                                case "DRONE":
                                    try {
                                        r = map.getParticipant(Drone.class, new Drone(name));
                                    } catch (NotFoundInMapException e) {
                                        throw new CommandExecutionException("Could not find drone: " + name);
                                    }
                                    break;
                                case "CLEANER":
                                    try {
                                        r = map.getParticipant(Cleaner.class, new Cleaner(name));
                                    } catch (NotFoundInMapException e) {
                                        throw new CommandExecutionException("Could not find cleaner: " + name);
                                    }
                                    break;
                                default:
                                    return -1;
                            }
                            cls.pickUp(r);
                            return 0;
                        case "DROP":
                            cls.dump();
                            return 0;
                        case "PLACE_AT":
                            cls.dump();
                            return 0;
                    }
                } catch (CleanerOperationException e) {
                    e.printStackTrace();
                }
                return -1;
        }
        return -1;
    }
}
