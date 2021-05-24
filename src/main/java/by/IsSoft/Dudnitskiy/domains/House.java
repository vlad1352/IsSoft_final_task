package by.IsSoft.Dudnitskiy.domains;

import by.IsSoft.Dudnitskiy.statistic.ElevatorsStatistic;
import by.IsSoft.Dudnitskiy.service.FloorAndElevatorService;

import java.util.ArrayList;
import java.util.List;

public class House {

    private final int numberOfFloors;
    private final int numberOfElevators;
    private List<Elevator> elevators;
    private List<Floor> floors;
    private final FloorAndElevatorService floorAndElevatorService;
    private ElevatorsStatistic statistic;


    public House() {
        this.numberOfElevators = 1;
        this.numberOfFloors = 9;
        configElevators();
        configFloors();
        floorAndElevatorService = new FloorAndElevatorService(floors, elevators);
        statistic = new ElevatorsStatistic(elevators, numberOfFloors);
        floorAndElevatorService.setStatistic(statistic);
    }

    public House(int numberOfElevators, int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        configElevators();
        configFloors();
        floorAndElevatorService = new FloorAndElevatorService(floors, elevators);
        statistic = new ElevatorsStatistic(elevators, numberOfFloors);
        floorAndElevatorService.setStatistic(statistic);

    }

    public House(int numberOfElevators, int numberOfFloors, List<Elevator> elevators) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        configElevators(elevators);
        configFloors();
        floorAndElevatorService = new FloorAndElevatorService(floors, elevators);
        statistic = new ElevatorsStatistic(elevators, numberOfFloors);
        floorAndElevatorService.setStatistic(statistic);
    }

    private void configElevators() {
        elevators = new ArrayList<>();
        for (int i = 1; i <= numberOfElevators; i++) {
            double maxWeight = Math.random() * (600 - 250) + 250;
            double doorSpeed = Math.random() * (7 - 2) + 2;
            double speed = Math.random() * (10 - 4) + 4;
            elevators.add(new Elevator(maxWeight, speed, doorSpeed, numberOfFloors, i));
        }
        statistic = new ElevatorsStatistic(elevators, numberOfFloors);
    }


    private void configElevators(List<Elevator> elevators) {
        this.elevators = elevators;
        statistic = new ElevatorsStatistic(elevators, numberOfFloors);
    }

    private void configFloors() {
        floors = new ArrayList<>();
        for (int i = 1; i <= numberOfFloors; i++) {
            floors.add(new Floor(i, numberOfFloors));
        }
    }

    public void start() {
        floorAndElevatorService.start();
    }


    public List<Floor> getFloors() {
        return floors;
    }

    public ElevatorsStatistic getStatistic() {
        return statistic;
    }

    public FloorAndElevatorService getFloorAndElevatorService() {
        return floorAndElevatorService;
    }
}
