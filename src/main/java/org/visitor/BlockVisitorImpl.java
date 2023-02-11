package org.visitor;

import org.wall.Block;
import org.wall.CompositeBlock;

import java.util.List;
import java.util.stream.Stream;

public class BlockVisitorImpl implements Visitor {

    @Override
    public Stream<Block> visit(Block block) {
        return Stream.of(block);
    }

    @Override
    public Stream<Block> visit(CompositeBlock block) {
        List<Block> childBlocks = block.getBlocks();

        if (childBlocks == null) {
            return Stream.of(block);
        }

        return Stream.concat(
                Stream.of(block),
                childBlocks.stream()
                        .peek(x-> System.out.println(this))
                        .flatMap(cBlock -> cBlock.accept(this)));
    }
}


