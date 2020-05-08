package env;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {

    Dotenv env;

    public Env() {
        this.env = Dotenv.load();
    }

    public String get(String envName) {
        return this.env.get(envName);
    }
}