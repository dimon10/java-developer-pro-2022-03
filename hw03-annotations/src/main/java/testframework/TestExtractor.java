package testframework;

import lombok.Getter;
import testframework.annotation.After;
import testframework.annotation.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Extracts test methods from test class ond instantiates tests.
 */
@Getter
public class TestExtractor {
    private final List<Method> testMethods;
    private final Method beforeMethod;
    private final Method afterMethod;
    private final Class<?> tClass;

    public TestExtractor(String testClassName) {
        try {
            this.tClass = Class.forName(testClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Test class not found");
        }

        Method[] methods = this.tClass.getDeclaredMethods();
        this.testMethods = this.extractTestMethods(methods);
        List<Method> beforeMethods = this.extractBeforeMethods(methods);
        List<Method> afterMethods = this.extractAfterMethods(methods);

        if (beforeMethods.size() > 1 || afterMethods.size() > 1) {
            throw new IllegalArgumentException("Test class %s has more than 1 after or before methods".formatted(testClassName));
        }

        beforeMethod = beforeMethods.size() == 1 ? beforeMethods.get(0) : null;
        afterMethod = afterMethods.size() == 1 ? afterMethods.get(0) : null;
    }

    public List<Test> getTestInstances() {
        ArrayList<Test> tests = new ArrayList<>();

        for (Method testMethod : testMethods) {
            var test = new Test(tClass, testMethod, beforeMethod, afterMethod);
            tests.add(test);
        }

        return tests;
    }

    private List<Method> extractTestMethods(Method[] methods) {
        return this.extractMethodsByAnnotation(methods, testframework.annotation.Test.class);
    }

    private List<Method> extractBeforeMethods(Method[] methods) {
        return this.extractMethodsByAnnotation(methods, Before.class);
    }

    private List<Method> extractAfterMethods(Method[] methods) {
        return this.extractMethodsByAnnotation(methods, After.class);
    }

    private List<Method> extractMethodsByAnnotation(Method[] methods, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(methods)
                .filter((method) -> {
                    Annotation[] annotations = method.getDeclaredAnnotations();
                    return Arrays.stream(annotations)
                            .anyMatch((a) -> a.annotationType().equals(annotationClass));
                }).toList();
    }
}
