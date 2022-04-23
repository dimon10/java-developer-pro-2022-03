package testframework;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents test with test class and test methods.
 */
@Getter
@EqualsAndHashCode(of = {"testClass", "testMethod"})
public class Test {
    private final Class<?> testClass;
    private final Method testMethod;
    private Method beforeMethod;
    private Method afterMethod;
    private final Object testClassInstance;

    public Test(Class<?> testClass, Method testMethod, Method beforeMethod, Method afterMethod) {
        this(testClass, testMethod);
        this.beforeMethod = beforeMethod;
        this.afterMethod = afterMethod;
    }

    private Test(Class<?> testClass, Method testMethod) {
        if (testClass != null && testMethod != null) {
            this.testClass = testClass;
            this.testMethod = testMethod;
            if (testClass.getDeclaredConstructors().length != 1) {
                throw new IllegalArgumentException("Test class must have only one constructor");
            } else {
                Constructor<?> constructor = testClass.getDeclaredConstructors()[0];

                try {
                    this.testClassInstance = constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException var5) {
                    throw new RuntimeException("Can not instantiate test class", var5);
                }
            }
        } else {
            throw new IllegalArgumentException("Test class and test method must not be null");
        }
    }

    public boolean hasBeforeMethod() {
        return this.beforeMethod != null;
    }

    public boolean hasAfterMethod() {
        return this.afterMethod != null;
    }

    public String toString() {
        return "Test[%s.%s]".formatted(testClass.getSimpleName(), testMethod.getName());
    }

}
