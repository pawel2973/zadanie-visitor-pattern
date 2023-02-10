package org.wall;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.visitor.BlockVisitorImpl;
import org.visitor.Visitor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Wall implements Structure {
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(@NonNull String color) {
        Visitor visitor = new BlockVisitorImpl();

        return blocks.stream()
                .flatMap(block -> block.accept(visitor))
                .filter(block -> {
                    if (block.getColor() == null) {
                        return false;
                    }
                    return block.getColor().equalsIgnoreCase(color);
                })
                .findFirst();
    }

    public List<Block> findBlocksByMaterial(@NonNull String material) {
        Visitor visitor = new BlockVisitorImpl();

        return blocks.stream()
                .flatMap(block -> block.accept(visitor))
                .filter(block -> {
                    if (block.getMaterial() == null) {
                        return false;
                    }
                    return block.getMaterial().equalsIgnoreCase(material);
                })
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        Visitor visitor = new BlockVisitorImpl();

        return blocks.stream()
                .flatMap(block -> block.accept(visitor))
                .mapToInt(block -> 1)
                .sum();
    }
}