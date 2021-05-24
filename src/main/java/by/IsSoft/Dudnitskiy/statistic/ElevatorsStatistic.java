package by.IsSoft.Dudnitskiy.statistic;

import by.IsSoft.Dudnitskiy.domains.Elevator;
import by.IsSoft.Dudnitskiy.domains.Person;

import java.util.*;

public class ElevatorsStatistic {

    private Map<String, Map<Integer, List<Person>>> transportedPersons;

    public ElevatorsStatistic(List<Elevator> elevators, int numberOfFloors) {
        transportedPersons = new HashMap<>();
        elevators.forEach(e -> {
            Map<Integer, List<Person>> fromTo = new HashMap<>();
            for (int i = 1; i <= numberOfFloors; i++) {
                fromTo.put(i, new ArrayList<>());
            }
            transportedPersons.put(e.toString(), fromTo);
        });
    }

    public void addStatistics(Elevator elevator, int from, Person person) {
        transportedPersons.get(elevator.toString()).get(from).add(person);
    }

    @Override
    public String toString() {

        return "transportedPersons = {" + transportedPersons +
                '}';
    }

    public Map<String, Map<Integer, List<Person>>> getTransportedPersons() {
        return transportedPersons;
    }
}
