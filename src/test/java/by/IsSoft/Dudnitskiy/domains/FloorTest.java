package by.IsSoft.Dudnitskiy.domains;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    private Floor floor;

    @Test
    void pushButtonsTest() {
        floor = new Floor(2, 10);
        List<Person> toUp = new ArrayList<>();
        toUp.add(new Person(100, 3));
        List<Person> toDown = new ArrayList<>();
        toDown.add(new Person(100, 1));

        floor.setToUpPersons(toUp);
        floor.setToDownPersons(toDown);
        floor.pushButtons();

        assertTrue(floor.isToUp());
        assertTrue(floor.isToDown());
    }
}