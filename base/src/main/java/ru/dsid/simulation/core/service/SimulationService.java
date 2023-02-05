package ru.dsid.simulation.core.service;

public interface SimulationService {
    void start();

    void pause();

    void resume();

    void stop();

    void setRate(double rate);

    int getIteration();
}
