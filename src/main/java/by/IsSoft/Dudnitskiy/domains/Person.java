package by.IsSoft.Dudnitskiy.domains;

import lombok.Data;

@Data
public class Person {

    private final double weight;
    private final int floor;

    public Person(double weight, int floor) {
        this.weight = weight;
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Person " +
                "{ weight =" + weight +
                ", need floor =" + floor +
                '}';
    }
}
