package model.objects;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
// this class is where balance and the food in the inventory is stored as well as methods to act on those fields

public class Inventory implements Writable {
    public static final int STARTING_BALANCE = 100;
    public static final int PAY_AMOUNT = 50;

    private int balance;
    private final LinkedList<Food> foods;

    // EFFECTS: constructs an inventory with balance 100 and no food
    public Inventory() {
        this.balance = STARTING_BALANCE;
        this.foods = new LinkedList<>();
    }

    // EFFECTS: returns the list of food in the inventory
    public LinkedList<Food> getFoods() {
        return this.foods;
    }

    // EFFECTS: returns the balance in the inventory
    public int getBalance() {
        return this.balance;
    }

    // MODIFIES: this
    // EFFECTS: sets balance to specified amount
    public void setBalance(int amount) {
        this.balance = amount;
    }

    // MODIFIES: this
    // EFFECTS: increases balance by PAY_AMOUNT
    public void payInventory() {
        this.balance = this.balance + PAY_AMOUNT;
    }

    // MODIFIES: this
    // EFFECTS: adds food to foods
    public void putFood(Food food) {
        foods.add(food);
    }

    // MODIFIES: this
    // EFFECTS: adds food to foods remove appropriate cost from balance return true, return false if balance too small
    public boolean buyFood(Food food) {
        EventLog.getInstance().logEvent(new Event("Bought " + food.getNameFood()));
        if (this.balance - food.getCost() >= 0) {
            foods.add(food);
            this.balance = this.balance - food.getCost();
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the first instance of the specified food item from foods
    public void removeFood(Food food) {
        for (int i = 0; i < foods.size(); i++) {
            Food current = foods.get(i);
            if (current.getNameFood().equals((food.getNameFood()))) {
                foods.remove(food);
            }
        }
    }

    // EFFECTS: counts all instances of the specified food in foods
    public int countFood(Food food) {
        int result = 0;
        for (Food current : foods) {
            if (current.getNameFood().equals(food.getNameFood())) {
                result = result + 1;
            }
        }
        return result;
    }

    // EFFECTS: produce true if foods contains the given food item by comparing strings
    public boolean containsFood(Food food) {
        return countFood(food) > 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        json.put("foods", foodsToJson());
        return json;
    }

    // EFFECTS: returns foods in this inventory as a JSON array
    private JSONArray foodsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food food : foods) {
            jsonArray.put(food.toJson());
        }

        return jsonArray;
    }
}
