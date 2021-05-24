package by.IsSoft.Dudnitskiy.domains;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    private Elevator elevator = new Elevator(100, 5, 2, 5, 1);

    @Test
    void addPersonTest() {
        Person person = new Person(75, 3);
        elevator.addPerson(person);
        assertEquals(elevator.getWeight(), person.getWeight());
        assertTrue(elevator.getNeedFloors().contains(person.getFloor()));
    }

    @Test
    void moveTest() {
        Person person1 = new Person(75, 3);
        Person person2 = new Person( 20, 4);
        elevator.addPerson(person1);
        elevator.addPerson(person2);
        elevator.move();

        assertEquals(elevator.getCurrentFloor(), person1.getFloor());
        assertEquals(person2.getWeight(), elevator.getWeight());
        assertFalse(elevator.getPersonList().contains(person1));
        assertTrue(elevator.getPersonList().contains(person2));
    }
}