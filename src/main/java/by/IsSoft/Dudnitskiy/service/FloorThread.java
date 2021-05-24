package by.IsSoft.Dudnitskiy.service;

import by.IsSoft.Dudnitskiy.domains.Floor;
import lombok.SneakyThrows;

public class FloorThread extends Thread{

    private final Floor floor;
    private boolean sleep;
    private boolean run;

    @SneakyThrows
    @Override
    public void run() {
        run = true;
        while(run) {
            while (sleep) {
                Thread.sleep(1000);
            }
            synchronized (floor) {
                floor.spawnPerson();
            }
            Thread.sleep(3000);
        }
    }

    @Override
    public synchronized void start() {
        super.start();
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

    public FloorThread(Floor floor) {
        this.floor = floor;
        sleep = false;
    }
}
