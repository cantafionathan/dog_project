package ui;

import model.objects.Dog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// handles KeyEvent (specifically when pressing ENTER key to name new dog)
public class KeyHandler implements KeyListener {

    GameManager gameManager;

    public KeyHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // EFFECTS: sets messageText to be editable and makes a new dog with the appropriate name
    // MODIFIES: this
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER) {
            String name = gameManager.gui.messageText.getText();
            gameManager.gui.dog = new Dog(name);
        }
    }

    // EFFECTS: sets messageText to not be editable and tells user they made a new dog with the appropriate name
    // MODIFIES: gui
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER) {
            String name = gameManager.gui.messageText.getText();
            gameManager.gui.messageText.setEditable(false);
            gameManager.gui.messageText.setText("Your new dog has been named " + name);
            gameManager.gui.messageText.removeKeyListener(gameManager.keyHandler);
        }
    }
}
