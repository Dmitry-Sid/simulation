package ru.dsid.simulation.core.service;

import ru.dsid.simulation.pojo.Creature;

public interface ReplicationService<T extends Creature> {
    T copy(T creature);
}
