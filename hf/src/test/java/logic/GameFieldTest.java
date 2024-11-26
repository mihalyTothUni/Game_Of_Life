package logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class GameFieldTest {

    @Test
    public void testDefaultConstructor() {
        GameField field = new GameField();
        assertEquals(100, field.getDimX());
        assertEquals(50, field.getDimY());
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                assertFalse(field.get(new Coordinates(i, j)));
            }
        }
    }

    @Test
    public void testParameterizedConstructor() {
        GameField field = new GameField(10, 20);
        assertEquals(10, field.getDimX());
        assertEquals(20, field.getDimY());
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                assertFalse(field.get(new Coordinates(i, j)));
            }
        }
    }

    @Test
    public void testConstructorWithPercentage() {
        GameField field = new GameField(10, 20, 50);
        assertEquals(10, field.getDimX());
        assertEquals(20, field.getDimY());
        int aliveCount = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (field.get(new Coordinates(i, j))) {
                    aliveCount++;
                }
            }
        }
        assertTrue(aliveCount > 0); // At least some cells should be alive
    }

    @Test
    public void testGetAndSet() {
        GameField field = new GameField(10, 10);
        Coordinates coords = new Coordinates(5, 5);
        field.set(coords, true);
        assertTrue(field.get(coords));
        field.set(coords, false);
        assertFalse(field.get(coords));
    }

    @Test
    public void testSetOutOfBounds() {
        GameField field = new GameField(10, 10);
        Coordinates coords = new Coordinates(15, 15);
        assertThrows(IllegalArgumentException.class, () -> {
            field.set(coords, true);
        });
    }

    @Test
    public void testClear() {
        GameField field = new GameField(10, 10);
        field.set(new Coordinates(5, 5), true);
        field.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertFalse(field.get(new Coordinates(i, j)));
            }
        }
    }
}
