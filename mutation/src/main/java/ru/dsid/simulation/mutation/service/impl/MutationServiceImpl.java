package ru.dsid.simulation.mutation.service.impl;

import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;
import ru.dsid.simulation.mutation.service.MutationService;

import java.util.Random;

public class MutationServiceImpl implements MutationService {
    private final CreatureService creatureService;

    public MutationServiceImpl(CreatureService creatureService) {
        this.creatureService = creatureService;
    }

    @Override
    public void makeMutation(MutationCreature creature) {
        final Random random = new Random();
        if (!random.nextBoolean()) {
            return;
        }
        final int sign = random.nextBoolean() ? 1 : -1;
        switch (random.nextInt(3)) {
            case 0:
                changeSize(creature, sign);
                break;
            case 1:
                changeSpeed(creature, sign);
                break;
            case 2:
                changeRange(creature, sign);
                break;
        }
    }

    private void changeSize(MutationCreature creature, int sign) {
        creature.setSize(creature.getSize() * (1 + sign * 0.05));
        creatureService.calculateSpeed(creature);
    }

    private void changeSpeed(MutationCreature creature, int sign) {
        creature.setSpeed(creature.getSpeed() * (1 + sign * 0.1));
    }

    private void changeRange(MutationCreature creature, int sign) {
        creature.setRange(creature.getRange() * (1 + sign * 0.2));
    }
}
