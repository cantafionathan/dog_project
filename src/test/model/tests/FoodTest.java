package model.tests;

import model.objects.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    @Test
    public void testGetNameFood() {
        assertEquals("Steak", Food.steak.getNameFood());
    }
}
