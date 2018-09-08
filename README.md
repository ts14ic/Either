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
