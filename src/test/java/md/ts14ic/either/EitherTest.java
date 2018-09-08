package md.ts14ic.either;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static junit.framework.TestCase.*;

public class EitherTest {
    @Test
    public void testLeftCreation() {
        Either<String, Integer> either = Either.left("Hello");

        assertTrue(either.isLeft());
        assertTrue(!either.isRight());
        assertEquals("Hello", either.toString());
        assertEquals("Either.Left{Hello}", either.toDebugString());
    }

    @Test
    public void testRightCreation() {
        Either<String, Integer> either = Either.right(200);

        assertTrue(!either.isLeft());
        assertTrue(either.isRight());
        assertEquals("200", either.toString());
        assertEquals("Either.Right{200}", either.toDebugString());
    }

    @Test
    public void whenLeft_getLeft_returns() {
        Either<String, Integer> either = Either.left("Hola");

        assertTrue(either.isLeft());
        assertEquals("Hola", either.getLeft());
    }

    @Test
    public void whenLeft_getLeftOr_returnsLeft() {
        Either<String, Integer> either = Either.left("Chao");

        assertEquals("Chao", either.getLeftOr("Noroc"));
    }

    @Test
    public void whenLeft_getLeftOrElse_returnsLeft() {
        Either<String, Integer> either = Either.left("Chao");

        assertEquals("Chao", either.getLeftOrElse(() -> "not used"));
    }

    @Test
    public void whenLeft_getLeftOrThrow_returnsLeft() {
        Either<String, Integer> either = Either.left("Hi");

        assertEquals("Hi", either.getLeftOrThrow(RuntimeException::new));
    }

    @Test
    public void whenLeft_getRight_throws() {
        Either<String, Integer> either = Either.left("Hello");

        assertTrue(!either.isRight());
        try {
            either.getRight();
            fail("Can't get right from a left");
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test
    public void whenLeft_getRightOr_returnsOr() {
        Either<String, Integer> either = Either.left("Hello");

        assertEquals(Integer.valueOf(10), either.getRightOr(10));
    }

    @Test
    public void whenLeft_getRightOrElse_returnsElse() {
        Either<String, Integer> either = Either.left("Hello");

        assertEquals(Integer.valueOf(40), either.getRightOrElse(() -> 40));
    }

    @Test
    public void whenLeft_getRightOrThrow_throws() {
        Either<String, Integer> either = Either.left("Hello");

        try {
            either.getRightOrThrow(RuntimeException::new);
            fail("A runtime exception is thrown");
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    public void whenLeft_applyLeft_consumes() {
        List<String> sideEffects = new ArrayList<>();
        Either<String, Integer> either = Either.left("Hello");

        either.applyLeft(sideEffects::add);

        assertEquals("Hello", sideEffects.get(0));
    }

    @Test
    public void whenLeft_applyRight_doesNothing() {
        List<Integer> sideEffects = new ArrayList<>();
        Either<String, Integer> either = Either.left("Hello");

        either.applyRight(sideEffects::add);

        assertEquals(0, sideEffects.size());
    }

    @Test
    public void whenLeft_apply_consumesLeft() {
        List<String> leftSideEffects = new ArrayList<>();
        List<Integer> rightSideEffects = new ArrayList<>();
        Either<String, Integer> either = Either.left("Hello");

        either.apply(leftSideEffects::add, rightSideEffects::add);

        assertEquals("Hello", leftSideEffects.get(0));
        assertEquals(0, rightSideEffects.size());
    }

    @Test
    public void whenLeft_mapLeft_changesLeft() {
        Either<String, Integer> either = Either.left("Hello");

        Either<Integer, Integer> out = either.mapLeft(String::length);

        assertEquals(Integer.valueOf(5), out.getLeft());
    }

    @Test
    public void whenLeft_mapRight_changesTypeOnly() {
        Either<String, Integer> either = Either.left("Hello");

        Either<String, String> out = either.mapRight(String::valueOf);

        assertEquals("Hello", out.getLeft());
    }

    @Test
    public void whenLeft_map_changesLeftAndType() {
        Either<String, Integer> either = Either.left("Hello");

        Either<Integer, String> out = either.map(String::length, String::valueOf);

        assertEquals(Integer.valueOf(5), out.getLeft());
    }

    @Test
    public void whenLeft_rotate_swapsLeft() {
        Either<String, Integer> either = Either.left("Hello");

        Either<Integer, String> out = either.rotate();

        assertTrue(out.isRight());
        assertEquals("Hello", out.getRight());
    }

    @Test
    public void whenLeft_fold_foldsLeft() {
        Either<String, Integer> either = Either.left("Hello");

        int folded = either.fold(String::length, integer -> integer);

        assertEquals(5, folded);
    }

    @Test
    public void whenLeft_flatMapLeftWithLeft_changesLeft() {
        Either<String, Integer> either = Either.left("Hello");

        Either<Integer, Integer> out = either.flatMapLeft(s -> Either.left(s.length()));

        assertEquals(Integer.valueOf(5), out.getLeft());
    }

    @Test
    public void whenLeft_flatMapLeftWithRight_changesToRight() {
        Either<String, Integer> either = Either.left("Hello");

        Either<String, Integer> out = either.flatMapLeft(s -> Either.right(10));

        assertEquals(Integer.valueOf(10), out.getRight());
    }

    @Test
    public void whenLeft_flatMapRightWithLeft_changesNothing() {
        Either<String, Integer> either = Either.left("Hello");

        Either<String, Integer> out = either.flatMapRight(i -> Either.left(String.valueOf(i)));

        assertEquals("Hello", out.getLeft());
    }

    @Test
    public void whenLeft_flatMapRightWithRight_changesTypeOnly() {
        Either<String, Integer> either = Either.left("Hello");

        Either<String, String> out = either.flatMapRight(i -> Either.right(String.valueOf(i)));

        assertEquals("Hello", out.getLeft());
    }

    @Test
    public void whenRight_getLeft_throws() {
        Either<String, Integer> either = Either.right(23);

        try {
            either.getLeft();
            fail("Can't get right from left");
        } catch (NoSuchElementException ignored) { }
    }

    @Test
    public void whenRight_getLeftOr_returnsOr() {
        Either<String, Integer> either = Either.right(23);

        assertEquals("Noroc", either.getLeftOr("Noroc"));
    }

    @Test
    public void whenRight_getLeftOrElse_returnsElse() {
        Either<String, Integer> either = Either.right(23);

        assertEquals("Chao", either.getLeftOrElse(() -> "Chao"));
    }

    @Test
    public void whenRight_getLeftOrThrow_throws() {
        Either<String, Integer> either = Either.right(23);

        try {
            either.getLeftOrThrow(RuntimeException::new);
            fail("Should throw runtime exception");
        } catch (RuntimeException ignored) {}
    }

    @Test
    public void whenRight_getRight_returns() {
        Either<String, Integer> either = Either.right(23);

        assertEquals(Integer.valueOf(23), either.getRight());
    }

    @Test
    public void whenRight_getRightOr_returns() {
        Either<String, Integer> either = Either.right(23);

        assertEquals(Integer.valueOf(23), either.getRightOr(10));
    }

    @Test
    public void whenRight_getRightOrElse_returns() {
        Either<String, Integer> either = Either.right(23);

        assertEquals(Integer.valueOf(23), either.getRightOrElse(() -> 40));
    }

    @Test
    public void whenRight_getRightOrThrow_returns() {
        Either<String, Integer> either = Either.right(23);

        assertEquals(Integer.valueOf(23), either.getRightOrThrow(RuntimeException::new));
    }

    @Test
    public void whenRight_applyLeft_doesNothing() {
        List<String> sideEffects = new ArrayList<>();
        Either<String, Integer> either = Either.right(23);

        either.applyLeft(sideEffects::add);

        assertEquals(0, sideEffects.size());
    }

    @Test
    public void whenRight_applyRight_sideEffects() {
        List<Integer> sideEffects = new ArrayList<>();
        Either<String, Integer> either = Either.right(23);

        either.applyRight(sideEffects::add);

        assertEquals(Integer.valueOf(23), sideEffects.get(0));
    }

    @Test
    public void whenRight_apply_consumesRight() {
        List<String> leftSideEffects = new ArrayList<>();
        List<Integer> rightSideEffects = new ArrayList<>();
        Either<String, Integer> either = Either.right(23);

        either.apply(leftSideEffects::add, rightSideEffects::add);

        assertEquals(0, leftSideEffects.size());
        assertEquals(Integer.valueOf(23), rightSideEffects.get(0));
    }

    @Test
    public void whenRight_mapLeft_changesNothing() {
        Either<String, Integer> either = Either.right(23);

        Either<Integer, Integer> out = either.mapLeft(String::length);

        assertEquals(Integer.valueOf(23), out.getRight());
    }

    @Test
    public void whenRight_mapRight_changesRight() {
        Either<String, Integer> either = Either.right(23);

        Either<String, String> out = either.mapRight(String::valueOf);

        assertEquals("23", out.getRight());
    }

    @Test
    public void whenRight_map_changesRightAndType() {
        Either<String, Integer> either = Either.right(23);

        Either<Integer, String> out = either.map(String::length, String::valueOf);

        assertEquals("23", out.getRight());
    }

    @Test
    public void whenRight_rotate_swapsRight() {
        Either<String, Integer> either = Either.right(23);

        Either<Integer, String> out = either.rotate();

        assertTrue(out.isLeft());
        assertEquals(Integer.valueOf(23), out.getLeft());
    }

    @Test
    public void whenRight_fold_foldsRight() {
        Either<String, Integer> either = Either.right(23);

        int folded = either.fold(String::length, integer -> integer);

        assertEquals(23, folded);
    }

    @Test
    public void whenRight_flatMapLeftWithLeft_changesTypeOnly() {
        Either<String, Integer> either = Either.right(23);

        Either<Integer, Integer> out = either.flatMapLeft(s -> Either.left(s.length()));

        assertEquals(Integer.valueOf(23), out.getRight());
    }

    @Test
    public void whenRight_flatMapLeftWithRight_changesNothing() {
        Either<String, Integer> either = Either.right(23);

        Either<String, Integer> out = either.flatMapLeft(s -> Either.right(10));

        assertEquals(Integer.valueOf(23), out.getRight());
    }

    @Test
    public void whenRight_flatMapRightWithLeft_changesToLeft() {
        Either<String, Integer> either = Either.right(23);

        Either<String, Integer> out = either.flatMapRight(i -> Either.left(String.valueOf(i)));

        assertEquals("23", out.getLeft());
    }

    @Test
    public void whenRight_flatMapRightWithRight_changesRight() {
        Either<String, Integer> either = Either.right(23);

        Either<String, String> out = either.flatMapRight(i -> Either.right(String.valueOf(i)));

        assertEquals("23", out.getRight());
    }
}
