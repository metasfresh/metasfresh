# Dev-Mode Hot-Reload Test Suite

This test suite verifies the Docker dev-mode functionality, including:
- Zero-build startup from CI images
- Incremental builds for each component
- Hot-reload capabilities

## Prerequisites

1. Docker Desktop running with WSL2 integration
2. GitHub CLI (`gh`) installed and authenticated
3. CI images available for the branch being tested

## Test Suite Overview

| Test | Component | Type | Expected Duration |
|------|-----------|------|-------------------|
| 1 | All | Zero-Build Startup | ~2-3 min |
| 2 | webapi | Java JAR Change | ~3-5 min |
| 3 | app | Java JAR Change | ~3-5 min |
| 4 | webui | Frontend JS Change | ~1-2 min |
| 5 | mobile | Frontend JS Change | ~1-2 min (skip if not in compose) |
| 6 | reports | JRXML Change | ~1 min |
| 7 | All | Cleanup and Restart | ~2-3 min |
| 8 | All | Force Re-extraction | ~2-3 min |

---

## Test 1: Zero-Build Startup (Baseline)

**Purpose**: Verify dev-mode starts without any local builds, by extracting artifacts from CI images.

### Steps

```bash
# 1. Ensure clean slate - remove existing artifacts
rm -rf .dev-artifacts/

# 2. Pull latest CI images (if not already pulled)
./misc/dev-support/docker/scripts/mf-docker.sh pull new_dawn_uat

# 3. Start stack in dev-mode
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack start --dev-mode
```

### Expected Output

- Extraction messages should appear:
  ```
  Preparing dev-mode artifacts...
  Extracting webapi JAR from metasfresh/metas-api:TAG...
  Extracting app JAR from metasfresh/metas-app:TAG...
  Extracting reports from metasfresh/metas-app:TAG...
  Extracting frontend assets from metasfresh/metas-frontend:TAG...
  Dev-mode artifacts ready in .dev-artifacts
  ```
- All containers should become healthy
- Stack should be accessible:
  - Frontend: http://localhost:3000
  - API: http://localhost:8080
  - App: http://localhost:8282

### Verification

```bash
# Check .dev-artifacts/ contains extracted files
ls -la .dev-artifacts/
ls -la .dev-artifacts/webapi/
ls -la .dev-artifacts/app/
ls -la .dev-artifacts/webui/
ls -la .dev-artifacts/reports/

# Check container health
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack status

# Run Playwright login test
cd e2e/frontend-webui && npx playwright test tests/spec/login.spec.js
```

### Pass Criteria
- [ ] Extraction completes without errors
- [ ] .dev-artifacts/ contains all expected files
- [ ] All containers are healthy
- [ ] Playwright login test passes

---

## Test 2: WebAPI Java Change

**Purpose**: Verify Java changes to webapi are reflected after build.

### Preparation

Identify a Java file to modify in the webapi module. Good candidate:
- `backend/metasfresh-webui-api/src/main/java/de/metas/ui/web/config/WebConfig.java`

### Steps

```bash
# 1. Note the current state of the file
cat backend/metasfresh-webui-api/src/main/java/de/metas/ui/web/config/WebConfig.java | head -50

# 2. Add a test log statement (manually edit the file)
# Add this line in a method that gets called on startup:
# System.out.println("DEV_MODE_TEST_WEBAPI: " + System.currentTimeMillis());

# 3. Build and update
./misc/dev-support/docker/scripts/mf-docker.sh build webapi

# 4. Check container logs for the test message
docker logs mfstack-webapi-1 2>&1 | grep "DEV_MODE_TEST_WEBAPI"

# 5. Verify stack still works
cd e2e/frontend-webui && npx playwright test tests/spec/login.spec.js
```

### Expected Output

- Maven build completes successfully
- JAR is copied to .dev-artifacts/webapi/
- Container restarts and becomes healthy
- Test message appears in container logs (if added to startup code)

### Cleanup

```bash
# Revert the test change
git checkout backend/metasfresh-webui-api/src/main/java/de/metas/ui/web/config/WebConfig.java
```

### Pass Criteria
- [ ] Build completes without errors
- [ ] JAR is copied to .dev-artifacts/webapi/
- [ ] Container restarts and becomes healthy
- [ ] Playwright test still passes

---

## Test 3: App Java Change

**Purpose**: Verify Java changes to the app server are reflected after build.

### Preparation

Identify a Java file to modify in the app module. Good candidate:
- `backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/logging/LogManager.java`

### Steps

```bash
# 1. Build the app and restart
./misc/dev-support/docker/scripts/mf-docker.sh build app

# 2. Check container health
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack status

# 3. Verify stack still works
cd e2e/frontend-webui && npx playwright test tests/spec/login.spec.js
```

### Expected Output

- Maven build completes successfully
- JAR is copied to .dev-artifacts/app/
- Container restarts and becomes healthy

### Pass Criteria
- [ ] Build completes without errors
- [ ] JAR is copied to .dev-artifacts/app/
- [ ] Container restarts and becomes healthy
- [ ] Playwright test still passes

---

## Test 4: WebUI JS Change

**Purpose**: Verify React frontend changes are reflected after build.

### Preparation

The frontend build requires a full yarn build-prod which creates the dist folder.

### Steps

```bash
# 1. Build frontend assets
./misc/dev-support/docker/scripts/mf-docker.sh build webui

# 2. Verify assets were copied
ls -la .dev-artifacts/webui/

# 3. Check that config.js exists (critical for frontend to work)
cat .dev-artifacts/webui/config.js

# 4. Open browser and verify frontend loads
# http://localhost:3000

# 5. Verify stack still works
cd e2e/frontend-webui && npx playwright test tests/spec/login.spec.js
```

### Expected Output

- Yarn build completes successfully
- Assets are copied to .dev-artifacts/webui/
- config.js is present and correctly configured
- Frontend loads in browser (no container restart needed)

### Pass Criteria
- [ ] Build completes without errors
- [ ] Assets copied to .dev-artifacts/webui/
- [ ] config.js exists and is correct
- [ ] Frontend loads at http://localhost:3000
- [ ] Playwright test still passes

---

## Test 5: Mobile JS Change

**Purpose**: Verify mobile UI changes are reflected after build.

**Note**: Mobile is NOT included in frontendtest compose.yml. This test verifies the build works but may not be able to verify runtime.

### Steps

```bash
# 1. Build mobile assets
./misc/dev-support/docker/scripts/mf-docker.sh build mobile

# 2. Verify assets were copied
ls -la .dev-artifacts/mobile/
```

### Expected Output

- Yarn build completes successfully
- Assets are copied to .dev-artifacts/mobile/

### Pass Criteria
- [ ] Build completes without errors
- [ ] Assets copied to .dev-artifacts/mobile/

---

## Test 6: JRXML Report Change

**Purpose**: Verify Jasper report files can be updated via dev-mode.

### Steps

```bash
# 1. Check if reports were extracted
ls -la .dev-artifacts/reports/

# 2. If reports exist, list JRXML files
find .dev-artifacts/reports -name "*.jrxml" | head -10

# 3. Note: To test actual report generation, would need to:
#    - Trigger a report from the UI
#    - Verify the report generates correctly
```

### Expected Output

- Reports directory exists in .dev-artifacts/
- JRXML files are present (if they exist in the CI image)

### Pass Criteria
- [ ] Reports directory exists
- [ ] JRXML files are accessible
- [ ] (Optional) Report generation works if tested

---

## Test 7: Cleanup and Restart

**Purpose**: Verify clean restart after artifacts exist.

### Steps

```bash
# 1. Stop the stack
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack stop

# 2. Start again in dev-mode
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack start --dev-mode

# 3. Verify no re-extraction (artifacts already exist)
# Should see "Using existing..." messages instead of "Extracting..."

# 4. Verify stack starts faster
# 5. Verify stack works
cd e2e/frontend-webui && npx playwright test tests/spec/login.spec.js
```

### Expected Output

- "Using existing..." messages appear (no re-extraction)
- Stack starts without extraction delay
- All containers become healthy

### Pass Criteria
- [ ] No extraction messages (artifacts reused)
- [ ] Stack starts successfully
- [ ] All containers healthy
- [ ] Playwright test passes

---

## Test 8: Force Re-extraction

**Purpose**: Verify ability to refresh artifacts from CI images.

### Steps

```bash
# 1. Stop the stack
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack stop

# 2. Delete .dev-artifacts/
rm -rf .dev-artifacts/

# 3. Start in dev-mode
./misc/dev-support/docker/scripts/mf-docker.sh e2e-stack start --dev-mode

# 4. Verify extraction happens again
# 5. Verify stack works
cd e2e/frontend-webui && npx playwright test tests/spec/login.spec.js
```

### Expected Output

- Extraction messages appear (fresh extraction)
- All artifacts are re-extracted from CI images
- Stack starts and becomes healthy

### Pass Criteria
- [ ] Extraction messages appear
- [ ] Fresh artifacts in .dev-artifacts/
- [ ] All containers healthy
- [ ] Playwright test passes

---

## Troubleshooting

### Extraction fails

```bash
# Check Docker can access images
docker pull metasfresh/metas-api:TAG
docker pull metasfresh/metas-app:TAG
docker pull metasfresh/metas-frontend:TAG

# Check image contents
docker run --rm metasfresh/metas-api:TAG ls -la /opt/metasfresh-webui-api/
docker run --rm metasfresh/metas-app:TAG ls -la /opt/metasfresh/
docker run --rm metasfresh/metas-frontend:TAG ls -la /usr/share/nginx/html/
```

### Container unhealthy after JAR replacement

```bash
# Check container logs
docker logs mfstack-webapi-1
docker logs mfstack-app-1

# Check if JAR is readable
docker exec mfstack-webapi-1 ls -la /opt/metasfresh-webui-api/metasfresh-webui-api.jar
docker exec mfstack-app-1 ls -la /opt/metasfresh/metasfresh_server.jar
```

### Frontend shows blank page

```bash
# Check config.js exists
cat .dev-artifacts/webui/config.js

# Check nginx is serving files
docker exec mfstack-webui-1 ls -la /usr/share/nginx/html/
```

---

## Notes

1. **JAR-level replacement only**: Class-level hot-reload is NOT supported. The CI images contain fat JARs (Spring Boot ZIP layout).

2. **Backend changes require restart**: Java changes require container restart (~2 min for health check).

3. **Frontend changes are immediate**: Just refresh browser - nginx serves from mounted directory.

4. **Mobile not in frontendtest compose**: Test 5 verifies build only, not runtime.

5. **Reports may be empty**: Some CI builds may not include JRXML files in the reports directory.
