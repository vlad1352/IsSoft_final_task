package by.IsSoft.Dudnitskiy.service;

import by.IsSoft.Dudnitskiy.domains.Elevator;
import by.IsSoft.Dudnitskiy.domains.Floor;
import by.IsSoft.Dudnitskiy.domains.Person;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

public class ElevatorsThread extends Thread {

    private final Elevator elevator;
    private final double floorHeight = 5;
    private final double elevatorDoors = 2;
    private List<Floor> floors;
    private boolean run;
    private boolean sleep;

    @SneakyThrows
    @Override
    public void run() {
        run = true;

        while (run) {

            while (sleep) {
                Thread.sleep(1000);
            }

            List<Integer> nextFloors = new ArrayList<>();
            if(elevator.getPersonList().isEmpty()) {
                floors.forEach(f -> {
                    if (f.isToUp() || f.isToDown()) {
                        nextFloors.add(f.getNumber() - elevator.getCurrentFloor());
                    }
                });
                while (nextFloors.isEmpty()) {
                    Thread.sleep(1000);
                    nextFloors.clear();
                    if (elevator.getPersonList().isEmpty()) {
                        floors.forEach(f -> {
                            if (f.isToUp() || f.isToDown()) {
                                nextFloors.add(f.getNumber() - elevator.getCurrentFloor());
                            }
                        });
                    }
                }
                int from = elevator.getCurrentFloor();
                int nextFloor;

                if (nextFloors.contains(0)) {
                    nextFloor = elevator.getCurrentFloor();
                } else {
                    int collect_1 = -1;
                    int collect_2 = -1;
                    List<Integer> collectList_1 = nextFloors.stream()
                            .filter(f -> f < 0)
                            .collect(Collectors.toList());
                    if(!collectList_1.isEmpty()){
                        collect_1 = collectList_1.stream().max(Integer::compareTo).get();
                    }
                    List<Integer> collectList_2 = nextFloors.stream()
                            .filter(f -> f > 0)
                            .collect(Collectors.toList());
                    if(!collectList_2.isEmpty()){
                        collect_2 = collectList_2.stream().min(Integer::compareTo).get();
                    }
                    if(collect_1 == -1 && collect_2 > 0) {
                        nextFloor = collect_2 + elevator.getCurrentFloor();
                    } else if(collect_2 == -1 && collect_1 > 0) {
                        nextFloor = elevator.getCurrentFloor() - collect_1;
                    } else  if(collect_1 != -1 && collect_2 != -1) {
                        if(Math.abs(collect_1) <= collect_2) {
                            nextFloor = elevator.getCurrentFloor() - collect_1;
                        } else {
                            nextFloor = elevator.getCurrentFloor() + collect_2;
                        }
                    } else {
                        nextFloor = elevator.getCurrentFloor();
                    }

                }
                elevator.moveEmptyElevator(nextFloor);
                int to = elevator.getCurrentFloor();
                Thread.sleep((long) ((floorHeight * Math.abs(to - from) / elevator.getSpeed()) * 1000 +
                        (elevatorDoors / elevator.getDoorSpeed()) * 1000));
            } else {
                int from = elevator.getCurrentFloor();
                elevator.move();
                int to = elevator.getCurrentFloor();
                Thread.sleep((long) ((floorHeight * Math.abs(to - from) / elevator.getSpeed()) * 1000 +
                        (elevatorDoors / elevator.getDoorSpeed()) * 1000));
            }
            addPersons();
        }

    }

    public void addPersons() {
        synchronized (floors.get(elevator.getCurrentFloor() - 1)) {
            Person person;
            if(elevator.getPersonList().isEmpty()) {
                if (floors.get(elevator.getCurrentFloor() - 1).isToDown()) {
                    elevator.setToUp(false);
                }
                if (floors.get(elevator.getCurrentFloor() - 1).isToUp()) {
                    elevator.setToUp(true);
                }
            }

            if (elevator.isToUp() && !floors.get(elevator.getCurrentFloor() - 1).getToUpPersons().isEmpty()) {
                person = floors.get(elevator.getCurrentFloor() - 1).getToUpPersons().get(0);
                while (elevator.addPerson(person)) {
                    floors.get(elevator.getCurrentFloor() - 1).getToUpPersons().remove(person);
                    if (floors.get(elevator.getCurrentFloor() - 1).getToUpPersons().isEmpty()) {
                        break;
                    }
                    person = floors.get(elevator.getCurrentFloor() - 1).getToUpPersons().get(0);
                }
            } else if (!floors.get(elevator.getCurrentFloor() - 1).getToDownPersons().isEmpty()){
                person = floors.get(elevator.getCurrentFloor() - 1).getToDownPersons().get(0);
                while (elevator.addPerson(person)) {
                    floors.get(elevator.getCurrentFloor() - 1).getToDownPersons().remove(person);
                    if (floors.get(elevator.getCurrentFloor() - 1).getToDownPersons().isEmpty()) {
                        break;
                    }
                    person = floors.get(elevator.getCurrentFloor() - 1).getToDownPersons().get(0);
                }
            }
            floors.get(elevator.getCurrentFloor() - 1).pushButtons();
        }
    }

    public void terminate() {
        run = false;
    }

    public void pause() {
        sleep = true;
    }

    public void unpause() {
        sleep = false;
    }

    public ElevatorsThread(Elevator elevator, List<Floor> floors) {
        this.elevator = elevator;
        this.floors = floors;
        sleep = false;
    }

    public Elevator getElevator() {
        return elevator;
    }

}
