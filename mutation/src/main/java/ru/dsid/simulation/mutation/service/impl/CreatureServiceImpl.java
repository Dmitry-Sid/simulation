package ru.dsid.simulation.mutation.service.impl;

import org.springframework.stereotype.Service;
import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;

@Service
public class CreatureServiceImpl implements CreatureService {
    @Override
    public void calculateEnergy(MutationCreature creature, double time) {
        creature.setEnergy(Math.max(creature.getEnergy() - calculateLoss(creature, time), 0.0));
    }

    @Override
    public boolean isAlive(MutationCreature creature) {
        return creature.isFed() || creature.getEnergy() > 0;
    }

    private double calculateLoss(MutationCreature creature, double time) {
        return ((calculateMass(creature) *
                Math.pow(creature.getSpeed(), 2)) / 2 +
                MutationConstants.ENERGY_RANGE_RATE * creature.getRange()) * time;
    }

    @Override
    public void calculateSpeed(MutationCreature creature) {
        creature.setSpeed(Math.sqrt(2 * MutationConstants.CREATURE_POWER / calculateMass(creature)));
    }

    private double calculateMass(MutationCreature creature) {
        return MutationConstants.DENSITY * Math.pow(creature.getSize(), 3);
    }
}
