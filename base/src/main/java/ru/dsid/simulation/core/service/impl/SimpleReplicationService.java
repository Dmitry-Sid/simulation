package ru.dsid.simulation.core.service.impl;

import org.springframework.stereotype.Service;
import ru.dsid.simulation.core.service.BaseReplicationService;
import ru.dsid.simulation.pojo.Creature;

@Service
public class SimpleReplicationService extends BaseReplicationService<Creature> {
    @Override
    protected Creature createCreature() {
        return new Creature();
    }

    @Override
    protected void fill(Creature creature, Creature copy) {
        copy.setSize(creature.getSize());
        copy.setSpeed(creature.getSpeed());
    }
}
