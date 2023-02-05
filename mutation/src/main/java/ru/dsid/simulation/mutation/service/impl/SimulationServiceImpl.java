package ru.dsid.simulation.mutation.service.impl;

import ru.dsid.simulation.core.Utils;
import ru.dsid.simulation.core.service.BaseSimulationService;
import ru.dsid.simulation.core.service.ReplicationService;
import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;
import ru.dsid.simulation.mutation.service.MutationSimulationService;
import ru.dsid.simulation.pojo.Area;
import ru.dsid.simulation.pojo.Food;
import ru.dsid.simulation.pojo.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimulationServiceImpl extends BaseSimulationService implements MutationSimulationService {
    private static final int INITIAL_CREATURE_COUNT = 50;
    private static final int FOOD_COUNT = 50;
    private static final long TIME_SLEEP = 100;
    private static final double MIN_DISTANCE = 0.1;

    private final Area area = new Area(100, 100);
    private final Random random = new Random();
    private final List<Food> foodList = new CopyOnWriteArrayList<>();
    private final List<MutationCreature> creatures = new CopyOnWriteArrayList<>();

    private final CreatureService creatureService;
    private final ReplicationService<MutationCreature> replicationService;

    public SimulationServiceImpl(CreatureService creatureService, ReplicationService<MutationCreature> replicationService) {
        this.creatureService = creatureService;
        this.replicationService = replicationService;
    }

    @Override
    protected void init() {
        generateCreatures();
    }

    private void generateCreatures() {
        for (int i = 0; i < INITIAL_CREATURE_COUNT; i++) {
            final MutationCreature creature = new MutationCreature();
            creature.setSize(MutationConstants.DEFAULT_SIZE);
            creature.setRange(MutationConstants.DEFAULT_RANGE);
            creatureService.calculateSpeed(creature);
            creatures.add(creature);
        }
    }

    @Override
    protected void runIteration() {
        initIteration();
        while (!foodList.isEmpty() || !creatures.isEmpty() || !isAllCreaturesFed() || !stop) {
            checkPause();
            for (Iterator<MutationCreature> iterator = creatures.iterator(); iterator.hasNext(); ) {
                final MutationCreature creature = iterator.next();
                feed(creature);
                if (creature.isFed()) {
                    continue;
                }
                if (!creatureService.isAlive(creature)) {
                    iterator.remove();
                    continue;
                }
                setDestination(creature);
                try {
                    Thread.sleep(TIME_SLEEP);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                move(creature, TIME_SLEEP);
            }
        }
        finishIteration();
    }

    private synchronized void checkPause() {
        while (pause) {
            if (stop) {
                return;
            }
            try {
                wait(10 * TIME_SLEEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initIteration() {
        generateFood();
        creatures.forEach(creature -> {
            creature.setEnergy(MutationConstants.CREATURE_ENERGY);
            creature.setFed(false);
            creature.setPoint(generateRandomBoundaryPoint());
            creature.setDestinationPoint(generateRandomPoint());
        });
    }

    private boolean isAllCreaturesFed() {
        return creatures.stream().allMatch(MutationCreature::isFed);
    }

    private void setDestination(MutationCreature creature) {
        if (creature.getTarget() != null) {
            if (!foodList.contains(creature.getTarget())) {
                creature.setTarget(null);
                searchNearest(creature);
                if (creature.getTarget() == null) {
                    creature.setDestinationPoint(generateRandomPoint());
                }
            }
        } else {
            searchNearest(creature);
            if (creature.getTarget() == null && Utils.calculateDistance(creature.getPoint(), creature.getDestinationPoint()) <= MIN_DISTANCE) {
                creature.setDestinationPoint(generateRandomPoint());
            }
        }
    }

    private void searchNearest(MutationCreature creature) {
        final Food food = findNearestFood(creature);
        if (food != null) {
            creature.setTarget(food);
            creature.setDestinationPoint(food.getPoint());
        }
    }

    private Food findNearestFood(MutationCreature creature) {
        return foodList.stream().filter(food -> isInRange(creature, food)).findAny().orElse(null);
    }

    private boolean isInRange(MutationCreature creature, Food food) {
        return Utils.calculateDistance(creature.getPoint(), food.getPoint()) <= creature.getRange();
    }

    private void feed(MutationCreature creature) {
        if (creature.isFed() || creature.getTarget() == null) {
            return;
        }
        if (canTakeFood(creature)) {
            foodList.remove(creature.getTarget());
            creature.setFed(true);
            creature.setTarget(null);
            creatures.add(replicationService.copy(creature));
        }
    }

    private boolean canTakeFood(MutationCreature creature) {
        return Utils.calculateDistance(Utils.calculatePoint(creature.getPoint(), creature.getSize() / 2,
                Utils.calculateAngle(creature.getPoint(), creature.getDestinationPoint())), creature.getTarget().getPoint()) <= MIN_DISTANCE;
    }

    private void move(MutationCreature creature, long time) {
        Utils.move(creature, time);
        creatureService.calculateEnergy(creature, time);
    }

    private void finishIteration() {
        foodList.clear();
    }

    private Point generateRandomBoundaryPoint() {
        final int boundary = random.nextInt(4);
        final Point point;
        switch (boundary) {
            case 0:
                point = new Point(random.nextDouble() * area.getLength(), 0);
                break;
            case 1:
                point = new Point(0, random.nextDouble() * area.getWidth());
                break;
            case 2:
                point = new Point(random.nextDouble() * area.getLength(), area.getWidth());
                break;
            case 3:
                point = new Point(area.getLength(), random.nextDouble() * area.getWidth());
                break;
            default:
                throw new RuntimeException("unknown boundary " + boundary);
        }
        return point;
    }

    private void generateFood() {
        for (int i = 0; i < FOOD_COUNT; i++) {
            final Food food = new Food();
            food.setPoint(generateRandomPoint());
            foodList.add(food);
        }
    }

    private Point generateRandomPoint() {
        return new Point(random.nextDouble() * area.getLength(),
                random.nextDouble() * area.getWidth());
    }

    @Override
    public List<MutationCreature> getCreatures() {
        return new ArrayList<>(creatures);
    }

    @Override
    public List<Food> getFood() {
        return new ArrayList<>(foodList);
    }

    @Override
    protected void clear() {
        foodList.clear();
        creatures.clear();
    }
}
