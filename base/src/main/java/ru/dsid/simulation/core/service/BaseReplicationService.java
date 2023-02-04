package ru.dsid.simulation.core.service;

import ru.dsid.simulation.pojo.Creature;

public abstract class BaseReplicationService<T extends Creature> implements ReplicationService<T> {

    @Override
    public T copy(T creature) {
        final T copy = createCreature();
        fill(creature, copy);
        return copy;
    }

    protected abstract T createCreature();

    protected abstract void fill(T creature, T copy);
}
