package md.ts14ic.either;

import java.util.NoSuchElementException;
import java.util.Objects;

class Left<L, R> extends Either<L, R> {
    private final L value;

    Left(L value) {
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public L getLeft() {
        return value;
    }

    @Override
    public L getLeftOr(L or) {
        return getLeft();
    }

    @Override
    public L getLeftOrElse(Producer<L> producer) {
        return getLeft();
    }

    @Override
    public <X extends Throwable> L getLeftOrThrow(Producer<X> producer) throws X {
        return getLeft();
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public R getRight() {
        throw new NoSuchElementException();
    }

    @Override
    public R getRightOr(R or) {
        return or;
    }

    @Override
    public R getRightOrElse(Producer<R> producer) {
        return producer.produce();
    }

    @Override
    public <X extends Throwable> R getRightOrThrow(Producer<X> producer) throws X {
        throw producer.produce();
    }

    @Override
    public void applyLeft(Action<L> action) {
        action.apply(value);
    }

    @Override
    public void applyRight(Action<R> action) {
        // Do nothing. Not instance of Right.
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R2> Either<L, R2> mapRight(Mapper<R, R2> mapper) {
        return (Either<L, R2>) this;
    }

    @Override
    public <L2> Either<L2, R> mapLeft(Mapper<L, L2> mapper) {
        return Either.left(mapper.map(value));
    }

    @Override
    public Either<R, L> rotate() {
        return Either.right(value);
    }

    @Override
    public <O> O fold(Mapper<L, O> leftMapper, Mapper<R, O> rightMapper) {
        return leftMapper.map(value);
    }

    @Override
    public <L2> Either<L2, R> flatMapLeft(Mapper<L, Either<L2, R>> mapper) {
        return mapper.map(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R2> Either<L, R2> flatMapRight(Mapper<R, Either<L, R2>> mapper) {
        return (Either<L, R2>) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Left<?, ?> that = (Left<?, ?>) o;
        return value != null
                ? value.equals(that.value)
                : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = value != null
                ? value.hashCode()
                : 0;
        return 31 * result + 1;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toDebugString() {
        return "Either.Left{" + value + "}";
    }
}
