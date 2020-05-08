package aggregator;

import java.lang.StringBuilder;
import villager.VillagerDetail;

class AggregatorQuery {
    public static final String CREATE_TABLE_VILLAGER = "CREATE TABLE IF NOT EXISTS Villager ( name varchar(15) NOT NULL, gender varchar(6) NOT NULL, species varchar(20) NOT NULL, personality varchar(40) NOT NULL, image_source varchar(255) NOT NULL, link varchar(255) NOT NULL, birthday varchar(35) NOT NULL, PRIMARY KEY (name));";
    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS User ( chat_id BIGINT NOT NULL, villagers varchar(255), PRIMARY KEY (chat_id));" ;

    public static String createDatabase(String dbName) {
        return "CREATE DATABASE IF NOT EXISTS " + dbName + ";";
    }

    public static String useDatabase(String dbName) {
        return "USE " + dbName + ";";
    }

    public static String insertVillager(VillagerDetail villager) {
        StringBuilder str = new StringBuilder();

        // Define table column
        str.append("INSERT INTO Villager (name, gender, species, personality, image_source, link, birthday) ");

        // Start mapping values here
        str.append("VALUES (");

        // Start add values here
        str.append("'" + villager.name + "',");
        str.append("'" + villager.gender + "',");
        str.append("'" + villager.species + "',");
        str.append("'" + villager.personality + "',");
        str.append("'" + villager.imageSource + "',");
        str.append("'" + villager.link + "',");
        str.append("'" + villager.birthday + "'");

        // Close values here
        str.append(") ");

        // Add on duplicate statement here
        str.append("ON DUPLICATE KEY UPDATE ");

        // Add every column (except primary key) here
        str.append("gender = VALUES(gender), ");
        str.append("species = VALUES(species), ");
        str.append("personality = VALUES(personality), ");
        str.append("image_source = VALUES(image_source), ");
        str.append("link = VALUES(link), ");
        str.append("birthday = VALUES(birthday);");

        // return the query
        return str.toString();
    }
}