package md.ts14ic.either;

import java.util.NoSuchElementException;

class Right<L, R> extends Either<L, R> {
    private final R value;

    Right(R value) {
        this.value = value;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public L getLeft() {
        throw new NoSuchElementException();
    }

    @Override
    public L getLeftOr(L or) {
        return or;
    }

    @Override
    public L getLeftOrElse(Producer<L> producer) {
        return producer.produce();
    }

    @Override
    public <X extends Throwable> L getLeftOrThrow(Producer<X> producer) throws X {
        throw producer.produce();
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public R getRightOr(R or) {
        return getRight();
    }

    @Override
    public R getRightOrElse(Producer<R> producer) {
        return value;
    }

    @Override
    public <X extends Throwable> R getRightOrThrow(Producer<X> producer) throws X {
        return value;
    }


    @Override
    public R getRight() {
        return value;
    }

    @Override
    public void applyLeft(Action<L> action) {
        // Do nothing. Not instance of Left.
    }

    @Override
    public void applyRight(Action<R> action) {
        action.apply(value);
    }

    @Override
    public <R2> Either<L, R2> mapRight(Mapper<R, R2> mapper) {
        return Either.right(mapper.map(value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L2> Either<L2, R> mapLeft(Mapper<L, L2> mapper) {
        return (Either<L2, R>) this;
    }

    @Override
    public Either<R, L> rotate() {
        return Either.left(value);
    }

    @Override
    public <O> O fold(Mapper<L, O> leftMapper, Mapper<R, O> rightMapper) {
        return rightMapper.map(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <L2> Either<L2, R> flatMapLeft(Mapper<L, Either<L2, R>> mapper) {
        return (Either<L2, R>) this;
    }

    @Override
    public <R2> Either<L, R2> flatMapRight(Mapper<R, Either<L, R2>> mapper) {
        return mapper.map(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toDebugString() {
        return "Either.Right{" + value + "}";
    }
}
