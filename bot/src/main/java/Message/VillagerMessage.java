package message;

import database.Database;
import model.Villager;

import java.lang.StringBuilder;
import java.util.List;
import java.sql.SQLException;

import model.Message;

class VillagerMessage {

    private Message message;
    private Database db;

    public VillagerMessage(String message) throws SQLException {
        this.db = new Database();
        this.message = processMessage(message);
    }

    public Message getMessage() {
        return this.message;
    }

    private Message processMessage(String rawMessage){
        Message processedMessage;
        String[] chunks = rawMessage.split(" ");
        
        if (chunks.length == 2) {
            String villagerName = chunks[1];
            
            processedMessage = searchVillagerDetails(villagerName);
        } else {
            String feature = chunks[1];
            String featureIdentifier = chunks[2];

            processedMessage = searchFeature(feature, featureIdentifier);
        }

        return processedMessage;
    }

    private Message searchVillagerDetails(String villagerName) {
        StringBuilder str = new StringBuilder();
        
        String capitalizeVillagerName = capitalize(villagerName);

        try {
            Villager villager = this.db.getVillager(capitalizeVillagerName);

            // First row
            str.append(villager.name + " (");
            str.append(villager.gender + " ");
            str.append(villager.species + ")\n");

            // Second row
            str.append("Personality: ");
            str.append(villager.personality + "\n");
            
            // Third row
            str.append("Birthday: ");
            str.append(villager.birthday);

            return new Message(str.toString(), villager.imageSource);
        } catch (Exception e) {
            return new Message("There is no such villager");
        }
    }

    private Message searchFeature(String feature, String featureIdentifier) {
        Message endMessage = null;

        switch(feature) {
            case "zodiac":
                endMessage = searchByZodiac(featureIdentifier);
                break;
            case "species":
                endMessage = searchBySpecies(featureIdentifier);
                break;
            default:
                endMessage = new Message("There are no such feature");
        }

        return endMessage;
    }

    private Message searchByZodiac(String zodiacName) {
        StringBuilder str = new StringBuilder();
        str.append("Villagers\n");

        String capitalizeZodiacName = capitalize(zodiacName);
        List<Villager> villagers = this.db.getVillagerByZodiac(capitalizeZodiacName);

        int count = 0;
        for (Villager villager: villagers) {
            str.append(villager.name + " (");
            str.append(villager.gender + " ");
            str.append(villager.species + ")\n");

            count++;
            if (count >= 7) {
                str.append("...");
                break;
            }
        }

        return new Message(str.toString());
    }

    private Message searchBySpecies(String speciesName) {
        StringBuilder str = new StringBuilder();
        str.append("Villagers\n");

        String capitalizeSpeciesName = capitalize(speciesName);
        List<Villager> villagers = this.db.getVillagerBySpecies(capitalizeSpeciesName);

        int count = 0;
        for (Villager villager: villagers) {
            str.append(villager.name + " (");
            str.append(villager.gender + " ");
            str.append(villager.species + ")\n");

            count++;
            if (count >= 7) {
                str.append("...");
                break;
            }
        }

        return new Message(str.toString());
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}