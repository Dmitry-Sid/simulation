package ru.dsid.simulation.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PointObject extends SizedObject {
    private volatile double pointX;
    private volatile double pointY;
}
