package ru.dsid.simulation.mutation.service.impl;

import ru.dsid.simulation.core.service.BaseReplicationService;
import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;

public class MutationReplicationService extends BaseReplicationService<MutationCreature> {

    @Override
    protected MutationCreature createCreature() {
        final MutationCreature creature = new MutationCreature();
        creature.setEnergy(MutationConstants.CREATURE_ENERGY);
        return creature;
    }

    @Override
    protected void fill(MutationCreature creature, MutationCreature copy) {
        copy.setSize(creature.getSize());
        copy.setRange(creature.getRange());
        copy.setSpeed(creature.getSpeed());
    }
}
