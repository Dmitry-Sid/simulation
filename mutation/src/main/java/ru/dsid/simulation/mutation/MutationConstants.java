package ru.dsid.simulation.mutation;

public interface MutationConstants {
    double DEFAULT_RANGE = 5; // м
    double ENERGY_RANGE_RATE = 2; // дж/м * сек
    double DEFAULT_SIZE = 0.5; //м
    double DENSITY = 1000; // кг/м3
    double DEFAULT_LIFE_TIME = 60; //сек
    double DEFAULT_SPEED = 1; // м/с
    double CREATURE_POWER = DENSITY * Math.pow(DEFAULT_SIZE, 3) * DEFAULT_SPEED / 2; // вт
    double CREATURE_ENERGY = DEFAULT_LIFE_TIME * (CREATURE_POWER + DEFAULT_RANGE * ENERGY_RANGE_RATE); // дж
}
