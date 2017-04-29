package eu.atanasio;

import eu.atanasio.middleware.Executioner;

public class Main {


    public static void main(String[] args) {
        Configuration conf = Configuration.getInstance();
        conf.getProperties().setProperty("map", args[0]);
        Executioner exe = new Executioner();
        exe.setUp();
        exe.init();
    }
}
