# Port & URL Reference for Docker Environments

This document provides a comprehensive overview of ports and URLs across all Docker-based metasfresh environments.

## Environment Overview

There are **four distinct Docker environments** with different port configurations:

| Environment | Runs On | Purpose | Compose Location | Used By |
|-------------|---------|---------|------------------|---------|
| **mf-docker Stack** | 🖥️ Local | E2E testing & dev-mode | `docker-builds/e2e-tests/`* | `mf-docker.sh` (e2e-stack, build) |
| **Local Dev Infrastructure** | 🖥️ Local | Supporting services for IDE dev | `misc/dev-support/docker/infrastructure/` | `95_start.sh`, IntelliJ |
| **CI E2E Stack** | ☁️ GitHub Actions | CI full stack testing | `docker-builds/e2e-tests/` | `.github/workflows/cicd.yaml` |
| **Mobile Test Stack** | ☁️ GitHub Actions | Mobile Playwright tests | `docker-builds/e2e-tests/` | `.github/workflows/cicd.yaml` |

*⚠️ mf-docker currently expects `frontendtest/` which doesn't exist - see [Known Issues](#critical-directory-structure-discrepancy-mf-docker-is-broken)

## Port Comparison Table

### Database (PostgreSQL)

| Environment | External Port | Internal Port | JDBC URL |
|-------------|---------------|---------------|----------|
| mf-docker Stack | **14432** | 5432 | `jdbc:postgresql://localhost:14432/metasfresh` |
| CI E2E Stack (compose) | **14432** | 5432 | `jdbc:postgresql://localhost:14432/metasfresh` |
| Mobile Test Stack | **14432** | 5432 | `jdbc:postgresql://localhost:14432/metasfresh` |
| Local Dev (new_dawn_uat) | **20432** | 5432 | `jdbc:postgresql://localhost:20432/metasfresh` |

### Message Queue (RabbitMQ)

| Environment | AMQP Port | Management Port |
|-------------|-----------|-----------------|
| mf-docker Stack | **14672** | **14673** |
| CI E2E Stack (compose) | **14672** | **14673** |
| Mobile Test Stack | **14672** | **14673** |
| Local Dev (new_dawn_uat) | 20672 | 20673 |

### Search (Elasticsearch)

| Environment | HTTP Port | Transport Port |
|-------------|-----------|----------------|
| mf-docker Stack | **14200** | **14300** |
| CI E2E Stack (compose) | **14200** | **14300** |
| Mobile Test Stack | **14200** | **14300** |
| Local Dev (new_dawn_uat) | 20300 | - |

### WebAPI Service

| Environment | HTTP Port | Debug Port |
|-------------|-----------|------------|
| mf-docker Stack | 8080 | - |
| CI E2E Stack (compose) | 8080 | - |
| Mobile Test Stack | - | - |
| Local Dev (from IDE/CLI) | **8080** | (configurable) |

### App Server (ServerBoot)

| Environment | HTTP Port | Debug Port |
|-------------|-----------|------------|
| mf-docker Stack | 8282 | 8788 |
| CI E2E Stack (compose) | 8282 | 8788 |
| Mobile Test Stack | 8282 | 8788 |
| Local Dev (from IDE/CLI) | **8282** | (configurable) |

**Note:** When running Java apps locally via `metasfresh-compile-and-test` or IDE, ports are configurable via `-Dserver.port=XXXX`. The standard convention is:
- App Server (ServerBoot): 8282
- WebAPI: 8080

### Frontend (WebUI)

| Environment | HTTP Port | HTTPS Port | Access URL |
|-------------|-----------|------------|------------|
| mf-docker Stack | **3000** | - | http://localhost:3000 |
| CI E2E Stack (compose) | **3000** | 3443 | http://localhost:3000 |
| Mobile Test Stack | - | - | - |
| Private Repo E2E Stack | **80** | 443 | http://localhost |

### Mobile WebUI

| Environment | Port | Access URL |
|-------------|------|------------|
| mf-docker Stack | (pending) | (mobile not yet in frontendtest) |
| CI E2E Stack (compose) | **3001** | http://localhost:3001 |
| Mobile Test Stack | **3001** | http://localhost:3001 |
| Private Repo E2E Stack | (via webui) | http://localhost/mobile/ |

**Note:** Private repos bundle mobile inside the webui container, accessible at `/mobile/` path. Adding mobile to frontendtest is pending.

## Access URLs Summary

### 🖥️ mf-docker Stack - Local dev via `mf-docker.sh` (⚠️ BROKEN)

Uses `docker-builds/e2e-tests/` with `--profile frontend`

| Service | URL |
|---------|-----|
| Desktop WebUI | http://localhost:3000 |
| Mobile WebUI | http://localhost:3001 (with `--profile mobile`) |
| WebAPI Health | http://localhost:8080/health |
| App Health | http://localhost:8282/health |
| RabbitMQ UI | http://localhost:14673 |
| Elasticsearch | http://localhost:14200 |
| Database | localhost:14432 |

*⚠️ mf-docker currently broken - expects `frontendtest/` which doesn't exist*

### ☁️ metasfresh CI E2E Stack - GitHub Actions

Uses `docker-builds/e2e-tests/` with profiles

| Service | URL |
|---------|-----|
| Desktop WebUI | http://localhost:3000 |
| Mobile WebUI | http://localhost:3001 |
| WebAPI Health | http://localhost:8080/health |
| App Health | http://localhost:8282/health |
| RabbitMQ UI | http://localhost:14673 |
| Elasticsearch | http://localhost:14200 |
| Database | localhost:14432 |

### ☁️ Private Repo CI (e.g., ic114) - GitHub Actions

Uses `docker-builds/compose/` (different structure than metasfresh)

| Service | URL |
|---------|-----|
| Desktop WebUI | http://localhost |
| Mobile WebUI | http://localhost/mobile/ (bundled in webui) |
| WebAPI Health | http://localhost:8080/health |
| App Health | http://localhost:8282/health |
| Database | localhost:14432 |

### Local Dev Infrastructure (new_dawn_uat)

**Infrastructure Services (Docker):**
| Service | URL |
|---------|-----|
| PostgreSQL | localhost:20432 |
| RabbitMQ AMQP | localhost:20672 |
| RabbitMQ UI | http://localhost:20673 |
| Elasticsearch | http://localhost:20300 |
| PostgREST | http://localhost:20001 |
| Papercut (Email) | http://localhost:20408 |

**Java Applications (from IDE/CLI via `metasfresh-compile-and-test`):**
| Service | URL |
|---------|-----|
| App Server Health | http://localhost:8282/health |
| WebAPI Health | http://localhost:8080/health |
| WebAPI Swagger | http://localhost:8080/swagger-ui.html |

**Frontend (local dev server):**
| Service | URL |
|---------|-----|
| WebUI (yarn start) | http://localhost:3000 |
| Mobile (yarn start) | http://localhost:3001 |

## Credentials

| Service | Username | Password | Notes |
|---------|----------|----------|-------|
| PostgreSQL | metasfresh | metasfresh | All environments |
| RabbitMQ (Docker) | metasfresh | metasfresh | E2E/Test stacks |
| RabbitMQ (Local Dev) | guest | guest | Infrastructure stacks |
| WebUI Login | metasfresh | metasfresh | Default admin user |

## Running Java Applications Locally

When running Java apps outside Docker (via IDE or `metasfresh-compile-and-test`), you need the Local Dev Infrastructure running.

### Using metasfresh-compile-and-test Skill

```bash
# Start local dev infrastructure first
metasfresh/misc/dev-support/docker/infrastructure/scripts/95_start.sh

# Then use metasfresh-compile-and-test skill to build and run
```

### Manual Java Execution

```bash
# Load environment for new_dawn_uat
source metasfresh/misc/dev-support/docker/infrastructure/env-files/new_dawn_uat.env

# Run ServerBoot (App)
$JAVA8_HOME/bin/java -Xmx1024M \
  -Dserver.port=8282 \
  -Dfrontend.testing=true \
  -Dspring.rabbitmq.port=$RABBITMQ_PORT \
  -Dspring.elasticsearch.rest.uris=http://localhost:$SEARCH_PORT \
  -jar backend/metasfresh-dist/serverRoot/target/metasfresh-dist-serverRoot-10.0.0.jar

# Run WebAPI
$JAVA8_HOME/bin/java -Xmx512M \
  -Dserver.port=8080 \
  -Dspring.rabbitmq.port=$RABBITMQ_PORT \
  -Dspring.elasticsearch.rest.uris=http://localhost:$SEARCH_PORT \
  -jar backend/metasfresh-webui-api/target/docker/metasfresh-webui-api.jar
```

## mf-docker Directory Detection

mf-docker now uses **smart directory detection** to work with both metasfresh and private repos:

| Priority | Directory | Repository Type | Notes |
|----------|-----------|-----------------|-------|
| 1 | `docker-builds/e2e-tests/` | metasfresh | Standard E2E stack with profiles |
| 2 | `docker-builds/compose/` | Private repos (ic114, etc.) | Falls back if e2e-tests not found |

**Automatic Features:**
- If `compose-testing.yml` exists, it's applied automatically (enables FRONTEND_TESTING=true)
- Mobile service is included if defined in compose.yml
- Error message shown if neither directory exists

### metasfresh e2e-tests Directory

The `docker-builds/e2e-tests/` directory uses **Docker Compose profiles**:

```bash
# Frontend tests (starts: db, rabbitmq, search, app, webapi, webui, playwright-frontend)
docker compose --profile frontend up

# Mobile tests (starts: db, rabbitmq, search, app, mobile, playwright-mobile)
docker compose --profile mobile up
```

**Ports in e2e-tests:**
- Database: 14432
- WebUI: 3000
- Mobile: 3001
- WebAPI: 8080
- App: 8282

### ic114 Directory Structure

**`docker-builds/compose/`** - Main E2E stack:
- No separate mobile service
- Mobile bundled inside webui container at `/mobile/` path
- Ports: db=14432, webui=80

**`docker-builds/mobiletest/`** - Separate CI mobile test stack

### Port Conflicts Between Stacks

Running multiple stacks simultaneously - port conflict analysis:
- PostgreSQL: 14432 (mf-docker) vs 14432 (CI compose) vs 20432 (local dev) - ⚠️ mf-docker and CI compose share 14432
- WebUI: 3000 (all E2E stacks) vs n/a (local dev) - OK
- RabbitMQ: 14672 (E2E stacks) vs 20672 (local dev) - OK, different
- Elasticsearch: 14200 (E2E stacks) vs 20300 (local dev) - OK, different

**Note**: mf-docker Stack uses unique 14xxx ports to avoid conflicts with Local Dev Infrastructure.

## Directory Structure by Repository

### metasfresh (public)

**Primary:** `docker-builds/e2e-tests/`
- Uses Docker Compose profiles (`--profile frontend`, `--profile mobile`)
- Mobile: Separate container on port 3001
- Ports: db=14432, webui=3000, webapi=8080, app=8282

**Secondary:** `docker-builds/compose/`
- Local development stack
- Has `compose-testing.yml` overlay for E2E testing

### Private Repos (ic114, ms205, etc.)

**Primary:** `docker-builds/compose/`
- No e2e-tests directory
- Mobile bundled in webui at `/mobile/` path
- Has `compose-testing.yml` overlay for E2E testing
- Ports: db=14432, webui=80

**Mobile Tests:** `docker-builds/mobiletest/` (separate CI stack)

## Related Documentation

- [Infrastructure CLAUDE.md](../../misc/dev-support/docker/infrastructure/CLAUDE.md) - Local dev setup
- [Compose CLAUDE.md](../compose/CLAUDE.md) - E2E compose details
