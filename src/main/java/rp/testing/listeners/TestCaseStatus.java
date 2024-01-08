package rp.testing.listeners;

import lombok.Getter;

@Getter
public enum TestCaseStatus {
    PROCESSING("41"),
    PASSED("31"),
    FAILED("21");

    private final String id;

    TestCaseStatus(String id) {
        this.id = id;
    }
}
