package md.ts14ic.either;

public abstract class Either<L, R> {
    /**
     * Box an Either.Left value
     */
    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Box an Either.Right value
     */
    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Query whether this is Either.Left
     */
    public abstract boolean isLeft();

    /**
     * Get value of Either.Left or throw
     *
     * @throws java.util.NoSuchElementException when this is Either.Right
     */
    public abstract L getLeft();

    /**
     * Get value of Either.Left or alternative value
     *
     * @param or Alternative value
     */
    public abstract L getLeftOr(L or);

    /**
     * Get value of Either.Left or alternative value lazily provided by producer
     *
     * @param producer Alternative value lazy producer
     */
    public abstract L getLeftOrElse(Producer<L> producer);

    /**
     * Get value of Either.Left or throw
     */
    public abstract <X extends Throwable> L getLeftOrThrow(Producer<X> producer) throws X;

    /**
     * Query whether this is Either.Right
     */
    public abstract boolean isRight();

    /**
     * Get value of Either.Right or throw
     *
     * @throws java.util.NoSuchElementException when this is Either.Left
     */
    public abstract R getRight();

    /**
     * Get value of Either.Right or alternative value
     *
     * @param or Alternative value
     */
    public abstract R getRightOr(R or);

    /**
     * Get value of Either.Right or alternative value lazily provided by producer
     *
     * @param producer Alternative value lazy producer
     */
    public abstract R getRightOrElse(Producer<R> producer);

    /**
     * Get value of Either.Right or throw
     */
    public abstract <X extends Throwable> R getRightOrThrow(Producer<X> producer) throws X;

    /**
     * Apply action to Either.Left or Either.Right depending on the type
     */
    public final void apply(Action<L> leftAction, Action<R> rightAction) {
        applyLeft(leftAction);
        applyRight(rightAction);
    }

    /**
     * Apply action if this is Either.Left
     */
    public abstract void applyLeft(Action<L> action);

    /**
     * Apply action if this is Either.Right
     *
     * @param action
     */
    public abstract void applyRight(Action<R> action);

    /**
     * Transform this Either.Left or Either.Right value to other type depending on the type
     */
    public final <L2, R2> Either<L2, R2> map(Mapper<L, L2> leftMapper, Mapper<R, R2> rightMapper) {
        return mapRight(rightMapper).mapLeft(leftMapper);
    }

    /**
     * Transform this Either.Left value and type if this is Either.Left or just type if this is Either.Right
     */
    public abstract <L2> Either<L2, R> mapLeft(Mapper<L, L2> mapper);

    /**
     * Transform this Either.Right value and type if this is Either.Right or just type if this is Either.Left
     */
    public abstract <R2> Either<L, R2> mapRight(Mapper<R, R2> mapper);

    /**
     * Swap left and right values and types
     */
    public abstract Either<R, L> rotate();

    /**
     * Map either Either.Left or Either.Right to a common type and get the value
     */
    public abstract <O> O fold(Mapper<L, O> leftMapper, Mapper<R, O> rightMapper);

    /**
     * Transform this Either.Left value and type if this is Either.Left or just type if this is Either.Right.
     * <p>
     * This version uses Either return type and change this type from left to type
     */
    public abstract <L2> Either<L2, R> flatMapLeft(Mapper<L, Either<L2, R>> mapper);

    /**
     * Transform this Either.Right value and type if this is Either.Right or just type if this is Either.Left
     * <p>
     * This version uses Either return type and change this type from right to left
     */
    public abstract <R2> Either<L, R2> flatMapRight(Mapper<R, Either<L, R2>> mapper);

    /**
     * Get stored object to string representation
     */
    public abstract String toString();

    /**
     * Get an "Either.Left{value}" or "Either.Right{value}" string representation for debugging
     */
    public abstract String toDebugString();

    public interface Producer<V> {
        V produce();
    }

    public interface Mapper<From, To> {
        To map(From from);
    }

    public interface Action<T> {
        void apply(T t);
    }
}

