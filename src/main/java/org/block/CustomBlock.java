package org.block;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.visitor.Visitor;
import org.visitor.ItemElement;
import org.wall.Block;

import java.util.stream.Stream;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomBlock implements Block, ItemElement {
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

    @Override
    public Stream<Block> accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
