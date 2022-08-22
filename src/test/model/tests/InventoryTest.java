package model.tests;

import model.objects.Food;
import model.objects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    public void runBefore() {
        this.inventory = new Inventory();
    }

    @Test
    public void testBuyFoodNotEnough() {
        assertFalse(inventory.buyFood(Food.peanutButter));

        LinkedList<Food> foods = inventory.getFoods();
        assertEquals(0, foods.size());
    }

    @Test
    public void testBuyFoodEnough() {
        inventory.setBalance(Food.PEANUT_BUTTER_COST);
        assertTrue(inventory.buyFood(Food.peanutButter));

        LinkedList<Food> foods = inventory.getFoods();
        assertEquals(1, foods.size());
    }

    @Test
    public void testCountFoodEmpty() {
        assertEquals(0, inventory.countFood(Food.steak));
    }

    @Test
    public void testCountFoodNonEmptySame() {
        inventory.setBalance(1000);
        inventory.buyFood(Food.steak);
        assertEquals(1, inventory.countFood(Food.steak));
    }

    @Test
    public void testCountFoodNonEmptyDifferent() {
        inventory.setBalance(1000);
        inventory.buyFood(Food.steak);
        assertEquals(0, inventory.countFood(Food.kibble));
    }

    @Test
    public void testCountFoodNonEmptyMoreThanOneSame() {
        inventory.setBalance(1000);
        inventory.buyFood(Food.steak);
        inventory.buyFood(Food.steak);
        assertEquals(2, inventory.countFood(Food.steak));
    }

    @Test
    public void testCountFoodNonEmptyMoreThanOneDifferent() {
        inventory.setBalance(1000);
        inventory.buyFood(Food.steak);
        inventory.buyFood(Food.milkBone);
        assertEquals(1, inventory.countFood(Food.steak));
    }

}
