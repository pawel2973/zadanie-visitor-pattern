package org.visitor;

import org.wall.Block;

import java.util.stream.Stream;

public interface ItemElement {

    Stream<Block> accept(Visitor visitor);
}
