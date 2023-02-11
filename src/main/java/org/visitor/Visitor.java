package org.visitor;

import org.wall.Block;
import org.wall.CompositeBlock;

import java.util.stream.Stream;

public interface Visitor {

    Stream<Block> visit(Block block);
    Stream<Block> visit(CompositeBlock block);
}
