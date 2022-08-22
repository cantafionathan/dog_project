package ui.old;

import model.objects.Dog;
import model.objects.Food;
import model.objects.Inventory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import static model.objects.Food.*;
// Show Dog application (based on TellerApp class from AccountNotRobust)
// Repository: https://github.students.cs.ubc.ca/CPSC210/TellerApp

public class DogApp {
    private Dog dog;
    private Scanner input;
    private String name;
    private int balance;
    private boolean dead;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/dog.json";


    // EFFECTS: runs the Show Dog application
    public DogApp() {
        startUp();
    }

    // MODIFIES: this
    // EFFECTS: asks if the user wants to start a new game or wants to continue where they left off
    private void startUp() {
        init();
        String command;
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\tWould you like to start a new game or continue where you left off?");
            System.out.println("\tn <- new game");
            System.out.println("\tl <- continue old game");
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("n")) {
                nameDog();
            } else if (command.equals("l")) {
                loadDog();
                runDog();
                keepGoing = false;
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: names the dog
    private void nameDog() {
        System.out.println("Welcome to Show Dog: The Game!");
        System.out.println("What would you like to name your dog?");
        boolean keepGoing = true;
        while (keepGoing) {
            name = input.nextLine();
            if (name.equals("")) {
                System.out.println("Choose a name for your dog!");
            } else {
                dog = new Dog(name);
                keepGoing = false;
            }
        }
        runDog();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs
    private void runDog() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            if (dead) {
                System.out.println("\nYour dog has died...");
                break;
            } else {
                displayMenu();
                command = input.next();
                command = command.toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                    break;
                } else {
                    processCommand(command);
                }
            }
        }
        System.out.println("\nWoof woof! Come back again soon!");
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: initializes dog and inventory
    public void init() {
        dead = false;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        int health = dog.getHealth();
        int hunger = dog.getHunger();
        Inventory inventory = dog.getInventory();
        balance = inventory.getBalance();
        System.out.println("\nYour dog, " + name + ", has " + health + " health and " + hunger + " hunger. You have "
                + balance + " dollars.");
        System.out.println("\nSelect from:");
        System.out.println("\tb <- Buy Food");
        System.out.println("\tp <- Play");
        System.out.println("\tf <- Feed");
        System.out.println("\tg <- Go to Dog Show");
        System.out.println("\ts <- save data");
        System.out.println("\tq <- Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("b")) {
            doBuyFood();
        } else if (command.equals("p")) {
            doPlay();
        } else if (command.equals("f")) {
            doFeed();
        } else if (command.equals("g")) {
            doDogShow();
        } else if (command.equals("s")) {
            saveDog();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input for buying food
    private void doBuyFood() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayFoodMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepGoing = false;
            } else {
                processFood(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void doFeed() {
        boolean keepGoing = true;
        String command;
        Inventory inventory = dog.getInventory();
        LinkedList<Food> foods = inventory.getFoods();

        if (foods.size() == 0) {
            System.out.println("\nYou don't have any food...");
        } else {
            while (keepGoing) {
                displayFeedMenu();
                command = input.next();
                command = command.toLowerCase();

                if (command.equals("b")) {
                    keepGoing = false;
                } else {
                    processFeed(command);
                }
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayFeedMenu() {
        Inventory inventory = dog.getInventory();
        int kibbleAmount = inventory.countFood(Food.kibble);
        int steakAmount = inventory.countFood(Food.steak);
        int milkBoneAmount = inventory.countFood(Food.milkBone);
        int peanutButterAmount = inventory.countFood(Food.peanutButter);
        System.out.println("\nWhat would you like to feed " + name + "?");
        System.out.println("\nSelect from:");
        System.out.println("\tk <- Kibble x " + kibbleAmount);
        System.out.println("\ts <- Steak x " + steakAmount);
        System.out.println("\tm <- Milk Bone x " + milkBoneAmount);
        System.out.println("\tp <- Peanut Butter x " + peanutButterAmount);
        System.out.println("\tb <- Go Back");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processFeed(String command) {
        if (command.equals("s")) {
            processFeedSteak();
        } else if (command.equals("m")) {
            processFeedMilkBone();
        } else if (command.equals("k")) {
            processFeedKibble();
        } else if (command.equals("p")) {
            processFeedPeanutButter();
        } else {
            System.out.println("\nSelection not valid...");
        }
    }

    // MODIFIES: this and feedDog
    // EFFECTS : feeds dog a steak
    private void processFeedSteak() {
        Food steak = Food.steak;
        if (dog.feedDog(steak)) {
            System.out.println("\nYou fed " + name + " a steak!");
        } else {
            System.out.println("\nYou do not have a steak...");
        }
    }

    // MODIFIES: this and feedDog
    // EFFECTS : feeds dog a milk bone
    private void processFeedMilkBone() {
        Food milkBone = Food.milkBone;
        if (dog.feedDog(milkBone)) {
            System.out.println("\nYou fed " + name + " a Milk Bone!");
        } else {
            System.out.println("\nYou do not have a Milk Bone...");
        }
    }

    // MODIFIES: this and feedDog
    // EFFECTS : feeds dog kibble
    private void processFeedKibble() {
        Food kibble = Food.kibble;
        if (dog.feedDog(kibble)) {
            System.out.println("\nYou fed " + name + " kibble!");
        } else {
            System.out.println("\nYou do not have kibble...");
        }
    }

    // MODIFIES: this and feedDog
    // EFFECTS : feeds dog peanut butter
    private void processFeedPeanutButter() {
        Food peanutButter = Food.peanutButter;
        if (dog.feedDog(peanutButter)) {
            System.out.println("\nYou fed " + name + " peanut butter!");
        } else {
            System.out.println("\nYou do not have peanut butter...");
        }
    }

    // MODIFIES: this and playWithDog
    // EFFECTS: plays with dog
    private void doPlay() {
        if (dog.playWithDog()) {
            if (dog.getHealth() == Dog.MAX_HEALTH) {
                System.out.println("\nYou played with " + name + "! Since " + name + " already has full health "
                        + name + " can not gain any more! However, " + name + " has lost " + Dog.PLAY_HUNGER_DECREASE
                        + " hunger.");
            } else {
                System.out.println("\nYou played with " + name + " and he gained " + Dog.PLAY_HEALTH_INCREASE
                        + " health and lost " + Dog.PLAY_HUNGER_DECREASE + " hunger.");
            }
        } else {
            System.out.println("\nYour dog does have enough hunger to play with " + name + "...");
        }
    }


    // MODIFIES: this and takeToDogShow
    // EFFECTS: takes dog to dog show
    private void doDogShow() {
        if (dog.takeToDogShow()) {
            System.out.println("\nYou took " + name + " to the dog show! " + name + " has lost "
                    + Dog.DOG_SHOW_HEALTH_DEDUCTION + " health and you have earned " + Inventory.PAY_AMOUNT
                    + " dollars.");
        } else {
            dead = true;
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayFoodMenu() {
        System.out.println("\nWhat food would you like to buy?");
        System.out.println("\nSelect from:");
        System.out.println("\tk <- Kibble $" + KIBBLE_COST);
        System.out.println("\ts <- Steak $" + STEAK_COST);
        System.out.println("\tm <- Milk Bone $" + MILKbONE_COST);
        System.out.println("\tp <- Peanut Butter $" + PEANUT_BUTTER_COST);
        System.out.println("\tb <- Go Back");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processFood(String command) {
        if (command.equals("s")) {
            processBuySteak();
        } else if (command.equals("m")) {
            processBuyMilkBone();
        } else if (command.equals("k")) {
            processBuyKibble();
        } else if (command.equals("p")) {
            processBuyPeanutButter();
        } else {
            System.out.println("\nSelection not valid...");
        }
    }

    // MODIFIES: this and buyFood
    // EFFECTS: buys a steak
    private void processBuySteak() {
        Inventory inventory = dog.getInventory();
        Food steak = Food.steak;
        if (inventory.buyFood(steak)) {
            balance = inventory.getBalance();
            System.out.println("\nYou bought a steak! Your new balance is " + balance + ".");
        } else {
            System.out.println("\nYou do not have enough money to buy a steak... "
                    + "Your balance is " + balance + ".");
        }
    }

    // MODIFIES: this and buyFood
    // EFFECTS: buys a Milk Bone
    private void processBuyMilkBone() {
        Inventory inventory = dog.getInventory();
        Food milkBone = Food.milkBone;
        if (inventory.buyFood(milkBone)) {
            balance = inventory.getBalance();
            System.out.println("\nYou bought a Milk Bone! Your new balance is " + balance + ".");
        } else {
            System.out.println("\nYou do not have enough money to buy a Milk Bone... "
                    + "Your balance is " + balance + ".");
        }
    }

    // MODIFIES: this and buyFood
    // EFFECTS: buys kibble
    private void processBuyKibble() {
        Inventory inventory = dog.getInventory();
        Food kibble = Food.kibble;
        if (inventory.buyFood(kibble)) {
            balance = inventory.getBalance();
            System.out.println("\nYou bought kibble! Your new balance is " + balance + ".");
        } else {
            System.out.println("\nYou do not have enough money to buy kibble... "
                    + "Your balance is " + balance + ".");
        }
    }

    // MODIFIES: this and buyFood
    // EFFECTS: buys peanut butter
    private void processBuyPeanutButter() {
        Inventory inventory = dog.getInventory();
        Food peanutButter = Food.peanutButter;
        if (inventory.buyFood(peanutButter)) {
            balance = inventory.getBalance();
            System.out.println("\nYou bought peanut butter! Your new balance is " + balance + ".");
        } else {
            System.out.println("\nYou do not have enough money to buy peanut butter.. "
                    + "Your balance is " + balance + ".");
        }
    }

    // EFFECTS: saves the dog to file
    private void saveDog() {
        try {
            jsonWriter.open();
            jsonWriter.write(dog);
            jsonWriter.close();
            System.out.println("Saved " + dog.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads dog from file
    private void loadDog() {
        try {
            dog = jsonReader.read();
            name = dog.getName();
            System.out.println("Loaded " + dog.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}