package eu.atanasio.catastrophe;

import eu.atanasio.catastrophe.middleware.Executioner;
import eu.atanasio.catastrophe.singletons.Configuration;

public class Main {


    public static void main(String[] args) {
        Configuration conf = Configuration.getInstance();
        conf.getProperties().setProperty("map", args[0]);
        Executioner exe = new Executioner();
        exe.init();
    }
}
