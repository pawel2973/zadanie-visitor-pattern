package org.block;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.visitor.Visitor;
import org.visitor.Visitable;
import org.wall.Block;
import org.wall.CompositeBlock;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomCompositeBlock implements CompositeBlock, Visitable {
    private String color;
    private String material;
    private List<Block> blocks;

    public CustomCompositeBlock(List<Block> blocks) {
        this.blocks = blocks;
    }

    public CustomCompositeBlock(String color, String material) {
        this.color = color;
        this.material = material;
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }

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
