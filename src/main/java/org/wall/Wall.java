package org.wall;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class Wall implements Structure {
    private List<Block> blocks;

    private static Stream<Block> deepFlat(@NonNull Block block) {

        if (block instanceof CompositeBlock) {
            CompositeBlock compositeBlock = (CompositeBlock) block;
            List<Block> childBlocks = compositeBlock.getBlocks();

            if (childBlocks == null) {
                return Stream.of(compositeBlock);
            }

            return Stream.concat(
                    Stream.of(compositeBlock),
                    childBlocks.stream()
                            .flatMap(Wall::deepFlat));
        } else {
            return Stream.of(block);
        }
    }

    private static int deepCount(@NonNull Block block) {

        if (block instanceof CompositeBlock) {
            List<Block> childBlocks = ((CompositeBlock) block).getBlocks();

            if (childBlocks == null) {
                return 1;
            }

            return 1 + childBlocks.stream()
                    .mapToInt(Wall::deepCount)
                    .sum();
        } else {
            return 1;
        }
    }

    @Override
    public Optional<Block> findBlockByColor(@NonNull String color) {

        return blocks.stream()
                .flatMap(Wall::deepFlat)
                .filter(bl -> {
                    if (bl.getColor() == null) {
                        return false;
                    }
                    return bl.getColor().equalsIgnoreCase(color);
                })
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(@NonNull String material) {

        return blocks.stream()
                .flatMap(Wall::deepFlat)
                .filter(bl -> {
                    if (bl.getMaterial() == null) {
                        return false;
                    }
                    return bl.getMaterial().equalsIgnoreCase(material);
                })
                .collect(Collectors.toList());
    }

    @Override
    public int count() {

        return blocks.stream()
                .mapToInt(Wall::deepCount)
                .sum();
    }
}