package org.block;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wall.Block;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomBlock implements Block {
    private final String color;
    private final String material;

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getMaterial() {
        return material;
    }

}
