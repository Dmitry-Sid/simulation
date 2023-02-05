package ru.dsid.simulation.mutation.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dsid.simulation.pojo.Creature;
import ru.dsid.simulation.pojo.Food;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MutationCreature extends Creature {
    private volatile double range;
    private volatile double energy;
    private volatile boolean fed;
    private volatile Food target;
}
