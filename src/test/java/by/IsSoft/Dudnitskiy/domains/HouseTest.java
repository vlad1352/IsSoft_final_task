package by.IsSoft.Dudnitskiy.domains;

import by.IsSoft.Dudnitskiy.statistic.ElevatorsStatistic;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HouseTest {

    private House house;

    @SneakyThrows
    @Test
    void houseTest() {
        house = new House(1, 5);
        Person person_1 = new Person(100, 1);
        Person person_2 = new Person(47, 2);
        Person person_3 = new Person(75, 3);
        Person person_4 = new Person(92, 5);

        house.getFloors().get(0).getToUpPersons().add(person_4);
        house.getFloors().get(1).getToUpPersons().add(person_3);
        house.getFloors().get(4).getToDownPersons().add(person_1);
        house.getFloors().get(3).getToDownPersons().add(person_2);

        house.getFloors().forEach(Floor::pushButtons);

        house.getFloorAndElevatorService().startElevators();
        Thread.sleep(20_000);

        ElevatorsStatistic statistic = house.getStatistic();
        List<Person> checkList = new ArrayList<>();
        checkList.add(person_4);
        Map<Integer, List<Person>> checkMap = statistic.getTransportedPersons().get("Elevator_1");

        assertTrue(checkMap.containsKey(1) && checkMap.containsValue(checkList));

        checkList.clear();
        checkList.add(person_1);
        assertTrue(checkMap.containsKey(5) && checkMap.containsValue(checkList));

        checkList.clear();
        checkList.add(person_2);
        assertTrue(checkMap.containsKey(4) && checkMap.containsValue(checkList));

        checkList.clear();
        checkList.add(person_3);
        assertTrue(checkMap.containsKey(2) && checkMap.containsValue(checkList));

        house.getFloors().forEach(f -> {
            assertTrue(f.getToDownPersons().isEmpty() && f.getToUpPersons().isEmpty());
        });


    }
}