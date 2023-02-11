package org.visitor;

import org.wall.Block;

import java.util.stream.Stream;

public interface Visitable {

    Stream<Block> accept(Visitor visitor);
}
