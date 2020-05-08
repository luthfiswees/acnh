package env;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {

    Dotenv dotenv;
    
    public Env() {
        this.dotenv = Dotenv.load();
    }

    public String get(String envName){
        return this.dotenv.get(envName);
    }
}