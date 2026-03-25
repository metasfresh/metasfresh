# Docker Troubleshooting

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Common issues and solutions**

## Build Issues

### Maven Dependency Download Fails

**Symptom**:
```
[ERROR] Failed to execute goal ... 401 Unauthorized
```

**Fix**:
```bash
# For local builds, edit docker-builds/mvn/local-settings.xml:
<username>your-github-username</username>
<password>ghp_YourPersonalAccessToken</password>

# For CI, verify METASFRESH_PACKAGES_READ_TOKEN secret
```

### Docker Build Context Too Large

**Symptom**:
```
Sending build context to Docker daemon ... GB
```

**Fix**:
- Always build from repository root: `docker build -f docker-builds/Dockerfile.backend .`
- Check `.dockerignore` includes `node_modules/`, `.git/`, build outputs
- Use multi-stage builds to minimize final image size

### Layer Caching Not Working

**Symptom**: Every build rebuilds all layers

**Fix**:
```bash
# Pull previous build for cache
docker pull metasfresh/metas-backend:5.175-master

# Use it as cache source
docker buildx build --cache-from metasfresh/metas-backend:5.175-master ...
```

**Dockerfile optimization**:
- Copy dependency files (package.json, pom.xml) before source code
- Order instructions from least to most frequently changing

### Out of Memory During Build

**Symptom**:
```
Killed
# or
java.lang.OutOfMemoryError
```

**Fix**:
```bash
# Increase Docker daemon memory (Windows/Mac)
# Minimum: 4GB, Recommended: 8GB

# For specific builds:
ENV MAVEN_OPTS="-Xmx2048m"
ENV NODE_OPTIONS="--max-old-space-size=4096"
```

### Frontend Build Fails with OpenSSL Error

**Symptom**:
```
Error: error:0308010C:digital envelope routines::unsupported
```

**Fix** (Already in Dockerfile.frontend):
```dockerfile
ENV NODE_OPTIONS=--openssl-legacy-provider
```

### PostgreSQL Fails to Start

**Symptom**:
```
PostgreSQL Database directory appears to contain a database
```

**Fix**:
```bash
docker compose down -v
```

### Test Results Not Extracted

**Symptom**: Empty junit/jest directories after test run

**Fix**:
```bash
# Verify mount matches Dockerfile VOLUME declaration
docker run --rm -v "$(pwd)/junit:/reports" metasfresh/metas-junit:local

# For Windows paths with spaces:
docker run --rm -v "C:\path with spaces\junit:/reports" ...
```

## Runtime Issues

### Debug Commands

```bash
# Check health
docker inspect --format "{{json .State.Health }}" container-name | jq

# View logs
docker logs container-name

# Exec into container
docker exec -it container-name bash

# Check environment
docker exec container-name env

# Check ports
docker port container-name
```

### Network Issues

```bash
# List networks
docker network ls

# Inspect network
docker network inspect compose_default

# Test connectivity from container
docker exec webapi curl http://db:5432
```

## Build Failure Debugging

```bash
# Show all build steps
docker buildx build --progress=plain ...

# Keep intermediate containers
docker buildx build --no-cache --rm=false ...

# Inspect specific layer
docker history image:tag
docker run -it --rm <layer-id> sh
```

## Checklist for Build Failures

1. Docker daemon running and accessible
2. Sufficient disk space (builds can be 10+ GB)
3. BuildKit enabled: `DOCKER_BUILDKIT=1`
4. Secrets properly mounted
5. Build args passed correctly

## Checklist for Runtime Failures

1. All dependent services healthy
2. Environment variables set correctly
3. Volumes mounted at expected paths
4. Ports not already in use
5. Memory limits sufficient
