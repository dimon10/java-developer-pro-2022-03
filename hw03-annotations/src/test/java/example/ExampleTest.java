package example;

import static org.assertj.core.api.Assertions.assertThat;
import testframework.After;
import testframework.Before;
import testframework.Test;

public class ExampleTest {

    private static final int INITIAL_TEST_VALUE = 10;
    private static final int FINAL_TEST_VALUE = 20;
    private int testVariable = 0;

    @Before
    void setUp() {
        testVariable = INITIAL_TEST_VALUE;
    }

    @After
    void tearDown() {
        testVariable = FINAL_TEST_VALUE;
    }

    @Test
    void test1_should_passed() {
        testVariable += 1;
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
        assertThat(testVariable).isEqualTo(14);
    }

}
