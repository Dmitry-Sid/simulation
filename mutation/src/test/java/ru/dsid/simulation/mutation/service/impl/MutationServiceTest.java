package ru.dsid.simulation.mutation.service.impl;

import org.junit.jupiter.api.Test;
import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.CreatureService;
import ru.dsid.simulation.mutation.service.MutationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MutationServiceTest {
    private final CreatureService creatureService = new CreatureServiceImpl();
    private final MutationService mutationService = new MutationServiceImpl(creatureService);

    @Test
    void makeMutationTest() {
        int iteration = 0;
        final boolean[] flags = {false, false, false};
        while (true) {
            final MutationCreature creature = createCreature();
            mutationService.makeMutation(creature);
            if (MutationConstants.DEFAULT_SIZE != creature.getSize()) {
                assertSizeChanged(creature);
                flags[0] = true;
            } else if (MutationConstants.DEFAULT_SPEED != creature.getSpeed()) {
                assertSpeedChanged(creature);
                flags[1] = true;
            } else if (MutationConstants.DEFAULT_RANGE != creature.getRange()) {
                assertRangeChanged(creature);
                flags[2] = true;
            }
            if (flags[0] && flags[1] && flags[2]) {
                break;
            }
            iteration++;
        }
        System.out.println("iteration: " + iteration);
    }

    private void assertSizeChanged(MutationCreature creature) {
        final int flag = MutationConstants.DEFAULT_SIZE < creature.getSize() ? 1 : -1;
        assertEquals(MutationConstants.DEFAULT_SIZE * (1 + flag * 0.05), creature.getSize());
        final MutationCreature expected = createCreature();
        expected.setSize(creature.getSize());
        creatureService.calculateSpeed(expected);
        assertEquals(expected.getSpeed(), creature.getSpeed());
    }

    private void assertSpeedChanged(MutationCreature creature) {
        final int flag = MutationConstants.DEFAULT_SPEED < creature.getSpeed() ? 1 : -1;
        assertEquals(MutationConstants.DEFAULT_SPEED * (1 + flag * 0.1), creature.getSpeed());
    }

    private void assertRangeChanged(MutationCreature creature) {
        final int flag = MutationConstants.DEFAULT_RANGE < creature.getRange() ? 1 : -1;
        assertEquals(MutationConstants.DEFAULT_RANGE * (1 + flag * 0.2), creature.getRange());
    }

    private MutationCreature createCreature() {
        final MutationCreature creature = new MutationCreature();
        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        creature.setRange(MutationConstants.DEFAULT_RANGE);
        creature.setSize(MutationConstants.DEFAULT_SIZE);
        creatureService.calculateSpeed(creature);
        return creature;
    }
}