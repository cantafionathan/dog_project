package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.objects.Dog;
import model.objects.Food;
import model.objects.Inventory;
import org.json.*;

// Based on JsonSerializationDemo's JsonReader class
// Repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Dog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: dog
    // EFFECTS: parses Dog from JSON object and returns it
    private Dog parseDog(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int hunger = jsonObject.getInt("hunger");
        int health = jsonObject.getInt("health");
        Inventory inventory = parseInventory(jsonObject);
        Dog dog = new Dog(name);
        dog.setName(name);
        dog.setHunger(hunger);
        dog.setHealth(health);
        dog.setInventory(inventory);
        return dog;
    }

    // MODIFIES: inventory
    // EFFECTS: parses Inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        int balance = jsonObject.getJSONObject("inventory").getInt("balance");
        Inventory inventory = new Inventory();
        inventory.setBalance(balance);
        addFoods(inventory, jsonObject);
        return inventory;
    }

    // MODIFIES: inventory
    // EFFECTS: parses foods from JSON object and adds them to inventory
    private void addFoods(Inventory inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONObject("inventory").getJSONArray("foods");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            addFood(inventory, nextFood);
        }
    }

    // MODIFIES: inventory
    // EFFECTS: parses foods from JSON object and adds it to inventory
    private void addFood(Inventory inventory, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int satiety = jsonObject.getInt("satiety");
        int heartiness = jsonObject.getInt("heartiness");
        int cost = jsonObject.getInt("cost");
        Food food = new Food(name, satiety, heartiness, cost);
        inventory.putFood(food);
    }
}
