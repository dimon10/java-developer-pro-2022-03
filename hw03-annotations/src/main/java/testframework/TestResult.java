package testframework;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

/**
 * Test result of the test.
 */
@Getter
@EqualsAndHashCode(of = {"test"})
public class TestResult {
    private final Test test;
    @Setter
    private TestStatus status;
    @Setter
    private Throwable error;
    @Setter
    private Duration duration;

    public TestResult(testframework.Test test, TestStatus status, Throwable error) {
        this.test = test;
        this.status = status;
        this.error = error;
    }

    public String toString() {
        String durationStr = duration.toString().substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
        String errorMessage = error != null ? error.getMessage() : "";

        return formatString(status.toString(), 12) + " | " +
                formatString(test.toString(), 70) + " | " +
                formatString(durationStr, 10) + " | " +
                formatString(errorMessage, 200);
    }

    private String formatString(String inputString, int targetLength) {
        String str = inputString.replace("\n", "").replace("\r", "");
        if (str.length() > targetLength) {
            return str.substring(0, targetLength);
        } else {
            String spaces = " ".repeat(targetLength - str.length());
            return str.concat(spaces);
        }
    }

}
