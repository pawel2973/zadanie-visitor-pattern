import org.block.CustomBlock;
import org.block.CustomCompositeBlock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.wall.Block;
import org.wall.WallRecursive;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WallRecursiveTest {
    private static Stream<Arguments> countData() {

        return Stream.of(
                Arguments.of(List.of(
                        new CustomBlock("red", "wood")
                ), 1),

                Arguments.of(List.of(
                        new CustomBlock("blue", "iron"),
                        new CustomBlock("green", "concrete")
                ), 2),

                Arguments.of(List.of(
                        new CustomCompositeBlock(List.of(
                                new CustomBlock("red", "wood")))
                ), 2),

                Arguments.of(List.of(
                        new CustomCompositeBlock(List.of(
                                new CustomBlock("red", "wood"))),
                        new CustomBlock("blue", "iron")
                ), 3),

                Arguments.of(List.of(
                        new CustomBlock("blue", "iron"),
                        new CustomCompositeBlock(List.of(
                                new CustomBlock("red", "wood"))),
                        new CustomBlock("blue", "iron")
                ), 4),

                Arguments.of(List.of(), 0),

                // Single CompositeBlock with no Blocks inside (getBlocks() returns null)
                Arguments.of(List.of(
                        new CustomCompositeBlock("red", "wood")
                ), 1),

                // Multiple CompositeBlock with no Blocks inside (getBlocks() returns null)
                Arguments.of(List.of(
                        new CustomCompositeBlock("red", "steel"),
                        new CustomCompositeBlock("magenta", "gold"),
                        new CustomCompositeBlock("grey", "silver")
                ), 3),

                // Multiple CompositeBlock with no Blocks inside (getBlocks() returns null)
                // or with Blocks inside (getBlocks() returns List<Block>)
                Arguments.of(List.of(
                        new CustomCompositeBlock("red", "steel"),
                        new CustomCompositeBlock("magenta", "gold", List.of(
                                new CustomBlock("red", "wood"),
                                new CustomBlock("blue", "iron")
                        )),
                        new CustomCompositeBlock("grey", "silver")
                ), 5),

                // All cases mixed
                Arguments.of(List.of(
                        new CustomCompositeBlock(List.of(
                                new CustomBlock("black", "graphite"),
                                new CustomBlock("black", "graphite"),
                                new CustomCompositeBlock(List.of(
                                        new CustomCompositeBlock(List.of(
                                                new CustomBlock("orange", "clay"),
                                                new CustomCompositeBlock("violet", "steel"))),
                                        new CustomBlock("orange", "clay"))),
                                new CustomBlock("black", "graphite"))),
                        new CustomBlock("blue", "iron"),
                        new CustomCompositeBlock(List.of())
                ), 11)
        );
    }

    private static Stream<Arguments> findBlocksByMaterialData() {

        return Stream.of(
                Arguments.of("wood",
                        List.of(new CustomBlock("red", "wood")),
                        List.of(new CustomBlock("red", "wood"))),

                Arguments.of("marble",
                        List.of(new CustomBlock("red", "wood"),
                                new CustomCompositeBlock("beige", "marble"),
                                new CustomBlock("purple", "stone")
                        ),
                        List.of(new CustomCompositeBlock("beige", "marble"))),


                Arguments.of("graphite",
                        List.of(new CustomCompositeBlock(List.of(
                                        new CustomBlock("black", "graphite"),
                                        new CustomBlock("red", "graphite"),
                                        new CustomCompositeBlock(List.of(
                                                new CustomCompositeBlock(List.of(
                                                        new CustomBlock("orange", "clay"),
                                                        new CustomCompositeBlock("blue", "graphite"))),
                                                new CustomBlock("orange", "clay"))),
                                        new CustomBlock("black", "graphite"))),
                                new CustomBlock("blue", "iron"),
                                new CustomCompositeBlock(List.of())
                        ),
                        List.of(new CustomBlock("black", "graphite"),
                                new CustomBlock("red", "graphite"),
                                new CustomCompositeBlock("blue", "graphite"),
                                new CustomBlock("black", "graphite"))),

                Arguments.of("graphite",
                        List.of(new CustomBlock("white", "iron")),
                        List.of())
        );
    }

    private static Stream<Arguments> findBlockByColorData_resultOptional() {

        return Stream.of(
                Arguments.of("red",
                        List.of(new CustomBlock("red", "wood")),
                        Optional.of(new CustomBlock("red", "wood"))),

                Arguments.of("beige",
                        List.of(new CustomBlock("red", "wood"),
                                new CustomCompositeBlock("beige", "marble"),
                                new CustomBlock("purple", "stone")),
                        Optional.of(new CustomCompositeBlock("beige", "marble"))),

                Arguments.of("violet",
                        List.of(new CustomCompositeBlock(List.of(
                                        new CustomBlock("black", "graphite"),
                                        new CustomBlock("red", "graphite"),
                                        new CustomCompositeBlock(List.of(
                                                new CustomCompositeBlock(List.of(
                                                        new CustomBlock("orange", "clay"),
                                                        new CustomCompositeBlock("blue", "graphite"))),
                                                new CustomBlock("violet", "clay"))),
                                        new CustomBlock("black", "graphite"))),
                                new CustomBlock("green", "iron"),
                                new CustomCompositeBlock(List.of())
                        ),
                        Optional.of(new CustomBlock("violet", "clay"))),

                Arguments.of("black",
                        List.of(new CustomBlock("red", "wood"),
                                new CustomCompositeBlock("beige", "marble"),
                                new CustomBlock("purple", "stone")),
                        Optional.empty())
        );
    }

    private static Stream<Arguments> findBlockByColorData_resultBoolean() {

        return Stream.of(
                Arguments.of("red",
                        List.of(new CustomBlock("red", "wood"),
                                new CustomBlock("red", "wood")),
                        true),

                Arguments.of("beige",
                        List.of(new CustomBlock("red", "wood"),
                                new CustomCompositeBlock("beige", "marble"),
                                new CustomBlock("purple", "stone")),
                        true),

                Arguments.of("yellow",
                        List.of(new CustomCompositeBlock(List.of(
                                        new CustomBlock("black", "graphite"),
                                        new CustomBlock("red", "graphite"),
                                        new CustomCompositeBlock(List.of(
                                                new CustomCompositeBlock(List.of(
                                                        new CustomBlock("orange", "clay"),
                                                        new CustomCompositeBlock("blue", "graphite"))),
                                                new CustomBlock("violet", "clay"))),
                                        new CustomBlock("black", "graphite"))),
                                new CustomBlock("green", "iron"),
                                new CustomCompositeBlock(List.of())
                        ),
                        false)
        );
    }

    @ParameterizedTest
    @MethodSource("countData")
    public void count_withDataFromMethod_resultFromData(List<Block> blocks,
                                                        int expectedResult) {
        // given
        WallRecursive wall = new WallRecursive(blocks);

        // when
        int result = wall.count();

        // then
        assertEquals(expectedResult, result);
    }


    @ParameterizedTest
    @MethodSource("findBlocksByMaterialData")
    public void findBlocksByMaterial_withDataFromMethod_resultFromData(String material,
                                                                       List<Block> blocks,
                                                                       List<Block> expectedResult) {
        // given
        WallRecursive wall = new WallRecursive(blocks);

        // when
        List<Block> result = wall.findBlocksByMaterial(material);

        // then
        assertIterableEquals(expectedResult, result);
    }


    @ParameterizedTest
    @MethodSource("findBlockByColorData_resultOptional")
    public void findBlockByColor_withDataFromMethod_resultFromData_expectedOptional(String color,
                                                                                    List<Block> blocks,
                                                                                    Optional<Block> expectedResult) {
        // given
        WallRecursive wall = new WallRecursive(blocks);

        // when
        Optional<Block> result = wall.findBlockByColor(color);

        // then
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("findBlockByColorData_resultBoolean")
    public void findBlockByColor_withDataFromMethod_resultFromData_expectedBoolean(String color,
                                                                                   List<Block> blocks,
                                                                                   boolean expectedResult) {
        // given
        WallRecursive wall = new WallRecursive(blocks);

        // when
        boolean result = wall.findBlockByColor(color).isPresent();

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    public void findByColor_withNullColor_throwsNullPointerException() {
        // given
        WallRecursive wall = new WallRecursive(List.of(new CustomBlock("red", "wood")));

        // when

        // then
        assertThrows(NullPointerException.class, () -> wall.findBlockByColor(null));
    }

    @Test
    public void findByMaterial_withNullMaterial_throwsNullPointerException() {
        // given
        WallRecursive wall = new WallRecursive(List.of(new CustomBlock("red", "wood")));

        // when

        // then
        assertThrows(NullPointerException.class, () -> wall.findBlocksByMaterial(null));
    }


}
