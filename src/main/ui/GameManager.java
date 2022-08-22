package ui;

// ui code based on tutorial for making Point and Click Adventure Game by RyiSnow
// : https://www.youtube.com/playlist?list=PL_QPQmz5C6WVLQ2_yYpN5BjEaS9uLRGbD
// hub for GUI and related classes
public class GameManager {

    KeyHandler keyHandler;
    ActionHandler actionHandler;
    GUI gui;

    // EFFECTS: runs the application
    // MODIFIES: this
    public GameManager() {
        keyHandler = new KeyHandler(this);
        actionHandler = new ActionHandler(this);
        gui = new GUI(this);
    }

}
