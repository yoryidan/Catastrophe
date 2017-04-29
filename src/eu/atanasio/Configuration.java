package eu.atanasio;

import lombok.Data;

import java.util.Properties;

/**
 * Created by victorperez on 29/04/17.
 */
@Data
public class Configuration {
    private static Configuration instance = new Configuration();
    private Properties properties;

    private Configuration(){
        properties = new Properties();
    }

    public static Configuration getInstance(){
        if (instance == null){
            instance = new Configuration();
        }
        return instance;
    }
}
