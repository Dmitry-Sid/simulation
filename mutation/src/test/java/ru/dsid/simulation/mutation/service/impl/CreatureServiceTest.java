package ru.dsid.simulation.mutation.service.impl;

import org.junit.jupiter.api.Test;
import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureServiceTest {
    private final CreatureService creatureService = new CreatureServiceImpl();

    @Test
    void calculateEnergyTest() {
        final MutationCreature creature = new MutationCreature();
        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        creature.setRange(MutationConstants.DEFAULT_RANGE);
        creature.setSize(MutationConstants.DEFAULT_SIZE);
        creatureService.calculateSpeed(creature);
        creatureService.calculateEnergy(creature, 0);
        assertEquals(MutationConstants.CREATURE_ENERGY, creature.getEnergy());
        creatureService.calculateEnergy(creature, MutationConstants.DEFAULT_LIFE_TIME / 2);
        assertEquals(MutationConstants.CREATURE_ENERGY / 2, creature.getEnergy());
        creatureService.calculateEnergy(creature, MutationConstants.DEFAULT_LIFE_TIME / 2);
        assertEquals(0, creature.getEnergy());

        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        creatureService.calculateEnergy(creature, MutationConstants.DEFAULT_LIFE_TIME);
        assertEquals(0, creature.getEnergy());

        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        creature.setSize(MutationConstants.DEFAULT_SIZE * 2);
        creatureService.calculateSpeed(creature);
        creatureService.calculateEnergy(creature, MutationConstants.DEFAULT_LIFE_TIME / 2);
        assertEquals(MutationConstants.CREATURE_ENERGY / 2, creature.getEnergy(), 0.1);

        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        creature.setSize(MutationConstants.DEFAULT_SIZE);
        creature.setSpeed(MutationConstants.DEFAULT_SPEED * 2);
        creatureService.calculateEnergy(creature, MutationConstants.DEFAULT_LIFE_TIME / 10);
        assertEquals(3330.0, creature.getEnergy());

        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        creature.setSize(MutationConstants.DEFAULT_SIZE);
        creatureService.calculateSpeed(creature);
        creature.setRange(MutationConstants.DEFAULT_RANGE * 2);
        creatureService.calculateEnergy(creature, MutationConstants.DEFAULT_LIFE_TIME / 2);
        assertEquals(1875.0, creature.getEnergy());
    }

    @Test
    void isAliveTest() {
        final MutationCreature mutationCreature = new MutationCreature();
        mutationCreature.setEnergy(100);
        assertTrue(creatureService.isAlive(mutationCreature));
        mutationCreature.setEnergy(0);
        assertFalse(creatureService.isAlive(mutationCreature));
        mutationCreature.setFed(true);
        assertTrue(creatureService.isAlive(mutationCreature));
    }

    @Test
    void calculateSpeedTest() {
        final MutationCreature creature = new MutationCreature();
        creature.setSize(MutationConstants.DEFAULT_SIZE);
        creatureService.calculateSpeed(creature);
        assertEquals(1, creature.getSpeed());
        creature.setSize(MutationConstants.DEFAULT_SIZE * 2);
        creatureService.calculateSpeed(creature);
        assertEquals(0.353, creature.getSpeed(), 0.001);
    }
}