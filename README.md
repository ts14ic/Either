# Either
An either monad backport

# Version history
* 0.1 - initial

# Setup
[![](https://jitpack.io/v/ts14ic/Either.svg)](https://jitpack.io/#ts14ic/Either)

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```gradle
dependencies {
	implementation 'com.github.User:Repo:Tag'
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
