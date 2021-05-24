package by.IsSoft.Dudnitskiy.domains;

import by.IsSoft.Dudnitskiy.statistic.ElevatorsStatistic;
import lombok.Getter;
import lombok.extern.java.Log;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Log
public class Elevator {
    private final int number;
    private final double maxWeight;
    private double weight;
    private final double speed;
    private final double doorSpeed;
    private List<Person> personList;
    private int currentFloor;
    private List<Integer> needFloors;
    private boolean toUp;
    private int maxFloor;
    private ElevatorsStatistic statistic;

    public Elevator(double maxWeight, double speed, double doorSpeed, int maxFloor, int number) {
        this.number = number;
        this.maxWeight = maxWeight;
        this.weight = 0;
        this.speed = speed;
        this.doorSpeed = doorSpeed;
        this.personList = new ArrayList<>();
        this.currentFloor = 1;
        this.needFloors = new ArrayList<>();
        this.toUp = true;
        this.maxFloor = maxFloor;
    }

    public boolean addPerson(Person person) {
        if (maxWeight - weight >= person.getWeight()) {
            personList.add(person);
            setWeight();
            needFloors.add(person.getFloor());
            log.info(person + " added to elevator");
            return true;
        }
        return false;
    }

    public void move() {
        int from = currentFloor;
        if (toUp) {
            currentFloor = needFloors.stream()
                    .filter(f -> f > currentFloor)
                    .collect(Collectors.toList())
                    .stream().min(Integer::compare)
                    .get();
        } else {
            currentFloor = needFloors.stream()
                    .filter(f -> f < currentFloor)
                    .collect(Collectors.toList())
                    .stream().max(Integer::compare)
                    .get();
        }
        log.info("current floor: " + currentFloor);

        personList.stream()
                .filter(p -> p.getFloor() == currentFloor)
                .collect(Collectors.toList())
                .forEach(person -> {
                    log.info(person + " removed from elevator");
                    statistic.addStatistics(this, from, person);
                    personList.remove(person);
                });

        setWeight();
        if (currentFloor == 1) {
            toUp = true;
        }
        if (currentFloor == maxFloor) {
            toUp = false;
        }

    }

    public void setToUp(boolean toUp) {
        this.toUp = toUp;
    }

    public void moveEmptyElevator(int floor) {
        currentFloor = floor;
    }

    private void setWeight() {
        weight = 0;
        personList.forEach(p -> weight += p.getWeight());
    }

    public void setStatistic(ElevatorsStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public String toString() {
        return "Elevator_" + number;
    }
}
