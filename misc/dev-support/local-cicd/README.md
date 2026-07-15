# Local CI/CD with `act`

Run the metasfresh CI/CD pipeline locally using [act](https://github.com/nektos/act) - a tool that runs GitHub Actions workflows on your local Docker host.

## Why Run CI/CD Locally?

- **Debug test failures** without waiting for GitHub Actions
- **Iterate quickly** on build issues
- **Reproduce** the exact CI environment locally
- **Run specific jobs** in isolation (e.g., just JUnit or Cucumber)

## Prerequisites

### 1. Install `act`

#### Windows 11 (Recommended Methods)

**Option A - Chocolatey (recommended):**
```powershell
choco install act-cli
```

**Option B - Scoop:**
```powershell
scoop install act
```

**Option C - WinGet:**
```powershell
winget install nektos.act
```

**Option D - Manual download:**
1. Download from [act releases](https://github.com/nektos/act/releases)
2. Extract `act_Windows_x86_64.zip`
3. Add the extracted folder to your PATH

#### Linux/macOS

```bash
# Linux
curl -sfL https://raw.githubusercontent.com/nektos/act/master/install.sh | sudo bash -s -- -b /usr/local/bin

# macOS
brew install act
```

**Verify installation:**
```powershell
# Windows
act --version

# Linux/macOS
act --version
```

### 2. Docker Requirements

- **Docker Desktop 4.x+** (Windows/macOS) or Docker Engine 20.10+ (Linux)
- At least **8GB RAM** allocated to Docker
- At least **50GB free disk space** for images
- Docker BuildKit enabled (set automatically by the scripts)

#### Windows 11 Docker Desktop Configuration

1. Open Docker Desktop
2. Click the gear icon (⚙️) → **Settings**
3. Navigate to **Resources** → **Advanced**
4. Set **Memory** to 8GB or higher
5. Set **Disk image size** to at least 50GB
6. Click **Apply & Restart**

**Important Windows Settings:**

| Setting | Recommended Value |
|---------|-------------------|
| Memory | 8GB+ |
| CPUs | 4+ |
| Disk image size | 50GB+ |
| WSL 2 backend | Enabled (default) |

#### Linux Docker Configuration

Edit `/etc/docker/daemon.json` or use Docker Desktop settings if available.

### 3. Required Credentials

You need two tokens:

| Token | How to Get | Purpose |
|-------|------------|---------|
| Docker Hub | [hub.docker.com/settings/security](https://hub.docker.com/settings/security) → Access Tokens | Push/pull metasfresh images |
| GitHub PAT | [github.com/settings/tokens](https://github.com/settings/tokens) → Classic token with `read:packages` | Maven dependencies |

## Quick Setup

### Windows 11 (PowerShell)

```powershell
# 1. Copy the secrets template
Copy-Item .secrets.template .secrets

# 2. Edit .secrets and add your tokens (use your preferred editor)
notepad .secrets
# Or: code .secrets (VS Code)

# 3. Test the setup
.\run-cicd.ps1 list
```

### Linux/macOS (Bash)

```bash
# 1. Copy the secrets template
cp .secrets.template .secrets

# 2. Edit .secrets and add your tokens
nano .secrets  # or vim, etc.

# 3. Test the setup
./run-cicd.sh list
```

## Usage

### Windows 11 (PowerShell)

#### List Available Jobs

```powershell
.\run-cicd.ps1 list
```

#### Run Specific Jobs

```powershell
# Build Java/Maven artifacts (20-30 min, 5-10 with cache)
.\run-cicd.ps1 java

# Build frontend images
.\run-cicd.ps1 frontend

# Run JUnit tests (20-30 min)
.\run-cicd.ps1 junit

# Run Cucumber integration tests (30-45 min per profile)
.\run-cicd.ps1 cucumber

# Run health checks
.\run-cicd.ps1 health_check

# Run Playwright mobile tests
.\run-cicd.ps1 mobile_test

# Run Playwright frontend tests
.\run-cicd.ps1 frontend_test
```

#### Run Full Pipeline

```powershell
.\run-cicd.ps1 full
```

**Warning**: Full pipeline takes 1.5-2+ hours depending on caching.

#### Advanced Options

```powershell
# Verbose output (see container logs)
.\run-cicd.ps1 junit -Verbose

# Dry run (see what would execute)
.\run-cicd.ps1 java -DryRun

# Run any job by name
.\run-cicd.ps1 cucumber-build
```

### Linux/macOS (Bash)

#### List Available Jobs

```bash
./run-cicd.sh list
```

#### Run Specific Jobs

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

#### Run Full Pipeline

```bash
./run-cicd.sh full
```

#### Advanced Options

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

Test results and logs are saved to:

| Platform | Path |
|----------|------|
| **Windows** | `%TEMP%\act-artifacts\` |
| **Linux/macOS** | `/tmp/act-artifacts/` |

```powershell
# Windows
Get-ChildItem $env:TEMP\act-artifacts

# Linux/macOS
ls -la /tmp/act-artifacts/
```

### 2. Verbose Mode

Run with verbose flag for detailed container output:

```powershell
# Windows
.\run-cicd.ps1 junit -Verbose

# Linux/macOS
./run-cicd.sh junit -v
```

### 3. Database Snapshots

The CI/CD workflow creates database snapshots after tests (`docker commit`). You can inspect the test state:

```powershell
# List available database snapshots
docker images | Select-String "metas-db"

# Start a post-test database
docker run -d -p 15432:5432 `
    -e PGDATA=/var/lib/postgresql/initdata `
    --name debug-db `
    metasfresh/metas-db:5.175-your_branch-postcucumber-profile1

# Connect with psql (if installed)
psql -h localhost -p 15432 -U metasfresh -d metasfresh

# Or use Docker exec
docker exec -it debug-db psql -U metasfresh -d metasfresh

# Cleanup when done
docker stop debug-db; docker rm debug-db
```

### 4. Run Single Cucumber Profile

To run a specific Cucumber profile:

```powershell
# Windows
act -j cucumber-run --matrix profile:profile1 --secret-file .secrets -C (Resolve-Path ..\..\..| Select-Object -ExpandProperty Path)

# Linux/macOS
act -j cucumber-run --matrix profile:profile1 --secret-file .secrets
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

### Windows-Specific Issues

#### "act is not recognized"

PowerShell doesn't find `act`:
```powershell
# Check if act is in PATH
Get-Command act

# If not, add it to PATH or reinstall via Chocolatey
choco install act-cli --force
```

#### Execution Policy Error

If PowerShell blocks the script:
```powershell
# Option 1: Allow for current session
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope Process

# Option 2: Run with bypass
powershell -ExecutionPolicy Bypass -File .\run-cicd.ps1 list
```

#### Long Path Issues

Windows has a 260-character path limit by default. Enable long paths:
```powershell
# Run as Administrator
Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Control\FileSystem" -Name "LongPathsEnabled" -Value 1
```

Then restart Docker Desktop.

#### WSL 2 Integration Issues

If Docker can't communicate with WSL:
1. Open Docker Desktop Settings
2. Navigate to **Resources** → **WSL Integration**
3. Enable integration with your distro
4. Restart Docker Desktop

### General Issues

#### "act is not installed"

Follow the installation instructions above for your platform.

#### "Docker daemon is not running"

Start Docker:
```powershell
# Windows: Start Docker Desktop from Start menu
# Or via PowerShell (if Docker is in PATH):
Start-Process "Docker Desktop"

# Linux
sudo systemctl start docker
```

#### "Secrets file not found"

```powershell
# Windows
Copy-Item .secrets.template .secrets
notepad .secrets

# Linux/macOS
cp .secrets.template .secrets
nano .secrets
```

#### Out of disk space

```powershell
# Clean up Docker resources
docker system prune -a --volumes
```

#### Build cache issues

```powershell
# Force clean build (no cache)
docker builder prune -a
.\run-cicd.ps1 java  # or ./run-cicd.sh java
```

#### Permission denied on secrets (Linux/macOS)

```bash
chmod 600 .secrets
```

## Files

| File | Purpose |
|------|---------|
| `.actrc` | act configuration (runner image, artifact path, etc.) |
| `.secrets.template` | Template for credentials (copy to `.secrets`) |
| `.secrets` | Your credentials (gitignored, never commit!) |
| `run-cicd.ps1` | **Windows** PowerShell wrapper script |
| `run-cicd.sh` | **Linux/macOS** Bash wrapper script |
| `README.md` | This documentation |

## Platform Comparison

| Feature | Windows (PowerShell) | Linux/macOS (Bash) |
|---------|---------------------|-------------------|
| Script | `.\run-cicd.ps1` | `./run-cicd.sh` |
| Verbose flag | `-Verbose` | `-v` |
| Dry-run flag | `-DryRun` | `--dry-run` |
| Artifact path | `%TEMP%\act-artifacts` | `/tmp/act-artifacts` |
| Docker backend | Docker Desktop (WSL 2) | Docker Engine |

## Resources

- [act documentation](https://nektosact.com/)
- [act GitHub repository](https://github.com/nektos/act)
- [GitHub Actions workflow syntax](https://docs.github.com/en/actions/reference/workflow-syntax-for-github-actions)
- [Docker Desktop for Windows](https://docs.docker.com/desktop/install/windows-install/)
