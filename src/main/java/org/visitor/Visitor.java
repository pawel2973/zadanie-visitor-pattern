package org.visitor;

import org.wall.Block;

import java.util.stream.Stream;

public interface Visitor {

    Stream<Block> visit(Block block);
}
