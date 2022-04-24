package testframework;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestExtractorTest {

    TestExtractor testExtractor;
    List<testframework.Test> actualTests;
    final String[] expectedTestMethodNames = {"test1_should_passed",
            "test2_should_throw_assertion_error",
            "test3_should_throw_runtime_exception",
            "test4_should_passed"};

    @BeforeEach
    void setUp() {
        testExtractor = new TestExtractor("example.ExampleTest");
        actualTests = testExtractor.getTestInstances();
    }

    @Test
    void extracted_test_methods_should_be_the_same_methods_in_class() {
        var actualTestNames = testExtractor.getTestMethods().stream()
                .map(Method::getName)
                .toList();

        assertThat(actualTestNames).containsExactlyInAnyOrder(expectedTestMethodNames);

        assertThat(testExtractor.getBeforeMethod().getName()).isEqualTo("setUp");
        assertThat(testExtractor.getAfterMethod().getName()).isEqualTo("tearDown");
    }

    @Test
    void test_instances_should_be_created_correctly() {
        var actualTestMethodNames = actualTests.stream()
                .map(test -> test.getTestMethod().getName())
                .toList();
        var actualBeforeMethodsNames = actualTests.stream()
                .map(test -> test.getBeforeMethod().getName())
                .toList();
        var actualAfterMethodsNames = actualTests.stream()
                .map(test -> test.getAfterMethod().getName())
                .toList();

        assertThat(actualTestMethodNames).containsExactlyInAnyOrder(expectedTestMethodNames);
        assertThat(actualBeforeMethodsNames).containsExactly("setUp","setUp","setUp","setUp");
        assertThat(actualAfterMethodsNames).containsExactly("tearDown","tearDown","tearDown","tearDown");
    }

}