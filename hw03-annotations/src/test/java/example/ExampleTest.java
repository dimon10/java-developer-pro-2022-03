package example;

import testframework.annotation.After;
import testframework.annotation.Before;
import testframework.annotation.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleTest {
    private static final int INITIAL_TEST_VALUE = 10;
    private static final int FINAL_TEST_VALUE = 20;
    private int testVariable = 0;

    @Before
    void setUp() {
        this.testVariable = INITIAL_TEST_VALUE;
    }

    @After
    void tearDown() {
        this.testVariable = FINAL_TEST_VALUE;
    }

    @Test
    void test1_should_passed() {
        testVariable++;
        assertThat(testVariable).isEqualTo(11);
    }

    @Test
    void test2_should_throw_assertion_error() {
        testVariable += 2;
        assertThat(testVariable)
                .as("testVariable should be equal to 12")
                .isEqualTo(11);
    }

    @Test
    void test3_should_throw_runtime_exception() {
        testVariable += 3;
        throw new RuntimeException("RuntimeException in test3");
    }

    @Test
    void test4_should_passed() {
        testVariable += 4;
        assertThat(this.testVariable).isEqualTo(14);
    }

    void nonTestMethod() {
        assertThat(2).isEqualTo(3);
    }
}
