package model.tests;

import model.objects.Dog;
import model.objects.Food;
import model.objects.Inventory;
import model.tests.JsonTest;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Based on JsonSerializationDemo's JsonReaderTest class
// Repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Dog dog = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDog.json");
        try {
            Dog dog = reader.read();
            Inventory inventory = new Inventory();
            checkDog("Toby", 10, 20, inventory, dog);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderStandardDog() {
        JsonReader reader = new JsonReader("./data/testReaderStandardDog.json");
        try {
            Dog dog = reader.read();
            Inventory inventory = new Inventory();
            inventory.putFood(Food.steak);
            inventory.putFood(Food.kibble);
            inventory.setBalance(200);
            checkDog("Bill", 5, 18, inventory, dog);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
