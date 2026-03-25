# Adding New Docker Images

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Step-by-step guide for new images**

## Step 1: Create Dockerfile

Location: `docker-builds/Dockerfile.{component}`

### Template for New Service

```dockerfile
FROM eclipse-temurin:8-jre-jammy

WORKDIR /opt/myservice

# Copy artifacts from build stage
ARG REFNAME=local
ARG REGISTRY=
COPY --from=${REGISTRY}metasfresh/metas-mvn-backend:$REFNAME \
     /backend/myservice/target/myservice.jar \
     app.jar

# Add metadata
COPY metadata/build-info.properties META-INF/
COPY metadata/git.properties BOOT-INF/classes/
RUN zip -g app.jar META-INF/build-info.properties BOOT-INF/classes/git.properties

# Configuration
EXPOSE 8090
ENV JAVA_OPTS="-Xmx512m"

# Health check
HEALTHCHECK --interval=10s --timeout=10s --start-period=60s --retries=30 \
  CMD curl --fail --silent http://localhost:8090/health | grep UP || exit 1

# Startup
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Note:** Use `eclipse-temurin:8-jre-jammy` for Java 8 backend services, `eclipse-temurin:17-jre-jammy` for Java 17 Camel services.

## Step 2: Update CI/CD Pipeline

Add to `.github/workflows/cicd.yaml`:

```yaml
- name: build-myservice
  run: |
    docker buildx build \
    --progress=plain \
    --file docker-builds/Dockerfile.myservice \
    --cache-to type=inline \
    --cache-from metasfresh/metas-myservice:${{ needs.init.outputs.tag-floating }} \
    --build-arg REFNAME=${{ needs.init.outputs.tag-fixed }} \
    --tag metasfresh/metas-myservice:${{ needs.init.outputs.tag-floating }} \
    --tag metasfresh/metas-myservice:${{ needs.init.outputs.tag-fixed }} \
    .

- name: push-image metasfresh/metas-myservice
  uses: nick-fields/retry@v3
  with:
    max_attempts: ${{ vars.RETRY_ATTEMPTS }}
    command: docker push --quiet metasfresh/metas-myservice:${{ needs.init.outputs.tag-fixed }}
```

## Step 3: Add to Docker Compose (if runtime service)

Update `docker-builds/compose/compose.yml`:

```yaml
myservice:
  image: $mfregistry/metas-myservice:$mfversion
  ports:
    - "8090:8090"
  healthcheck:
    test: "curl --fail --silent localhost:8090/health || exit 1"
    interval: 10s
    timeout: 10s
    start_period: 60s
    retries: 30
  environment:
    - JAVA_OPTS=-Xmx512m
  depends_on:
    db:
      condition: service_healthy
  deploy:
    mode: replicated
    replicas: 1
```

## Step 4: Test Locally

```bash
# Build image
docker buildx build -f docker-builds/Dockerfile.myservice \
  -t metasfresh/metas-myservice:local .

# Update compose/.env
echo "mfversion=local" > docker-builds/compose/.env

# Start stack
cd docker-builds/compose
docker compose up -d

# Check health
docker inspect --format "{{json .State.Health }}" compose-myservice-1 | jq

# Check logs
docker logs compose-myservice-1
```

## Step 5: Update Documentation

- Add entry to `docker-builds/CLAUDE.md` under "Dockerfile Naming Convention"
- Update README.md if user-facing
- Add to `.github/workflows/CLAUDE.md` if CI/CD changes

## Checklist

- [ ] Dockerfile follows naming convention: `Dockerfile.{component}`
- [ ] Uses multi-stage build where appropriate
- [ ] Has health check for runtime images
- [ ] Includes both floating and fixed tags in CI
- [ ] Added to docker compose if runtime service
- [ ] Documentation updated
