package ru.otus;

import com.google.common.base.Strings;


/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/gradleHelloOtus-0.1.jar
 */
public class HelloOtus {
    public static void main(String... args) {

        System.out.println(Strings.repeat("Hello, Otus! ", 3));
        
    }
}
