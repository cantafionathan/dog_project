package model.objects;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// name, hunger, health, and inventory of dog are stored in this class as well as methods that act on these fields
public class Dog implements Writable {
    public static final int MAX_HEALTH = 10;
    public static final int MAX_HUNGER = 20;
    public static final int DOG_SHOW_HEALTH_DEDUCTION = 3;
    public static final int PLAY_HEALTH_INCREASE = 2;
    public static final int PLAY_HUNGER_DECREASE = 2;

    private String name;
    private int hunger;
    private int health;
    private Inventory inventory;

    // EFFECTS : constructs a dog with the given name and also maximum health and hunger and an inventory with balance 100
    //           and no food
    public Dog(String name) {
        this.name = name;
        this.health = MAX_HEALTH;
        this.hunger = MAX_HUNGER;
        this.inventory = new Inventory();
    }

    // MODIFIES: this
    // EFFECTS: sets field dog to dog
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the name of the dog
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the health of the dog
    public int getHealth() {
        return this.health;
    }

    // EFFECTS: returns the hunger of the dog
    public int getHunger() {
        return this.hunger;
    }

    // EFFECTS: returns the inventory of the dog
    public Inventory getInventory() {
        return this.inventory;
    }

    // MODIFIES: this
    // EFFECTS: sets field health to health
    public void setHealth(int health) {
        this.health = health;
    }

    // MODIFIES: this
    // EFFECTS: sets field hunger to hunger
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    // MODIFIES: this
    // EFFECTS: sets field inventory to inventory
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // MODIFIES: this
    // EFFECTS: increases the dog's health by PLAY_HEALTH_INCREASE, but never goes over the max
    //          decreases the dog's hunger by PLAY_HUNGER_DECREASE, but never goes below 0
    //          returns false if the dog doesn't have enough hunger to play, true if the dog does have enough hunger
    //          to play
    public boolean playWithDog() {
        EventLog.getInstance().logEvent(new Event("Played with " + name));
        this.health = Math.min(this.health + PLAY_HEALTH_INCREASE, MAX_HEALTH);


        if (this.hunger - PLAY_HUNGER_DECREASE < 0) {
            this.hunger = 0;
            return false;
        } else if (this.hunger - PLAY_HUNGER_DECREASE == 0) {
            this.hunger = 0;
            return true;
        } else {
            this.hunger = this.hunger - PLAY_HUNGER_DECREASE;
            return true;
        }
    }

    // MODIFIES: this and payInventory
    // EFFECTS: decreases the dog's health by DOG_SHOW_HEALTH_DEDUCTION, increases inventory's balance by PAY_AMOUNT
    //          returns false if the dog's health reaches zero, true if the dog is still alive
    public boolean takeToDogShow() {
        EventLog.getInstance().logEvent(new Event("Took " + name + " to the Dog Show"));
        if (this.health - DOG_SHOW_HEALTH_DEDUCTION <= 0) {
            this.health = 0;
            return false;
        } else {
            this.health = this.health - DOG_SHOW_HEALTH_DEDUCTION;
            inventory.payInventory();
            return true;
        }

    }


    // MODIFIES: this and removeFood
    // EFFECTS: increase the dog's hunger and health by the appropriate amount depending on the food,
    //          removes the Food used from inventory
    //          returns false if you don't have the food, true if you do have the food in your inventory
    public boolean feedDog(Food food) {
        if (this.inventory.containsFood(food)) {
            EventLog.getInstance().logEvent(new Event("Fed " + name + " " + food.getNameFood()));
            this.health = Math.min(this.health + food.getHeartiness(), MAX_HEALTH);

            this.hunger = Math.min(this.hunger + food.getSatiety(), MAX_HUNGER);

            inventory.removeFood(food);

            return true;
        } else {
            EventLog.getInstance().logEvent(new Event("Tried to feed " + name + " " + food.getNameFood()
                    + " but didn't have any."));
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("hunger", hunger);
        json.put("health", health);
        json.put("inventory", inventory.toJson());
        return json;
    }


}