package org.visitor;

import org.wall.Block;
import org.wall.CompositeBlock;

import java.util.List;
import java.util.stream.Stream;

public class BlockVisitorImpl implements Visitor {

    @Override
    public Stream<Block> visit(Block block) {

        if (block instanceof CompositeBlock) {
            CompositeBlock compositeBlock = (CompositeBlock) block;
            List<Block> childBlocks = compositeBlock.getBlocks();

            if (childBlocks == null) {
                return Stream.of(compositeBlock);
            }

            return Stream.concat(
                    Stream.of(compositeBlock),
                    childBlocks.stream()
                            .flatMap(this::visit));
        }

        return Stream.of(block);
    }
}
