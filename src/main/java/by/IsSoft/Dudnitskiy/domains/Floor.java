package by.IsSoft.Dudnitskiy.domains;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class Floor {

    private final int number;
    private final int maxFloor;
    private List<Person> toUpPersons = new ArrayList<>();
    private List<Person> toDownPersons;
    private boolean toUp;
    private boolean toDown;

    public Floor(int number, int maxFloor) {
        this.number = number;
        this.maxFloor = maxFloor;
        toDownPersons = new ArrayList<>();
        toDownPersons = new ArrayList<>();
        toUp = false;
        toDown = false;
    }

    public void pushButtons() {
        if (!toUpPersons.isEmpty()) {
            toUp = true;
        } else {
            toUp = false;
        }
        if (!toDownPersons.isEmpty()) {
            toDown = true;
        } else {
            toDown = false;
        }
    }

    public void spawnPerson() {
        Person person = new Person(Math.random() * 100 + 20, (int) (Math.random() * maxFloor + 1));
        if (person.getFloor() > number) {
            toUpPersons.add(person);
        } else  if (person.getFloor() < number){
            toDownPersons.add(person);
        }
        pushButtons();
    }

}
