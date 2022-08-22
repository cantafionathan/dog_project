package ui;

import model.objects.Event;
import model.objects.Food;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// determines which button was pressed and delegates to appropriate handler method
public class ActionHandler implements ActionListener {

    GameManager gameManager;

    public ActionHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // EFFECTS: performs the action corresponding to the actionCode
    // MODIFIES: gui
    @Override
    public void actionPerformed(ActionEvent e) {
        String yourChoice = e.getActionCommand();
        firstHalfCases(yourChoice);
        secondHalfCases(yourChoice);
        gameManager.gui.removeBackgroundPanel();
        gameManager.gui.generateScreen();
        gameManager.gui.window.revalidate();
        gameManager.gui.window.repaint();
    }

    // EFFECTS: determines which button was pressed for first half of cases
    // MODIFIES: gui
    public void firstHalfCases(String yourChoice) {
        if ("feed kibble".equals(yourChoice)) {
            gameManager.gui.handleFeed(Food.kibble);
        } else if ("buy kibble".equals(yourChoice)) {
            gameManager.gui.handleBuy(Food.kibble);
        } else if ("feed steak".equals(yourChoice)) {
            gameManager.gui.handleFeed(Food.steak);
        } else if ("buy steak".equals(yourChoice)) {
            gameManager.gui.handleBuy(Food.steak);
        } else if ("feed milk bone".equals(yourChoice)) {
            gameManager.gui.handleFeed(Food.milkBone);
        } else if ("buy milk bone".equals(yourChoice)) {
            gameManager.gui.handleBuy(Food.milkBone);
        } else if ("feed peanut butter".equals(yourChoice)) {
            gameManager.gui.handleFeed(Food.peanutButter);
        }
    }

    // EFFECTS: determines which button was pressed for second half of cases
    // MODIFIES: gui
    public void secondHalfCases(String yourChoice) {
        if ("buy peanut butter".equals(yourChoice)) {
            gameManager.gui.handleBuy(Food.peanutButter);
        } else if ("play".equals(yourChoice)) {
            gameManager.gui.handlePlay();
        } else if ("show".equals(yourChoice)) {
            gameManager.gui.handleShow();
        } else if ("load".equals(yourChoice)) {
            gameManager.gui.loadDog();
        } else if ("new game".equals(yourChoice)) {
            gameManager.gui.newGame();
        } else if ("save".equals(yourChoice)) {
            gameManager.gui.saveDog();
        } else if ("quit".equals(yourChoice)) {
            for (Event next : model.objects.EventLog.getInstance()) {
                System.out.println(next.toString());
                System.out.println(" ");
            }
            System.exit(0);
        }
    }
}
