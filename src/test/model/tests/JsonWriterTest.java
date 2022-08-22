package model.tests;

import model.objects.Dog;
import model.objects.Food;
import model.objects.Inventory;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// Based on JsonSerializationDemo's JsonWriterTest class
// Repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Dog dog = new Dog("Toby");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDog() {
        try {
            Dog dog = new Dog("Bill");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDog.json");
            writer.open();
            writer.write(dog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDog.json");
            dog = reader.read();
            Inventory inventory = dog.getInventory();
            checkDog("Bill", 10 , 20, inventory, dog);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterStandardDog() {
        try {
            Dog dog = new Dog("Tony");
            JsonWriter writer = new JsonWriter("./data/testWriterStandardDog.json");
            Inventory inventory = new Inventory();
            inventory.putFood(Food.steak);
            inventory.putFood(Food.peanutButter);
            inventory.putFood(Food.milkBone);
            inventory.setBalance(2000);
            dog.setInventory(inventory);
            dog.setHealth(5);
            dog.setHunger(10);

            writer.open();
            writer.write(dog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterStandardDog.json");
            dog = reader.read();
            Inventory inventory1 = dog.getInventory();
            checkDog("Tony", 5, 10, inventory1, dog);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
