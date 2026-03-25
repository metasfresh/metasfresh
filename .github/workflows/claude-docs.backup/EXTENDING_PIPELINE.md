# Extending the CI/CD Pipeline

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Adding new images, tests, and jobs**

## Adding a New Docker Image

### 1. Create Dockerfile

Create in `docker-builds/`:
```dockerfile
FROM existing-base-image
# Your build steps
```

### 2. Add Build Step

Add to appropriate job (java/frontend/backend):
```yaml
- name: build-myimage
  run: |
    docker buildx build \
    --progress=plain \
    --file docker-builds/Dockerfile.myimage \
    --cache-from metasfresh/metas-myimage:${{ needs.init.outputs.tag-floating }} \
    --build-arg REFNAME=${{ needs.init.outputs.tag-fixed }} \
    --tag metasfresh/metas-myimage:${{ needs.init.outputs.tag-floating }} \
    --tag metasfresh/metas-myimage:${{ needs.init.outputs.tag-fixed }} \
    .
```

### 3. Add Push Steps

Add push steps for both tags using retry action.

### 4. Update Summary

Add to produce-summary step.

## Adding a New Test Suite

### 1. Create Test Dockerfile

See `Dockerfile.junit` as example.

### 2. Add Job

```yaml
my-test:
  name: test (mytest)
  runs-on: ubuntu-latest
  needs: [ init, dependencies ]
  steps:
    # Build test image
    # Run tests
    # Extract results
    # Upload to testspace
    # Assert success
```

### 3. Configure Testspace

Add testspace integration step.

### 4. Add Artifact Upload

Upload logs on failure for debugging.

## Adding a New Job Dependency

Update the `needs:` array in dependent jobs:
```yaml
my-job:
  needs: [ init, java, frontend, my-new-job ]
```

**Important**: Check dependency graph to avoid circular dependencies.

## Modifying Version Format

1. Update `define-build-info-properties` step in init job
2. Adjust discriminator if needed (currently hardcoded to `3`)
3. Update documentation

## Performance Optimization

### Current Optimizations

- **BuildKit caching**: `--cache-from` reuses layers
- **Inline cache**: `--cache-to type=inline` embeds cache
- **Parallel jobs**: Max 10 concurrent Cucumber profiles
- **Multi-stage builds**: Separates build/test/runtime
- **POM extraction**: Maven dependencies in separate layer

### Potential Improvements

- Use GitHub Actions cache for Maven `.m2` directory
- Use external cache backend (registry, s3, gha)
- Optimize Cucumber test distribution across profiles
- Consider buildx bake for multi-image builds
- Implement fail-fast: false strategically

## Testing Locally

### Simulate GitHub Actions Builds

1. Set environment variables:
   ```bash
   export BUILD_SYSTEM="local"
   export BUILD_USER="developer"
   export GIT_REF="refs/heads/my-branch"
   export GIT_ID=$(git rev-parse HEAD)
   ```

2. Create local settings:
   ```bash
   envsubst < docker-builds/mvn/settings.xml > docker-builds/mvn/local-settings.xml
   ```

3. Build common image:
   ```bash
   docker buildx build \
     --file docker-builds/Dockerfile.common \
     --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml \
     --tag metasfresh/metas-mvn-common:local \
     .
   ```

4. Continue with other images using `--build-arg REFNAME=local`

## Future Improvements

### Known Issues
1. Mislabeled step (line 530-536)
2. Duplicate step names (line 530, 537)
3. Typos in step names (lines 495, 700, 610)

### Enhancement Ideas
1. Split workflow into separate files
2. Add SonarQube integration
3. Implement automatic rollback
4. Add security scanning (Trivy, Snyk)
5. Create reusable composite actions
