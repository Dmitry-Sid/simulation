package ru.dsid.simulation.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MovingObject extends PointObject {
    private volatile double speed;
    private volatile Point destinationPoint;
}
