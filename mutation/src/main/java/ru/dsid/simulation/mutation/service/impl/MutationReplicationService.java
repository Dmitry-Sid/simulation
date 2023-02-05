package ru.dsid.simulation.mutation.service.impl;

import ru.dsid.simulation.core.service.BaseReplicationService;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.MutationService;

public class MutationReplicationService extends BaseReplicationService<MutationCreature> {
    private final MutationService mutationService;

    public MutationReplicationService(MutationService mutationService) {
        this.mutationService = mutationService;
    }

    @Override
    protected MutationCreature createCreature() {
        return new MutationCreature();
    }

    @Override
    protected void fill(MutationCreature creature, MutationCreature copy) {
        copy.setSize(creature.getSize());
        copy.setRange(creature.getRange());
        copy.setSpeed(creature.getSpeed());
        copy.setFed(true);
        copy.setPoint(creature.getPoint());
        mutationService.makeMutation(copy);
    }
}
