# Cucumber Utility Unit Tests

These are **local developer tests** for cucumber step definition utilities.

## NOT Run in CI/GitHub Actions

These tests are **not executed** during CI builds because:

1. The `de.metas.cucumber` module requires Testcontainers (PostgreSQL, RabbitMQ) to run
2. `Dockerfile.junit` runs offline without Docker-in-Docker support
3. When the module's infrastructure fails to start, all tests in the module are skipped

## Running Locally

To run these tests locally:

```bash
cd backend/de.metas.cucumber
mvn test -Dtest=*Test -s ../../../misc/dev-support/maven/settings.xml
```

Or run individual test classes directly from your IDE.

## Purpose

These tests validate utility methods used in cucumber step definitions, such as:
- String parsing helpers
- Data transformation utilities
- Assertion helpers

They don't require the full metasfresh infrastructure to run.
