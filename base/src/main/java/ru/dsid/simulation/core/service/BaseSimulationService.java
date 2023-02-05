package ru.dsid.simulation.core.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class BaseSimulationService implements SimulationService {
    protected final AtomicInteger iteration = new AtomicInteger();
    protected volatile boolean stop;
    protected volatile boolean pause;
    protected volatile double rate = 1;

    @Override
    public void start() {
        clear();
        iteration.set(0);
        init();
        log.info("initialized");
        while (!stop && !pause) {
            runIteration();
            iteration.incrementAndGet();
        }
        log.info("simulation finished");
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

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    protected abstract void clear();

    @Override
    public int getIteration() {
        return iteration.get();
    }
}
