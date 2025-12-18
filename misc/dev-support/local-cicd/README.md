# Local CI/CD with `act`

Run the metasfresh CI/CD pipeline locally using [act](https://github.com/nektos/act) - a tool that runs GitHub Actions workflows on your local Docker host.

## Why Run CI/CD Locally?

- **Debug test failures** without waiting for GitHub Actions
- **Iterate quickly** on build issues
- **Reproduce** the exact CI environment locally
- **Run specific jobs** in isolation (e.g., just JUnit or Cucumber)

## Prerequisites

### 1. Install `act`

```bash
# Linux
curl -sfL https://raw.githubusercontent.com/nektos/act/master/install.sh | sudo bash -s -- -b /usr/local/bin

# macOS
brew install act

# Windows (via Chocolatey)
choco install act-cli
```

Verify installation:
```bash
act --version
```

### 2. Docker Requirements

- Docker Engine 20.10+ or Docker Desktop
- At least **8GB RAM** allocated to Docker
- At least **50GB free disk space** for images
- Docker BuildKit enabled (set automatically by the script)

**Configuring Docker Desktop RAM:**

| Platform | Path |
|----------|------|
| **Windows** | Settings → Resources → Advanced → Memory slider |
| **macOS** | Settings → Resources → Advanced → Memory slider |
| **Linux** | Settings → Resources → Advanced → Memory slider (or edit `~/.docker/daemon.json`) |

In Docker Desktop: Click the gear icon (⚙️) → Resources → Advanced → Set Memory to 8GB or higher → Apply & Restart

### 3. Required Credentials

You need two tokens:

| Token | How to Get | Purpose |
|-------|------------|---------|
| Docker Hub | [hub.docker.com/settings/security](https://hub.docker.com/settings/security) → Access Tokens | Push/pull metasfresh images |
| GitHub PAT | [github.com/settings/tokens](https://github.com/settings/tokens) → Classic token with `read:packages` | Maven dependencies |

## Quick Setup

```bash
# 1. Copy the secrets template
cp .secrets.template .secrets

# 2. Edit .secrets and add your tokens
# DOCKERHUB_METASFRESH_RW_TOKEN=your_dockerhub_token
# METASFRESH_PACKAGES_READ_TOKEN=ghp_your_github_pat

# 3. Test the setup
./run-cicd.sh list
```

## Usage

### List Available Jobs

```bash
./run-cicd.sh list
```

### Run Specific Jobs

```bash
# Build Java/Maven artifacts (20-30 min, 5-10 with cache)
./run-cicd.sh java

# Build frontend images
./run-cicd.sh frontend

# Run JUnit tests (20-30 min)
./run-cicd.sh junit

# Run Cucumber integration tests (30-45 min per profile)
./run-cicd.sh cucumber

# Run health checks
./run-cicd.sh health_check

# Run Playwright mobile tests
./run-cicd.sh mobile_test

# Run Playwright frontend tests
./run-cicd.sh frontend_test
```

### Run Full Pipeline

```bash
./run-cicd.sh full
```

**Warning**: Full pipeline takes 1.5-2+ hours depending on caching.

### Advanced Options

```bash
# Verbose output (see container logs)
./run-cicd.sh junit -v

# Dry run (see what would execute)
./run-cicd.sh java --dry-run

# Run any job by name
./run-cicd.sh cucumber-build
```

## Debugging Test Failures

### 1. Check Artifacts

Test results and logs are saved to `/tmp/act-artifacts/`:

```bash
ls -la /tmp/act-artifacts/
```

### 2. Verbose Mode

Run with `-v` for detailed container output:

```bash
./run-cicd.sh junit -v
```

### 3. Database Snapshots

The CI/CD workflow creates database snapshots after tests (`docker commit`). You can inspect the test state:

```bash
# List available database snapshots
docker images | grep metas-db

# Start a post-test database
docker run -d -p 15432:5432 \
    -e PGDATA=/var/lib/postgresql/initdata \
    --name debug-db \
    metasfresh/metas-db:5.175-your_branch-postcucumber-profile1

# Connect with psql
psql -h localhost -p 15432 -U metasfresh -d metasfresh

# Cleanup when done
docker stop debug-db && docker rm debug-db
```

### 4. Run Single Cucumber Test

To run a specific Cucumber scenario, you'll need to modify the workflow temporarily or use the matrix parameter:

```bash
# Run specific Cucumber profile
act -j cucumber-run --matrix profile:profile1 \
    --secret-file .secrets
```

## Estimated Run Times

| Job | Duration | With Cache |
|-----|----------|------------|
| `java` | 20-30 min | 5-10 min |
| `frontend` | 5-10 min | 3-5 min |
| `junit` | 20-30 min | - |
| `cucumber` (1 profile) | 30-45 min | - |
| `cucumber` (all 10) | 5-8 hours | - |
| `health_check` | 5-10 min | - |
| Full pipeline | 1.5-2 hours | - |

## Troubleshooting

### "act is not installed"

Follow the installation instructions above.

### "Docker daemon is not running"

Start Docker:
```bash
# Linux
sudo systemctl start docker

# macOS/Windows
# Start Docker Desktop
```

### "Secrets file not found"

```bash
cp .secrets.template .secrets
# Edit .secrets with your credentials
```

### Out of disk space

```bash
# Clean up Docker resources
docker system prune -a --volumes
```

### Build cache issues

```bash
# Force clean build (no cache)
docker builder prune -a
./run-cicd.sh java
```

### Permission denied on secrets

```bash
chmod 600 .secrets
```

## Files

| File | Purpose |
|------|---------|
| `.actrc` | act configuration (runner image, artifact path, etc.) |
| `.secrets.template` | Template for credentials (copy to `.secrets`) |
| `.secrets` | Your credentials (gitignored, never commit!) |
| `run-cicd.sh` | Wrapper script for common commands |
| `README.md` | This documentation |

## Resources

- [act documentation](https://nektosact.com/)
- [act GitHub repository](https://github.com/nektos/act)
- [GitHub Actions workflow syntax](https://docs.github.com/en/actions/reference/workflow-syntax-for-github-actions)
