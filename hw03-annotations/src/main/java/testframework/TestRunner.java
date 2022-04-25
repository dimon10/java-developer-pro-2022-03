package testframework;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs tests for particular test class and returns results.
 */
@Getter
public class TestRunner {
    private final List<Test> tests;
    private final List<TestResult> testResults = new ArrayList<>();

    public TestRunner(String testClassName) {
        TestExtractor testExtractor = new TestExtractor(testClassName);
        tests = testExtractor.getTestInstances();
    }

    public List<TestResult> run() {
        testResults.clear();

        for (Test test : tests) {
            testResults.add(run(test));
        }

        return this.testResults;
    }

    private TestResult run(Test test) {
        TestResult testResult = new TestResult(test, TestStatus.NOT_STARTED, null);
        Object testClassObject = test.getTestClassInstance();
        Instant startTime = Instant.now();

        try {
            //Before method execution
            if (test.hasBeforeMethod()) {
                invokeMethod(test.getBeforeMethod().getName(), testClassObject);
            }

            //Test method execution
            invokeMethod(test.getTestMethod().getName(), testClassObject);

        } catch (Throwable e) {
            if (e.getCause() instanceof AssertionError) {
                testResult.setStatus(TestStatus.FAILED);
            } else {
                testResult.setStatus(TestStatus.BROKEN);
            }

            testResult.setError(e.getCause());
        } finally {
            //After method execution
            Throwable e = executeAfterMethod(test, testClassObject);

            if (testResult.getStatus() == TestStatus.NOT_STARTED) {
                var testStatus = e == null ? TestStatus.PASSED : TestStatus.BROKEN;
                testResult.setStatus(testStatus);
            }
            Instant endTime = Instant.now();
            testResult.setDuration(Duration.between(startTime, endTime));
        }

        return testResult;
    }

    private void invokeMethod(String methodName, Object classObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = classObject.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(classObject);
    }

    private Throwable executeAfterMethod(Test test, Object testClassObject) {
        try {
            if (test.hasAfterMethod()) {
                this.invokeMethod(test.getAfterMethod().getName(), testClassObject);
            }
        } catch (Throwable e) {
            return e;
        }

        return null;
    }

}
