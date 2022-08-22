package model.objects;
// This class is where the different types of food are constructed and data on the different types of food is stored

import org.json.JSONObject;
import persistence.Writable;

public class Food implements Writable {
    public static final int KIBBLE_SATIETY = 3;
    public static final int KIBBLE_HEARTINESS = 0;
    public static final int KIBBLE_COST = 15;
    public static final int STEAK_SATIETY = 5;
    public static final int STEAK_HEARTINESS = 2;
    public static final int STEAK_COST = 50;
    public static final int MILKbONE_SATIETY = 1;
    public static final int MILKbONE_HEARTINESS = 3;
    public static final int MILKbONE_COST = 30;
    public static final int PEANUT_BUTTER_SATIETY = 20;
    public static final int PEANUT_BUTTER_HEARTINESS = 10;
    public static final int PEANUT_BUTTER_COST = 200;


    private final String name;
    private final int satiety;
    private final int heartiness;
    private final int cost;

    // EFFECTS: constructs Food with the given name, satiety, and cost
    public Food(String name, int satiety, int heartiness, int cost) {
        this.name = name;
        this.satiety = satiety;
        this.heartiness = heartiness;
        this.cost = cost;
    }

    // EFFECTS: returns name of Food
    public String getNameFood() {
        return this.name;
    }

    // EFFECTS: returns the satiety of Food
    public int getSatiety() {
        return this.satiety;
    }

    // EFFECTS: returns heartiness of Food
    public int getHeartiness() {
        return this.heartiness;
    }

    // EFFECTS: returns cost of Food
    public int getCost() {
        return this.cost;
    }

    // DIFFERENT FOODS:
    public static final Food kibble =
            new Food("Kibble", KIBBLE_SATIETY, KIBBLE_HEARTINESS, KIBBLE_COST);

    public static final Food steak =
            new Food("Steak", STEAK_SATIETY, STEAK_HEARTINESS, STEAK_COST);

    public static final Food milkBone =
            new Food("MilkBone", MILKbONE_SATIETY, MILKbONE_HEARTINESS, MILKbONE_COST);

    public static final Food peanutButter =
            new Food("Peanut Butter", PEANUT_BUTTER_SATIETY, PEANUT_BUTTER_HEARTINESS, PEANUT_BUTTER_COST);

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("satiety", satiety);
        json.put("heartiness", heartiness);
        json.put("cost", cost);
        return json;
    }
}
