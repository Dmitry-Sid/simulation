package ru.dsid.simulation.mutation.service;

import ru.dsid.simulation.core.service.SimulationService;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.pojo.Area;
import ru.dsid.simulation.pojo.Food;

import java.util.List;

public interface MutationSimulationService extends SimulationService {

    Area getArea();

    List<MutationCreature> getCreatures();

    List<Food> getFood();
}
