package ru.dsid.simulation.mutation.service.impl;

import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;

public class CreatureServiceImpl implements CreatureService {
    @Override
    public void calculateEnergy(MutationCreature creature, double time) {
        creature.setEnergy(creature.getEnergy() - calculateLoss(creature, time));
    }

    private double calculateLoss(MutationCreature creature, double time) {
        return (MutationConstants.SIZE_MASS_RATE * Math.pow(creature.getSize(), 3) *
                Math.pow(creature.getSpeed(), 2) +
                MutationConstants.ENERGY_RANGE_RATE * creature.getRange()) * time;
    }

    @Override
    public void calculateSpeed(MutationCreature creature) {
        creature.setSpeed(2 * MutationConstants.CREATURE_ENERGY /
                (MutationConstants.SIZE_MASS_RATE * Math.pow(creature.getSize(), 3)));
    }
}
