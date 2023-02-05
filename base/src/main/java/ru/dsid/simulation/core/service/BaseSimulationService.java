package ru.dsid.simulation.core.service;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseSimulationService implements SimulationService {
    protected final AtomicInteger iteration = new AtomicInteger();
    protected volatile boolean stop;
    protected volatile boolean pause;

    @Override
    public void start() {
        clear();
        iteration.set(0);
        init();
        while (!stop && !pause) {
            runIteration();
            iteration.incrementAndGet();
        }
    }

    protected abstract void init();

    protected abstract void runIteration();

    @Override
    public synchronized void pause() {
        this.pause = true;
        notifyAll();
    }

    @Override
    public synchronized void resume() {
        this.pause = false;
        notifyAll();
    }

    @Override
    public synchronized void stop() {
        this.stop = true;
        this.pause = false;
        iteration.set(0);
        clear();
        notifyAll();
    }

    protected abstract void clear();

    @Override
    public int getIteration() {
        return iteration.get();
    }
}
