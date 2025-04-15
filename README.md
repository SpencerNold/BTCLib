# ByteCodeLib
A Java Library meant to be able to easily interface with byte code through annotations.
## Installing
BTCLib can be installed easily using maven or gradle through jitpack.io.
### Gradle
In `settings.gradle.kts`
```kotlin
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url = uri("https://jitpack.io") }
	}
}
```
In `build.gradle.kts`
```kotlin
dependencies {
     implementation("com.github.SpencerNold:BTCLib:-SNAPSHOT")
}
```
### Maven
In `pom.xml`
```xml
<repositories>
	<repository>
	     <id>jitpack.io</id>
		 <url>https://jitpack.io</url>
	</repository>
</repositories>

...

<dependency>
    <groupId>com.github.SpencerNold</groupId>
	<artifactId>BTCLib</artifactId>
	<version>-SNAPSHOT</version>
</dependency>
```
## Usage
Using the @Transformer annotation, you can declare a transformer class, and any method declared with the @Injector annotation will be representative of an injector to be inserted.
```java
@Transformer(className = "ClassNameHere")
public class Test {
    @Injector(name = "methodNameAndDescriptor()V", target = Target.HEAD)
    public void onGenericMethodCall(Object instance, Callback callback) {
        // Called at the head of ClassNameHere::methodNameAndDescriptor
    }
}
```
```java
public static void transform(byte[] classByteCode) {
    ClassAdapter adapter = new ClassAdapter();
    adapter.registerTransformerClass(Test.class);
    return adapter.transform("ClassNameHere", classByteCode);
}
```
This code represents inserting `Test::onGenericMethodCall` into the very top of any target method.
### Features
BTCLib supports inserting method calls into the `HEAD` of methods, `TAIL` of methods, as well as individual opcode instances with a directed ordinal. Additionally the `@Local` annotation can be used to load locals in the method's local table, and arguments passed to the function can be accessed. See the `test` folder for more examples.
