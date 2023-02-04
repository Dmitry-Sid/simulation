package ru.dsid.simulation.mutation.service;

import ru.dsid.simulation.pojo.PointObject;

import java.util.List;

public interface SimulationService {
    void start();
    
    List<PointObject> getPointObjects();
}
