package model.tests;

import model.objects.Food;
import model.objects.Inventory;
import model.objects.Dog;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// Based on JsonSerializationDemo's JsonTest class
// Repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkDog(String name, int health, int hunger, Inventory inventory, Dog dog) {
        assertEquals(name, dog.getName());
        assertEquals(health, dog.getHealth());
        assertEquals(hunger, dog.getHunger());
        LinkedList<Food> foods = inventory.getFoods();
        Inventory inventory1 = dog.getInventory();
        LinkedList<Food> foods1 = inventory1.getFoods();
        assertEquals(foods.size(), foods1.size());
    }
}
