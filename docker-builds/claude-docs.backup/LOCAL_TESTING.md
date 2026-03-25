# Local Testing

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Testing images locally and incremental builds**

## Quick Test: Single Image

```bash
# Build
docker build -f docker-builds/Dockerfile.frontend -t metas-frontend:test .

# Run standalone
docker run -p 80:80 metas-frontend:test

# Access
curl http://localhost
```

## Full Stack Test: Docker Compose

```bash
# Set version to local builds
cd docker-builds/compose
echo "mfversion=local" > .env

# Start all services
docker compose up -d

# Watch logs
docker compose logs -f

# Check health of all services
docker compose ps

# Access services:
# - Web UI: http://localhost
# - Mobile: http://localhost:8880
# - API: http://localhost:8080
# - App: http://localhost:8282

# Stop all
docker compose down

# Stop and remove volumes
docker compose down -v
```

## Database Variants

### Pre-loaded Database
```bash
cd docker-builds/compose
echo "dbqualifier=-preloaded" >> .env
docker compose up -d
```

### Post-Cucumber Database
```bash
cd docker-builds/compose
echo "dbqualifier=-postcucumber" >> .env
docker compose up -d
```

## Incremental Builds (Dev-Mode)

For development with CI images and local code changes, use the `mf-docker` skill:

```bash
# Use the mf-docker skill for incremental builds
# Builds specific modules and hot-reloads into the running stack
```

### How It Works

- Uses Maven `-pl <module> -am` from `backend/` reactor root
- `-am` (also-make) rebuilds changed dependencies automatically
- Uses parallel builds (`-T 1C`)
- Copies JAR to `.dev-artifacts/` where `compose-dev.yml` mounts it
- Restarts container and waits for health check

## Quick Reference Commands

### Build Common Image
```bash
docker buildx build \
  --file docker-builds/Dockerfile.common \
  --secret id=mvn-settings,src=docker-builds/mvn/settings.xml \
  --tag metasfresh/metas-mvn-common:local \
  .
```

### Build Backend Image
```bash
docker buildx build \
  --file docker-builds/Dockerfile.backend \
  --secret id=mvn-settings,src=docker-builds/mvn/settings.xml \
  --build-arg REFNAME=local \
  --tag metasfresh/metas-mvn-backend:local \
  .
```

### Build Frontend Image
```bash
docker buildx build \
  --file docker-builds/Dockerfile.frontend \
  --tag metasfresh/metas-frontend:local \
  .
```

### Build API Runtime Image
```bash
docker buildx build \
  --file docker-builds/Dockerfile.backend.api \
  --build-arg REFNAME=local \
  --tag metasfresh/metas-api:local \
  .
```

### Extract Test Results
```bash
# JUnit
docker run --rm -v "$(pwd)/junit:/reports" metasfresh/metas-junit:local

# Jest
docker run --rm -v "$(pwd)/jest:/reports" metasfresh/metas-frontend:test
```

### Check All Health Checks
```bash
cd docker-builds/health
./containerHealthCheck.sh db
./containerHealthCheck.sh webapi
./containerHealthCheck.sh app
./apiHealthChecks.sh
```
