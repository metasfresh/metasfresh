# CI/CD Jobs Reference

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Detailed breakdown of all pipeline jobs**

## 1. init

**Purpose**: Initialize build metadata and version tags

**Outputs**:
- `tag-floating`: `{mfversion}-{branch-name}` (e.g., `5.175-new_dawn_uat`)
- `tag-fixed`: `{mfversion}-{branch-name}.{build-number}` (e.g., `5.175-new_dawn_uat.123`)
- `build-info-properties`: Build metadata
- `git-properties`: Git metadata

**Version Format**: `{mfversion}.3-{branch-name}.{build-number}`

## 2. java

**Purpose**: Build Java/Maven components

**Builds** (in order):
1. `metas-mvn-common` - Parent POM and common libraries
2. `metas-mvn-backend` - Backend compilation
3. `metas-mvn-backend-dist` - Backend distribution
4. `metas-mvn-camel` - Camel integration modules
5. `metas-mvn-camel-dist` - Camel distribution

**Strategy**: Docker BuildKit with layer caching, secret mounts for Maven settings.

## 3. frontend

**Purpose**: Build React frontend and mobile apps

**Builds**:
- `metas-frontend` - React Web UI (port 80)
- `metas-mobile` - Mobile UI (port 8880)
- `metas-mobile-test` - Mobile test container with Playwright

**Process**: Node.js 20, yarn, webpack, nginx runtime.

## 4. test-frontend

**Purpose**: Run Jest unit tests for frontend

**Artifacts**: `jest-logs` (on failure)

## 5. backend

**Purpose**: Build runtime Docker images from compiled artifacts

**Builds**:
- `metas-api` - Web API (port 8080, debug 8789)
- `metas-app` - Application server (port 8282, debug 8788)
- `metas-externalsystems` - External systems integration
- `metas-edi` - EDI integration (port 8184)
- `metas-db` - Database init container
- `metas-db-migration-tool` - Database migration tool

## 6. preloaded-db

**Purpose**: Create database image with migrations pre-applied

**Process**:
1. Builds base db image
2. Starts db with docker compose
3. Runs migrations
4. Commits container as `{version}-preloaded`

## 7. procurement

**Purpose**: Build procurement system components

**Builds**: backend, nginx, rabbitmq, frontend for procurement subsystem.

## 8. compatibility-images

**Purpose**: Create backward-compatible images for instances.metasfresh.com

**Process**: Builds `-compat` variants and tags with legacy names:
- `metasfresh-webapi` (from metas-api)
- `metasfresh-app` (from metas-app)
- `metasfresh-webui` (from metas-frontend)
- `metasfresh-db` (from metas-db)

## 9. junit

**Purpose**: Run Java unit tests

**Test Suites**:
- Java 8 tests (commons + backend)
- Java 14 tests (camel)

**Exit Codes**: Checks both test exit code AND maven exit code.

## 10. cucumber-build

**Purpose**: Build Cucumber integration test container

**Output**: `metas-cucumber` image with test suite and compose files.

## 11. cucumber-run

**Purpose**: Run Cucumber integration tests in parallel

**Strategy**: Matrix with 10 parallel executors:
- profile1-7: Executor-specific tests
- report: Report generation tests
- flaky: Known flaky tests (allowed to fail)
- catchall: All other tests

**Timeout**: 120 minutes per profile

## 12. health_check

**Purpose**: Verify all services start and pass health checks

**Checks**: db, webapi, app, rabbitmq, search, external, webui, mobile, edi

## 13. mobile_test

**Purpose**: Run Playwright tests for mobile UI

**Timeout**: 60 minutes

**Artifacts**: `playwright-report` (always uploaded)

## 14. cicd-dispatch

**Purpose**: Trigger deployment workflows for specific branches

**Condition**: Only runs if branch is in `CICD_BRANCH_MAP` variable.

## Test Results Locations

| Test Type | Location |
|-----------|----------|
| JUnit | Testspace + artifacts |
| Cucumber | Testspace + HTML reports |
| Jest | Testspace + artifacts |
| Playwright | Testspace + HTML report |

## Post-Test Database Snapshots

| Suffix | Source |
|--------|--------|
| `-preloaded` | After migrations |
| `-postcucumber-{profile}` | After Cucumber tests |
| `-postmobiletest` | After mobile tests |
