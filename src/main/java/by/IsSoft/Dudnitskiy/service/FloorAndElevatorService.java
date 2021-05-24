package by.IsSoft.Dudnitskiy.service;

import by.IsSoft.Dudnitskiy.domains.Elevator;
import by.IsSoft.Dudnitskiy.domains.Floor;
import by.IsSoft.Dudnitskiy.statistic.ElevatorsStatistic;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import java.util.*;

@Getter
@Setter
public class FloorAndElevatorService {
    private final List<Floor> floors;
    private final List<Elevator> elevators;
    private List<Thread> elevatorsThreads;
    private  List<Thread> spawnPersonsThreads;
    private ElevatorsStatistic statistic;

    public FloorAndElevatorService(List<Floor> floors, List<Elevator> elevators) {
        this.floors = floors;
        this.elevators = elevators;
    }

    @SneakyThrows
    public void start() {
        startSpawn();
        Thread.sleep(5_000);
        startElevators();
    }

    public void startElevators() {
        elevatorsThreads = new ArrayList<>();
        for (Elevator elevator : elevators) {
            ElevatorsThread elevatorsThread = new ElevatorsThread(elevator, floors);
            elevatorsThreads.add(elevatorsThread);
        }

        elevatorsThreads.forEach(Thread::start);
    }

    public void startSpawn() {
        spawnPersonsThreads = new ArrayList<>();
        for(Floor floor : floors) {
            FloorThread thread = new FloorThread(floor);
            spawnPersonsThreads.add(thread);
        }
        spawnPersonsThreads.forEach(Thread::start);

    }

    public void setStatistic(ElevatorsStatistic statistic) {
        this.statistic = statistic;
        elevators.forEach(e -> e.setStatistic(statistic));
    }

}
