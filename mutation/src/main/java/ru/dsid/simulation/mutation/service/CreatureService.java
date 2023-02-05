package ru.dsid.simulation.mutation.service;

import ru.dsid.simulation.mutation.pojo.MutationCreature;

public interface CreatureService {
    void calculateEnergy(MutationCreature creature, double time);

    boolean isAlive(MutationCreature creature);

    void calculateSpeed(MutationCreature mutationCreature);
}
