package ru.dsid.simulation.mutation.service.impl;

import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;
import ru.dsid.simulation.mutation.service.SimulationService;
import ru.dsid.simulation.pojo.Area;
import ru.dsid.simulation.pojo.Food;
import ru.dsid.simulation.pojo.PointObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimulationServiceImpl implements SimulationService {
    private static final int INITIAL_CREATURE_COUNT = 50;
    private static final int FOOD_COUNT = 50;

    private final Area area = new Area(100, 100);
    private final Random random = new Random();
    private final List<Food> foodList = new ArrayList<>();
    private final List<MutationCreature> creatures = new ArrayList<>();
    private int iteration;

    private final CreatureService creatureService;

    public SimulationServiceImpl(CreatureService creatureService) {
        this.creatureService = creatureService;
    }

    @Override
    public void start() {
        generateFood();
        if (iteration == 0) {
            generateCreatures();
        }
    }

    private void generateFood() {
        for (int i = 0; i < FOOD_COUNT; i++) {
            final Food food = new Food();
            placeToRandomPoint(food);
            foodList.add(food);
        }
    }

    private void placeToRandomPoint(Food food) {
        food.setPointX(random.nextDouble() * area.getLength());
        food.setPointY(random.nextDouble() * area.getWidth());
    }

    private void generateCreatures() {
        for (int i = 0; i < INITIAL_CREATURE_COUNT; i++) {
            final MutationCreature creature = new MutationCreature();
            creature.setEnergy(MutationConstants.CREATURE_ENERGY);
            creature.setSize(MutationConstants.DEFAULT_SIZE);
            creature.setRange(MutationConstants.DEFAULT_RANGE);
            creatureService.calculateSpeed(creature);
            placeToRandomPoint(creature);
            creatures.add(creature);
        }
    }

    private void placeToRandomPoint(MutationCreature creature) {
        final int boundary = random.nextInt(4);
        switch (boundary) {
            case 0:
                creature.setPointX(random.nextDouble() * area.getLength());
                creature.setPointY(0);
                break;
            case 1:
                creature.setPointX(0);
                creature.setPointY(random.nextDouble() * area.getWidth());
                break;
            case 2:
                creature.setPointX(random.nextDouble() * area.getLength());
                creature.setPointY(area.getWidth());
                break;
            case 3:
                creature.setPointX(area.getLength());
                creature.setPointY(random.nextDouble() * area.getWidth());
                break;
        }
    }

    @Override
    public List<PointObject> getPointObjects() {
        final List<PointObject> list = toPointObjectList(foodList);
        list.addAll(toPointObjectList(creatures));
        return list;
    }

    private <T extends PointObject> List<PointObject> toPointObjectList(List<T> list) {
        return list.stream().map(f -> (PointObject) f).collect(Collectors.toList());
    }
}
