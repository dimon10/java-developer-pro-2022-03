package testframework;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String testClass = args[0];
        TestRunner testRunner = new TestRunner(testClass);
//        TestRunner testRunner = new TestRunner("example.ExampleTest");
        List<TestResult> testResults = testRunner.run();

        for (TestResult testResult : testResults) {
            System.out.println(testResult);
        }

    }
}
