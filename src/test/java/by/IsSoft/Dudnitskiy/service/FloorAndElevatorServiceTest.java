package by.IsSoft.Dudnitskiy.service;

import by.IsSoft.Dudnitskiy.domains.Elevator;
import by.IsSoft.Dudnitskiy.domains.Floor;
import by.IsSoft.Dudnitskiy.domains.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.*;

class FloorAndElevatorServiceTest {

    private FloorAndElevatorService floorAndElevatorService;
    private List<Floor> floors;
    private List<Elevator> elevators;

    @BeforeEach
    public void before() {

        floors = new ArrayList<>();
        floors.add(new Floor(1, 3));
        floors.add(new Floor(2, 3));
        floors.add(new Floor(3, 3));
        floors.get(0).getToUpPersons().add(new Person(84, 3));
        elevators = new ArrayList<>();
        elevators.add((new Elevator(300, 5, 2, 3, 1)));


    }

    @Test
    void startSpawnTest() throws InterruptedException {
        floorAndElevatorService = new FloorAndElevatorService(floors, elevators);
        floorAndElevatorService.startSpawn();
        Thread.sleep(5000);
        assertTrue(floorAndElevatorService.getFloors().get(0).getToDownPersons().size() > 0
                || floorAndElevatorService.getFloors().get(0).getToUpPersons().size() > 0);
        assertTrue(floorAndElevatorService.getFloors().get(1).getToDownPersons().size() > 0
                || floorAndElevatorService.getFloors().get(1).getToUpPersons().size() > 0);
        assertTrue(floorAndElevatorService.getFloors().get(2).getToDownPersons().size() > 0
                || floorAndElevatorService.getFloors().get(2).getToUpPersons().size() > 0);
    }

}