# Report Portal Test Automation Framework

A Java-based test automation framework for UI and API testing of [Report Portal](https://reportportal.io/) — an open-source test reporting and analytics platform.

## About

This project covers end-to-end automated testing of Report Portal's core functionality, including filter management via both API and UI layers. It was built to demonstrate a full-stack testing approach using industry-standard tools and CI/CD integration.

## Tech Stack

| Layer | Tools |
|---|---|
| Language | Java 9 |
| Build | Maven |
| API Testing | REST Assured, Apache HttpClient |
| UI Testing | Selenium, Selenide |
| Test Frameworks | JUnit 5, TestNG, Cucumber (BDD) |
| Assertions | AssertJ |
| Reporting | Report Portal (agent-java-junit5) |
| Logging | SLF4J, Logback |
| Data Binding | Jackson, Lombok |
| CI/CD | Jenkins (see Jenkinsfile) |
| Code Quality | SonarCloud |

## Project Structure

```
src/
└── main/test/
    └── rp/testing/
        ├── junit/tests/
        │   ├── api/testsuites/       # API test suites
        │   └── ui/selenide/suites/   # UI test suites
```

## Test Coverage

- **API tests** — validate Report Portal filter management endpoints (create, read, update, delete)
- **UI tests** — verify filter functionality through the browser using Selenide

## Running the Tests

### Prerequisites

- Java 9+
- Maven
- A running Report Portal instance (local or remote)

### Run all tests

```bash
mvn clean test
```

### Run via Jenkins

The project includes a `Jenkinsfile` and `Jenkinsfile.yaml` for pipeline execution. Tests can be triggered on a schedule or ad hoc via the Jenkins job.

## CI/CD

The framework is integrated with Jenkins for automated regression runs. Test results are reported to Report Portal for visibility and trend analysis. Code quality is monitored via SonarCloud.
