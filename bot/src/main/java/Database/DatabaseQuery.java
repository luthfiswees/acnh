package database;

class DatabaseQuery {

    public static String getVillagerQuery(String dbName, String name) {
        return "SELECT * from " + dbName + ".Villager WHERE name = '" + name + "';";
    }

    public static String getVillagerByZodiacQuery(String dbName, String zodiacName) {
        return "SELECT * from " + dbName + ".Villager WHERE birthday LIKE '%" + zodiacName + "%' ;";
    }

    public static String getVillagerBySpeciesQuery(String dbName, String speciesName){
        return "SELECT * from " + dbName + ".Villager WHERE species = '" + speciesName + "';";
    }

    public static String userNotExistQuery(String dbName, Long chatId){
        return "SELECT * from " + dbName + ".User WHERE chat_id = " + chatId + ";";
    }

    public static String addUserQuery(String dbName, Long chatId) {
        return "INSERT INTO " + dbName + ".User (chat_id) VALUES (" + chatId + ");";
    }

    public static String getVillagersQuery(String dbName, Long chatId) {
        return "SELECT villagers FROM " + dbName + ".User WHERE chat_id = " + chatId + ";";
    }

    public static String updateReminderQuery(String dbName, Long chatId, String villagers) {
        return "UPDATE " + dbName + ".User SET villagers = '" + villagers + "' WHERE chat_id = " + chatId + " ;";
    }
}