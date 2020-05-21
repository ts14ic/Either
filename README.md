# Either
An `Either` monad backport.  
Doesn't have Java 8+ dependencies so can be used in Android dev.  
The implementation is opinionated (all methods require explicit right/left prefix).
It was also made to avoid bringing in a big library with a big learning surface in a specific project,
but instead is a small class whose whole documentation fits here.

It's like an `Optional`, but instead of `empty` you have a different value of a different type.  
Can be used both imperatively and via its fluent interface:    
* Either.left(value) - Box an Either.Left value
* Either.right(value) - Box an Either.Right value
* isLeft() - Query whether this is Either.Left
* getLeft() - Get the value of Either.Left or throw if it's Either.Right
* getLeftOr(default) - Get the value of Either.Left or a provided default
* getLeftOrElse(lazyDefault) - Get the value of Either.Left or a lazy provided default
* getLeftOrThrow(lazyException) - Get the value of Either.Left or a lazy provided custom exception
* apply(leftConsumer, rightConsumer) - Like a foreach, applies leftConsumer or rightConsumer depending on what this is
* applyLeft(consumer) - Applies action to value if this is Either.Left or does nothing
* map(leftMapper, rightMapper) - Transforms the type/value of Either, by mapping both sides
* mapLeft(mapper) - Transforms the type/value of Either.Left (only type, if this is Either.Right)
* flatMapLeft(mapper) - Same as above, but the mapper uses an Either return type
* ... same methods for right
* rotate() - Swap right and left sides 
* equals() & hashCode - Two `Either`s are equal if they are same side and same value.
* toString() - Calls `String.valueOf()` on the contained value (be it left or right)
* toDebugString - Returns a string in the format of `"Either.Left{toString()}"` or `"Either.Right{toString()}"`     

The online javadoc can be see here: [click me](https://javadoc.jitpack.io/com/github/ts14ic/Either/v0.1.3/javadoc/md/ts14ic/either/Either.html)  

# Version history
* 0.1 - initial
* 0.1.1 - published javadocs and sources
* 0.1.2 - equals and hashcode were added
* 0.1.3 - make Either constructor package private

# Setup
[![](https://jitpack.io/v/ts14ic/Either.svg)](https://jitpack.io/#ts14ic/Either)

Click the badge up there to get the latest version. But it's usually something like:  

Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		// add this
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency
```gradle
dependencies {
        implementation 'com.github.ts14ic:Either:v0.1.3'
}
```

# Example usages
What the interface can do, can be seen here:  
[EitherTest](https://github.com/ts14ic/Either/blob/master/src/test/java/md/ts14ic/either/EitherTest.java)

```java
private void run() {
    downloadNumber()
            .flatMapLeft(this::parseNumber)
            .mapLeft(number -> number / 100.0f)
            .apply(
                    number -> System.out.println("Got a number: " + number),
                    e -> System.out.println("Got an error: " + e)
            );
}

private Either<String, Exception> downloadNumber() {
    return Either.left("100");
}

private Either<Integer, Exception> parseNumber(String number) {
    try {
        return Either.left(Integer.parseInt(number));
    } catch (RuntimeException e) {
        return Either.right(e);
    }
}
```

Or an imperative version:  
```java
private void run2() {
    Either<String, Exception> downloaded = downloadNumber();
    
    Either<Integer, Exception> downloadedParsedNumber;
    if (downloaded.isLeft()) {
        Either<Integer, Exception> parsedNumber = parseNumber(downloaded.getLeft());
        if (parsedNumber.isLeft()) {
            downloadedParsedNumber = Either.left(parsedNumber.getLeft());
        } else {
            downloadedParsedNumber = Either.right(parsedNumber.getRight());
        }
    } else {
        downloadedParsedNumber = Either.right(downloaded.getRight());
    }
    
    Either<Float, Exception> mappedNumber;
    if (downloadedParsedNumber.isLeft()) {
        mappedNumber = Either.left(downloadedParsedNumber.getLeft() / 100.0f);
    } else {
        mappedNumber = Either.right(downloadedParsedNumber.getRight());
    }
    
    if (mappedNumber.isLeft()) {
        System.out.println("Got a number: " + mappedNumber.getLeft());
    } else {
        System.out.println("Got an error: " + mappedNumber.getRight());
    }
}
```
