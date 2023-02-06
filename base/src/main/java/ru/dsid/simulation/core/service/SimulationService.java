package ru.dsid.simulation.core.service;

public interface SimulationService {
    void start();

    boolean isPlaying();

    void pause();

    boolean isPaused();

    void resume();

    void stop();

    boolean isStopped();

    void setRate(double rate);

    double getRate();

    int getIteration();
}
