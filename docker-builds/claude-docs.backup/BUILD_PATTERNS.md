# Docker Build Patterns

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Multi-stage builds, caching, optimizations**

## Pattern 1: Maven Build Optimization

```dockerfile
# Stage 1: Extract POMs only (Alpine for small size)
FROM alpine AS poms
COPY backend .
RUN find . -type f ! -name "pom.xml" -exec rm -r {} \;

# Stage 2: Build with cached dependencies
FROM maven:3.8.4-jdk-8 AS build
COPY --from=common /root/.m2 /root/.m2/
COPY --from=poms /backend .
RUN mvn resolve-dependencies  # Cached layer
COPY backend .                 # Source code layer
RUN mvn install               # Build layer
```

**Why**: Dependencies change rarely, source code changes often. Separating them enables better layer caching.

## Pattern 2: Frontend with Test Stage

```dockerfile
# Stage 1: Build
FROM node:20 AS build
RUN yarn install
RUN yarn lint
RUN webpack

# Stage 2: Test (branches from build)
FROM build AS test
RUN yarn test
CMD cp /webui/jest.* /reports/

# Stage 3: Runtime (from build, not test)
FROM nginx:1.21.6 AS runtime
COPY --from=build /webui/dist/ .
```

**Why**: Test stage is optional. CI builds to test stage, local builds skip to runtime.

## Pattern 3: Repackaging with Metadata

```dockerfile
FROM eclipse-temurin:8-jre-jammy AS repackage
COPY --from=builder /dist/metasfresh-webui-api.jar app.jar
COPY metadata/build-info.properties META-INF/
COPY metadata/git.properties BOOT-INF/classes/
RUN zip -g app.jar META-INF/build-info.properties BOOT-INF/classes/git.properties

FROM eclipse-temurin:8-jre-jammy AS runtime
COPY --from=repackage app.jar .
```

**Why**: Injects build metadata without rebuilding entire application.

## Offline Maven Builds

All Maven Dockerfiles use offline builds for reproducibility:

```dockerfile
# First: Resolve all dependencies (with internet access)
RUN --mount=type=secret,id=mvn-settings,dst=/root/.m2/settings.xml \
    mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

# Then: Build offline (no internet needed)
RUN --mount=type=secret,id=mvn-settings,dst=/root/.m2/settings.xml \
    mvn clean install --offline -Dmaven.gitcommitid.skip=true
```

**Why**: Ensures all dependencies are in the image, prevents download failures.

## Git Plugin Workaround

Always skip git plugin: `-Dmaven.gitcommitid.skip=true`

**Reason**: Git plugin fails in Docker context. Git metadata is injected via git.properties instead.

## Test Skipping

- Build images skip tests: `-DskipTests`
- Test images run tests: `mvn test` (without -DskipTests)

**Reason**: Tests run in dedicated test jobs, not during artifact builds.

## Layer Caching Strategy

```bash
docker buildx build \
  --cache-from metasfresh/metas-backend:floating-tag \
  --cache-to type=inline \
  ...
```

- `--cache-from`: Pull and use layers from previous build
- `--cache-to type=inline`: Embed cache in pushed image
- **Effect**: Subsequent builds only rebuild changed layers

## Executable Configuration Change

In Dockerfile.backend:
```dockerfile
RUN sed -i 's/<executable>true<\/executable>/<executable>false<\/executable>/' \
    metasfresh-dist/serverRoot/pom.xml
```

**Why**: Allows repackaging JARs with metadata in downstream images without rebuilding.

## Performance Optimizations

### Current Optimizations

1. **Multi-stage builds**: Separate build/test/runtime stages
2. **Layer caching**: `--cache-from` and `--cache-to`
3. **POM-only layer**: Maven dependency resolution in separate layer
4. **Offline builds**: All dependencies fetched in separate step
5. **Parallel builds**: CI runs multiple images in parallel
6. **BuildKit**: Modern build engine with advanced features

### Potential Improvements

1. **Maven Dependency Cache**: Use GitHub Actions cache for `.m2` directory
2. **Yarn Cache**: Use GitHub Actions cache for `node_modules`
3. **Buildx Bake**: Use `docker buildx bake` for multi-image builds
4. **Build Matrix**: Test multiple Java/Node versions in parallel
