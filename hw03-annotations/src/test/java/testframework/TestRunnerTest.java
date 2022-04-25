package testframework;

import example.ExampleTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class TestRunnerTest {

    private TestRunner testRunner;
    private List<testframework.Test> actualTests;
    private List<TestResult> actualTestResults;

    @BeforeEach
    void setUp() {
        testRunner = new TestRunner("example.ExampleTest");
        testRunner.run();
        actualTests = testRunner.getTests();
        actualTestResults = testRunner.getTestResults();
    }

    @Test
    void test1_should_passed() {
        var test1 = getTestByMethodName("test1_should_passed", testRunner.getTests());
        var test1Result = getTestResultByTest(test1, actualTestResults);
        example.ExampleTest exampleTestInstance1 = (ExampleTest) test1.getTestClassInstance();

        assertThat(test1Result.getStatus())
                .isEqualTo(TestStatus.PASSED);
        assertThat(exampleTestInstance1.getTestVariable())
                .isEqualTo(11);
        assertThat(exampleTestInstance1.getTestVariableForBeforeMethod())
                .isEqualTo(ExampleTest.BEFORE_TEST_VALUE);
        assertThat(exampleTestInstance1.getTestVariableForAfterMethod())
                .isEqualTo(ExampleTest.AFTER_TEST_VALUE);
    }

    @Test
    void test2_should_throw_assertion_error() {
        var test2 = getTestByMethodName("test2_should_throw_assertion_error", testRunner.getTests());
        var test2Result = getTestResultByTest(test2, actualTestResults);
        example.ExampleTest exampleTestInstance2 = (ExampleTest) test2.getTestClassInstance();

        assertThat(test2Result.getStatus())
                .isEqualTo(TestStatus.FAILED);
        assertThat(test2Result.getError())
                .isInstanceOf(AssertionError.class);
        assertThat(exampleTestInstance2.getTestVariable())
                .isEqualTo(12);
        assertThat(exampleTestInstance2.getTestVariableForBeforeMethod())
                .isEqualTo(ExampleTest.BEFORE_TEST_VALUE);
        assertThat(exampleTestInstance2.getTestVariableForAfterMethod())
                .isEqualTo(ExampleTest.AFTER_TEST_VALUE);
    }

    @Test
    void test3_should_throw_runtime_exception() {
        var test3 = getTestByMethodName("test3_should_throw_runtime_exception", testRunner.getTests());
        var test3Result = getTestResultByTest(test3, actualTestResults);
        example.ExampleTest exampleTestInstance2 = (ExampleTest) test3.getTestClassInstance();

        assertThat(test3Result.getStatus())
                .isEqualTo(TestStatus.BROKEN);
        assertThat(test3Result.getError())
                .isInstanceOf(RuntimeException.class);
        assertThat(exampleTestInstance2.getTestVariable())
                .isEqualTo(13);
        assertThat(exampleTestInstance2.getTestVariableForBeforeMethod())
                .isEqualTo(ExampleTest.BEFORE_TEST_VALUE);
        assertThat(exampleTestInstance2.getTestVariableForAfterMethod())
                .isEqualTo(ExampleTest.AFTER_TEST_VALUE);
    }

    private testframework.Test getTestByMethodName(String methodName, List<testframework.Test> tests) {
        return tests.stream()
                .filter(test -> test.getTestMethod().getName().equals(methodName))
                .findFirst()
                .orElseThrow();
    }

    private TestResult getTestResultByTest(testframework.Test test, List<TestResult> testResults) {
        return testResults.stream()
                .filter(testResult -> testResult.getTest().equals(test))
                .findFirst()
                .orElseThrow();
    }
}