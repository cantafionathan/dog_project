package ui;

import model.objects.Dog;
import model.objects.Food;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// graphics are generated here, as well as dog stored, handler methods defined, and save and load methods defined
public class GUI {

    GameManager gameManager;
    JFrame window;
    JTextArea messageText;
    JPanel backgroundPanel;
    JLabel backgroundLabel;
    Dog dog;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/dog.json";

    // EFFECTS: starts the GUI
    // MODIFIES: this
    public GUI(GameManager gameManager) {
        this.gameManager = gameManager;
        init();
        createMainField();
        generateScreen();
        newGame();

        window.setVisible(true);
    }

    // EFFECTS: initializes fields
    // MODIFIES: this
    public void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        backgroundPanel = new JPanel();
        backgroundLabel = new JLabel();
        dog = new Dog(null);
    }

    // EFFECTS: creates main window and text area
    // MODIFIES: this
    public void createMainField() {
        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);

        messageText = new JTextArea("Welcome to the Dog Show!");
        messageText.setBounds(50, 400, 700, 150);
        messageText.setBackground(Color.black);
        messageText.setForeground(Color.white);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Book Antiqua", Font.PLAIN, 26));
        window.add(messageText);
    }

    // EFFECTS: creates backgroundPanel
    // MODIFIES: this
    public void createBackground(String fileName) {
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(50, 50, 700, 350);
        backgroundPanel.setBackground(Color.black);
        backgroundPanel.setLayout(null);
        window.add(backgroundPanel);

        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 700, 350);

        ImageIcon backgroundIcon = new ImageIcon(fileName);

        backgroundLabel.setIcon(backgroundIcon);
    }

    // EFFECTS: creates button of specified size and location with buttonLabel = label and ActionCommand = command
    // MODIFIES: this
    public void createButton(int x, int y, int width, int height, String label, String command) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        JLabel buttonLabel = new JLabel(label);
        buttonLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 15));
        button.add(buttonLabel);
        button.addActionListener(gameManager.actionHandler);
        button.setActionCommand(command);

        backgroundPanel.add(button);
    }

    // EFFECTS: creates dog of specified size and location with ImageIcon = fileName and with popMenu
    // MODIFIES: this
    public void createDog(int x, int y, int width, int height, String fileName) {
        // CREATE POP MENU
        JPopupMenu popMenu = new JPopupMenu();
        //CREATE POP MENU ITEMS
        createPopUpMenu(popMenu);

        // CREATE DOG
        JButton dogButton = new JButton();
        createDogButton(dogButton, x, y, width, height, fileName);

        createActionListener(dogButton, popMenu);

        backgroundPanel.add(dogButton);
    }

    // EFFECTS: sets up the dog button
    public void createDogButton(JButton dogButton, int x, int y, int width, int height, String fileName) {
        dogButton.setBounds(x, y, width, height);

        ImageIcon objectIcon = new ImageIcon(fileName);
        dogButton.setIcon(objectIcon);

        dogButton.setFocusPainted(false);
        dogButton.setMargin(new Insets(0, 0, 0, 0));
        dogButton.setContentAreaFilled(false);
        dogButton.setBorderPainted(false);
        dogButton.setOpaque(false);
    }

    // EFFECTS: adds menu items to popMenu menu
    public void createPopUpMenu(JPopupMenu popMenu) {
        JMenuItem[] menuItem = new JMenuItem[3]; // use [1], [2]

        createFeedMenu(popMenu);

        createBuyMenu(popMenu);

        menuItem[1] = new JMenuItem("Play");
        menuItem[1].addActionListener(gameManager.actionHandler);
        menuItem[1].setActionCommand("play");
        popMenu.add(menuItem[1]);

        menuItem[2] = new JMenuItem("Take to Dog Show");
        menuItem[2].addActionListener(gameManager.actionHandler);
        menuItem[2].setActionCommand("show");
        popMenu.add(menuItem[2]);
    }

    // EFFECTS: adds the feed submenu to the feed button in popMenu
    public void createFeedMenu(JPopupMenu popMenu) {
        JMenu feedMenu = new JMenu("Feed");

        JMenuItem feedMenuItemKibble = new JMenuItem("Kibble x " + dog.getInventory().countFood(Food.kibble));
        feedMenuItemKibble.addActionListener(gameManager.actionHandler);
        feedMenuItemKibble.setActionCommand("feed kibble");

        JMenuItem feedMenuItemSteak = new JMenuItem("Steak x " + dog.getInventory().countFood(Food.steak));
        feedMenuItemSteak.addActionListener(gameManager.actionHandler);
        feedMenuItemSteak.setActionCommand("feed steak");

        JMenuItem feedMenuItemMilkBone = new JMenuItem("Milk Bone x " + dog.getInventory().countFood(Food.milkBone));
        feedMenuItemMilkBone.addActionListener(gameManager.actionHandler);
        feedMenuItemMilkBone.setActionCommand("feed milk bone");

        JMenuItem feedMenuItemPeanutButter = new JMenuItem("Peanut Butter x "
                + dog.getInventory().countFood(Food.peanutButter));
        feedMenuItemPeanutButter.addActionListener(gameManager.actionHandler);
        feedMenuItemPeanutButter.setActionCommand("feed peanut butter");

        feedMenu.add(feedMenuItemKibble);
        feedMenu.add(feedMenuItemSteak);
        feedMenu.add(feedMenuItemMilkBone);
        feedMenu.add(feedMenuItemPeanutButter);
        popMenu.add(feedMenu);
    }

    // EFFECTS: adds the buy submenu to the buy button in popMenu
    public void createBuyMenu(JPopupMenu popMenu) {
        JMenu buyMenu = new JMenu("Buy food");

        JMenuItem buyMenuItemKibble = new JMenuItem("($" + Food.kibble.getCost() + ") Kibble x "
                + dog.getInventory().countFood(Food.kibble));
        buyMenuItemKibble.addActionListener(gameManager.actionHandler);
        buyMenuItemKibble.setActionCommand("buy kibble");

        JMenuItem buyMenuItemSteak = new JMenuItem("($" + Food.steak.getCost() + ") Steak x "
                + dog.getInventory().countFood(Food.steak));
        buyMenuItemSteak.addActionListener(gameManager.actionHandler);
        buyMenuItemSteak.setActionCommand("buy steak");

        JMenuItem buyMenuItemMilkBone = new JMenuItem("($" + Food.milkBone.getCost() + ") Milk Bone x "
                + dog.getInventory().countFood(Food.milkBone));
        buyMenuItemMilkBone.addActionListener(gameManager.actionHandler);
        buyMenuItemMilkBone.setActionCommand("buy milk bone");

        JMenuItem buyMenuItemPeanutButter = new JMenuItem("($" + Food.peanutButter.getCost() + ") Peanut Butter x "
                + dog.getInventory().countFood(Food.peanutButter));
        buyMenuItemPeanutButter.addActionListener(gameManager.actionHandler);
        buyMenuItemPeanutButter.setActionCommand("buy peanut butter");

        buyMenu.add(buyMenuItemKibble);
        buyMenu.add(buyMenuItemSteak);
        buyMenu.add(buyMenuItemMilkBone);
        buyMenu.add(buyMenuItemPeanutButter);
        popMenu.add(buyMenu);
    }

    // EFFECTS: creates ActionListener for dog button
    public void createActionListener(JButton button, JPopupMenu popMenu) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Point mousePos = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(mousePos, button);
                int x = mousePos.x;
                int y = mousePos.y;
                if (e.getSource() == button) {
                    popMenu.show(button, x, y);
                }
            }
        });
    }

    // EFFECTS: creates a label of specified size and location and with the corresponding stat displayed
    // MODIFIES: this
    public void createStat(int x, int y, int width, int height, String statInformation) {
        JPanel statPanel = new JPanel();
        statPanel.setBounds(x, y, width, height);

        JLabel statLabel = new JLabel(statInformation);
        statLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 15));
        statPanel.add(statLabel);

        backgroundPanel.add(statPanel);
    }

    // EFFECTS; adds dog, buttons, and stats to the background pannel
    // MODIFIES: this
    public void generateScreen() {
        // MAIN SCREEN
        createBackground("./data/res/background.jpg");
        createDog(200, 40, 300, 271, "./data/res/dog.png");
        createButton(530, 80, 145, 50, "New Game", "new game");
        createButton(530, 135, 145, 50, "Load Old Game", "load");
        createButton(530, 190, 145, 50, "Save", "save");
        createButton(530, 245, 145, 50, "Quit", "quit");
        createStat(30, 100, 145, 50, "Health: " + dog.getHealth() + "/" + Dog.MAX_HEALTH);
        createStat(30, 155, 145, 50, "Hunger: " + dog.getHunger() + "/" + Dog.MAX_HUNGER);
        createStat(30, 210, 145, 50, "Balance: $" + dog.getInventory().getBalance());

        backgroundPanel.add(backgroundLabel);
    }

    // EFFECTS: handles when user presses "New Game" button and displays appropriate messageText
    // MODIFIES: this
    public void newGame() {
        messageText.setText("Enter your new dog's name in this text box (make sure to delete all the existing text "
                + "first), and then press the ENTER key to start a new game! Or you can press the 'Load Old Game' "
                + "button to load data from an old save.");
        messageText.setEditable(true);
        messageText.addKeyListener(gameManager.keyHandler);
    }

    // EFFECTS: handles when user presses "Play" button and displays appropriate messageText
    // MODIFIES: this
    public void handlePlay() {
        int dogHealth = dog.getHealth();
        if (dog.getHealth() == 0) {
            messageText.setText(dog.getName() + " has already passed...");
            dog.setHealth(dogHealth);
        } else if (dog.playWithDog()) {
            if (dogHealth == Dog.MAX_HEALTH && dog.getHunger() != 0) {
                messageText.setText("You played with " + dog.getName() + "! Since " + dog.getName()
                        + " is already at maximum health, " + dog.getName() + " cannot gain any more.");

            } else if (dogHealth >= Dog.MAX_HEALTH - Dog.PLAY_HEALTH_INCREASE && dog.getHunger() != 0) {
                messageText.setText("You played with " + dog.getName() + "! Since " + dog.getName()
                        + " has now reached maximum health, " + dog.getName() + " cannot gain any more.");

            } else {
                messageText.setText("You played with " + dog.getName() + "!");
            }
        } else {
            messageText.setText(dog.getName() + " is too tired to play...");
            dog.setHealth(dogHealth);
        }
    }

    // EFFECTS: handles when user presses "Dog Show" button and displays appropriate textMessage
    // MODIFIES: this
    public void handleShow() {
        int dogHealth = dog.getHealth();
        if (dog.takeToDogShow()) {
            messageText.setText("You took " + dog.getName() + " to the dog show!");
        } else if (dogHealth == 0) {
            messageText.setText(dog.getName() + " has already passed...");
        } else {
            messageText.setText(dog.getName() + " was too weak to go to the dog show and has died...");
        }
    }

    // EFFECTS: handles when user presses one of the "Feed" buttons and displays appropriate messageText
    // MODIFIES: this
    public void handleFeed(Food food) {
        int dogHealth = dog.getHealth();
        if (dogHealth == 0) {
            messageText.setText(dog.getName() + " has already passed...");
        } else if (dog.feedDog(food)) {
            messageText.setText("You fed " + dog.getName() + " " + food.getNameFood());
        } else {
            messageText.setText("You don't have any " + food.getNameFood());
        }
    }

    // EFFECTS: handles when user presses one of the "Buy" buttons and displays appropriate messageText
    // MODIFIES: this
    public void handleBuy(Food food) {
        if (dog.getInventory().buyFood(food)) {
            messageText.setText("\nYou bought " + food.getNameFood());
        } else {
            messageText.setText("You do not have enough money to buy " + food.getNameFood() + "... ");
        }
    }

    // EFFECTS: removes backgroundPanel
    // MODIFIES: this
    public void removeBackgroundPanel() {
        window.remove(backgroundPanel);
    }

    // EFFECTS: saves dog to file
    public void saveDog() {
        try {
            jsonWriter.open();
            jsonWriter.write(dog);
            jsonWriter.close();
            messageText.setText("Saved " + dog.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            messageText.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads dog from file
    public void loadDog() {
        try {
            dog = jsonReader.read();
            messageText.setText("Loaded " + dog.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            messageText.setText("Unable to read from file: " + JSON_STORE);
        }
    }
}
