package model.tests;

import model.objects.Dog;
import model.objects.Food;
import model.objects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DogTest {
    private Dog Toby;

    @BeforeEach
    public void runBefore() {
        this.Toby = new Dog("Toby");
    }


    @Test
    public void testGetName() {
        assertEquals("Toby", Toby.getName());
    }

    @Test
    public void testPlayWithDogMaxHealthMaxHunger() {
        assertTrue(Toby.playWithDog());
        assertEquals(Dog.MAX_HEALTH, Toby.getHealth());
        assertEquals(Dog.MAX_HUNGER - Dog.PLAY_HUNGER_DECREASE, Toby.getHunger());
    }

    @Test
    public void testPlayWithDogOneLessMaxHealthJustToMinHunger() {
        Toby.setHealth(Dog.MAX_HEALTH - 1);
        Toby.setHunger(Dog.PLAY_HUNGER_DECREASE);
        assertTrue(Toby.playWithDog());
        assertEquals(Dog.MAX_HEALTH, Toby.getHealth());
        assertEquals(0, Toby.getHunger());
    }

    @Test
    public void testPlayWithDogJustToMaxHealthOneMoreMinHunger() {
        Toby.setHealth(Dog.MAX_HEALTH - Dog.PLAY_HEALTH_INCREASE);
        Toby.setHunger(1);
        assertFalse(Toby.playWithDog());
        assertEquals(Dog.MAX_HEALTH, Toby.getHealth());
        assertEquals(0, Toby.getHunger());
    }

    @Test
    public void testPlayWithDogMinHealthMinHunger() {
        Toby.setHealth(0);
        Toby.setHunger(0);
        assertFalse(Toby.playWithDog());
        assertEquals(Dog.PLAY_HEALTH_INCREASE, Toby.getHealth());
        assertEquals(0, Toby.getHunger());
    }

    @Test
    public void testTakeToDogShowMinHealth() {
        Inventory inventory = Toby.getInventory();

        Toby.setHealth(0);
        assertFalse(Toby.takeToDogShow());
        assertEquals(0, Toby.getHealth());
        assertEquals(Inventory.STARTING_BALANCE, inventory.getBalance());
    }

    @Test
    public void testTakeToDogShowOneMoreMinHealth() {
        Inventory inventory = Toby.getInventory();

        Toby.setHealth(1);
        assertFalse(Toby.takeToDogShow());
        assertEquals(0, Toby.getHealth());
        assertEquals(Inventory.STARTING_BALANCE, inventory.getBalance());

    }

    @Test
    public void testTakeToDogShowJustToMinHealth() {
        Inventory inventory = Toby.getInventory();

        Toby.setHealth(Dog.DOG_SHOW_HEALTH_DEDUCTION);
        assertFalse(Toby.takeToDogShow());
        assertEquals(0, Toby.getHealth());
        assertEquals(Inventory.STARTING_BALANCE, inventory.getBalance());
    }

    @Test
    public void testTakeToDogShowMaxHealth() {
        Inventory inventory = Toby.getInventory();
        inventory.setBalance(500);

        assertTrue(Toby.takeToDogShow());
        assertEquals(Dog.MAX_HEALTH - Dog.DOG_SHOW_HEALTH_DEDUCTION, Toby.getHealth());
        assertEquals(500 + Inventory.PAY_AMOUNT, inventory.getBalance());
    }

    @Test
    public void testFeedDogMaxHealthMinHunger() {
        Inventory inventory = Toby.getInventory();
        inventory.setBalance(5000);
        inventory.buyFood(Food.steak);

        Toby.setHealth(Dog.MAX_HEALTH);
        Toby.setHunger(0);
        Toby.feedDog(Food.steak);
        assertEquals(Dog.MAX_HEALTH, Toby.getHealth());
        assertEquals(Food.STEAK_SATIETY, Toby.getHunger());
    }

    @Test
    public void testFeedDogOneLessMaxHealthJustToMaxHunger() {
        Inventory inventory = Toby.getInventory();
        inventory.setBalance(5000);
        inventory.buyFood(Food.steak);
        inventory.buyFood(Food.steak);

        Toby.setHealth(Dog.MAX_HEALTH - 1);
        Toby.setHunger(Dog.MAX_HUNGER - Food.STEAK_SATIETY);
        Toby.feedDog(Food.steak);
        assertEquals(Dog.MAX_HEALTH, Toby.getHealth());
        assertEquals(Dog.MAX_HUNGER, Toby.getHunger());
    }

    @Test
    public void testFeedDogJustToMaxHealthOneLessMaxHunger() {
        Inventory inventory = Toby.getInventory();
        inventory.setBalance(5000);
        inventory.buyFood(Food.steak);
        inventory.buyFood(Food.milkBone);

        Toby.setHealth(Dog.MAX_HEALTH - Food.MILKbONE_HEARTINESS);
        Toby.setHunger(Dog.MAX_HUNGER - 1);
        Toby.feedDog(Food.milkBone);
        assertEquals(Dog.MAX_HEALTH, Toby.getHealth());
        assertEquals(Dog.MAX_HUNGER, Toby.getHunger());
    }

    @Test
    public void testFeedDogMinHealthMaxHunger() {
        Inventory inventory = Toby.getInventory();
        inventory.setBalance(5000);
        inventory.buyFood(Food.steak);
        inventory.buyFood(Food.milkBone);


        Toby.setHealth(0);
        Toby.setHunger(Dog.MAX_HUNGER);
        Toby.feedDog(Food.steak);
        assertEquals(Food.STEAK_HEARTINESS, Toby.getHealth());
        assertEquals(Dog.MAX_HUNGER, Toby.getHunger());
    }

    @Test
    public void testFeeDogDoNoHaveSpecifiedFood() {
        Inventory inventory = Toby.getInventory();
        inventory.setBalance(5000);
        inventory.buyFood(Food.kibble);

        assertFalse(Toby.feedDog(Food.steak));
    }
}
