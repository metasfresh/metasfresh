# Docker Compose for Local Development

This directory contains Docker Compose configuration for running metasfresh locally.

## Quick Start

```bash
# Start all services
docker compose up -d

# Check status
docker compose ps

# View logs
docker compose logs -f

# Stop all services
docker compose down
```

## Running E2E Tests Locally

To run Playwright E2E tests against the Docker stack, you need to enable the frontend testing API:

```bash
# Start with testing overlay (enables FRONTEND_TESTING=true on app server)
docker compose -p mfstack -f compose.yml -f compose-testing.yml up -d

# Or using COMPOSE_FILE environment variable
export COMPOSE_FILE=compose.yml:compose-testing.yml
docker compose -p mfstack up -d
```

### What the testing overlay does

`compose-testing.yml` adds the `FRONTEND_TESTING=true` environment variable to the app service, which enables the `/api/v2/frontendTesting` REST endpoint used by Playwright tests to create test data.

## Configuration Files

| File | Purpose |
|------|---------|
| `compose.yml` | Base service definitions |
| `compose-testing.yml` | Testing overlay (adds FRONTEND_TESTING=true) |
| `.env` | Environment variables (registry, version, db qualifier) |
| `metasfresh.properties` | Application properties |
| `web-config.js` | Web UI (desktop) configuration |
| `mobile-config.js` | Mobile UI configuration |

### Config.js Format

The config.js files must use `window.config` (not `const config`) for the Playwright tests to read configuration:

```javascript
// Correct format
window.config = {
    SERVER_URL: 'http://localhost:8282'
}

// Wrong format (tests will fail)
const config = {
    SERVER_URL: 'http://localhost:8282'
}
```

## Service Ports

| Service | Port | Description |
|---------|------|-------------|
| db | 14432 | PostgreSQL database |
| rabbitmq | 5672, 15672 | RabbitMQ (AMQP, Management UI) |
| search | 9200, 9300 | Elasticsearch |
| webapi | 8080, 8789 | WebAPI (REST, Debug) |
| app | 8282, 8788 | App server (REST, Debug) |
| webui | 3000, 3443 | Web frontend (HTTP, HTTPS) |
| mobile | 3001 | Mobile frontend |
| external | 8095 | External systems |
| edi | 8184 | EDI integration |

## E2E Test Setup

### Frontend Web UI Tests

```bash
cd ../../e2e/frontend-webui
npm install
npx playwright install chromium
npm test
```

### Mobile Web UI Tests

```bash
cd ../../e2e/mobile-webui
npm install
npx playwright install chromium
npm test
```

**Note**: Each E2E test project needs its own `npx playwright install chromium` because they may use different Playwright versions.

## Troubleshooting

### "Frontend testing REST endpoints are disabled"

The app server was started without the testing overlay. Restart with:

```bash
docker compose -p mfstack -f compose.yml -f compose-testing.yml up -d app
```

### "window.config !== undefined" timeout in mobile tests

The `mobile-config.js` file uses `const config` instead of `window.config`. Fix the file to use `window.config = {...}` and recreate the mobile container.

### Port conflicts

Check if another metasfresh stack is running. Use `docker ps` to find and stop conflicting containers.
