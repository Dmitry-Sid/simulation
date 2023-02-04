package ru.dsid.simulation.mutation.service;

import ru.dsid.simulation.mutation.pojo.MutationCreature;

public interface CreatureService {
    void calculateEnergy(MutationCreature creature, double time);

    void calculateSpeed(MutationCreature mutationCreature);
}
