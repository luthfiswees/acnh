package database;

public class DatabaseQuery {

    public static String getVillagersNameQuery(String dbName,String dateString) {
        return "SELECT name FROM " + dbName + ".Villager WHERE birthday LIKE '%" + dateString + "%' ;";
    }

    public static String getMessageDetailQuery(String dbName, String name) {
        return "SELECT chat_id FROM " + dbName + ".User WHERE villagers LIKE '%" + name + "%' ;";
    }
}