package ru.dsid.simulation.mutation.service.impl;

import org.junit.jupiter.api.Test;
import ru.dsid.simulation.core.service.ReplicationService;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.MutationService;
import ru.dsid.simulation.pojo.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MutationReplicationServiceTest {

    @Test
    public void copyTest() {
        final MutationService mutationService = mock(MutationService.class);
        final ReplicationService<MutationCreature> replicationService = new MutationReplicationService(mutationService);
        final MutationCreature creature = new MutationCreature();
        creature.setSize(10);
        creature.setSpeed(100);
        creature.setRange(2.0);
        creature.setPoint(new Point(5.0, 5.0));
        final MutationCreature copy = replicationService.copy(creature);
        assertEquals(creature.getSize(), copy.getSize());
        assertEquals(creature.getSpeed(), copy.getSpeed());
        assertEquals(creature.getRange(), copy.getRange());
        assertEquals(creature.getPoint(), copy.getPoint());
        assertTrue(copy.isFed());
        verify(mutationService).makeMutation(any(MutationCreature.class));
    }

}