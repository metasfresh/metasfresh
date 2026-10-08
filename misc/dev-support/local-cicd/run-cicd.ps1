#Requires -Version 5.1
<#
.SYNOPSIS
    Run CI/CD pipeline locally using act

.DESCRIPTION
    Runs the metasfresh CI/CD pipeline locally using act (GitHub Actions runner).

.PARAMETER Command
    The command to execute:
    - list          List all available jobs
    - full          Run entire CI/CD pipeline
    - java          Build Java/Maven artifacts
    - frontend      Build frontend images
    - backend       Build backend runtime images
    - junit         Run JUnit tests
    - cucumber      Run Cucumber integration tests
    - cucumber-build Build Cucumber test container
    - health_check  Run health checks
    - mobile_test   Run Playwright mobile tests
    - frontend_test Run Playwright frontend tests
    - <job-name>    Run any specific job by name

.PARAMETER Verbose
    Enable verbose output from act

.PARAMETER DryRun
    Show what would be run without executing

.EXAMPLE
    .\run-cicd.ps1 list

.EXAMPLE
    .\run-cicd.ps1 junit -Verbose

.EXAMPLE
    .\run-cicd.ps1 java -DryRun
#>

[CmdletBinding()]
param(
    [Parameter(Position = 0)]
    [string]$Command = "help",

    [switch]$DryRun
)

# Script configuration
$ErrorActionPreference = "Stop"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$RepoRoot = Resolve-Path (Join-Path $ScriptDir "..\..\..") | Select-Object -ExpandProperty Path

# Windows artifact path (use temp directory)
$ArtifactPath = Join-Path $env:TEMP "act-artifacts"

# Log file path (in repo root for easy access)
$Timestamp = Get-Date -Format "yyyy_MM_dd_HHmmss"
$LogFile = Join-Path $RepoRoot "act_output_$Timestamp.txt"

# Colors for output (PowerShell 5.1+ compatible)
function Write-Info { param([string]$Message) Write-Host "[INFO] $Message" -ForegroundColor Green }
function Write-Warn { param([string]$Message) Write-Host "[WARN] $Message" -ForegroundColor Yellow }
function Write-Err { param([string]$Message) Write-Host "[ERROR] $Message" -ForegroundColor Red }

function Test-Prerequisites {
    # Check act
    $actPath = Get-Command act -ErrorAction SilentlyContinue
    if (-not $actPath) {
        Write-Err "act is not installed. Install it with one of these methods:"
        Write-Host ""
        Write-Host "  Option 1 - Chocolatey (recommended):"
        Write-Host "    choco install act-cli"
        Write-Host ""
        Write-Host "  Option 2 - Scoop:"
        Write-Host "    scoop install act"
        Write-Host ""
        Write-Host "  Option 3 - WinGet:"
        Write-Host "    winget install nektos.act"
        Write-Host ""
        Write-Host "  Option 4 - Manual download:"
        Write-Host "    https://github.com/nektos/act/releases"
        exit 1
    }

    # Check Docker
    $dockerPath = Get-Command docker -ErrorAction SilentlyContinue
    if (-not $dockerPath) {
        Write-Err "docker is not installed or not in PATH"
        Write-Host "  Install Docker Desktop from: https://www.docker.com/products/docker-desktop"
        exit 1
    }

    # Check if Docker daemon is running
    # Temporarily allow errors so stderr output from docker doesn't throw
    $oldErrorAction = $ErrorActionPreference
    $ErrorActionPreference = "SilentlyContinue"
    $null = & docker info 2>&1
    $dockerExitCode = $LASTEXITCODE
    $ErrorActionPreference = $oldErrorAction

    if ($dockerExitCode -ne 0) {
        Write-Err "Docker daemon is not running"
        Write-Host "  Start Docker Desktop from the Windows Start menu"
        exit 1
    }

    # Check secrets file
    $secretsFile = Join-Path $ScriptDir ".secrets"
    if (-not (Test-Path $secretsFile)) {
        Write-Err "Secrets file not found: $secretsFile"
        Write-Host "  Copy .secrets.template to .secrets and fill in your credentials:"
        Write-Host "  Copy-Item `"$ScriptDir\.secrets.template`" `"$ScriptDir\.secrets`""
        exit 1
    }

    # Ensure artifact directory exists
    if (-not (Test-Path $ArtifactPath)) {
        New-Item -ItemType Directory -Path $ArtifactPath -Force | Out-Null
    }
}

function Show-Help {
    @"
Usage: .\run-cicd.ps1 <command> [options]

Commands:
  list              List all available jobs in the workflow
  full              Run the entire CI/CD pipeline
  java              Build Java/Maven artifacts (metas-mvn-common, backend, camel)
  frontend          Build frontend and mobile images
  backend           Build runtime Docker images (api, app, db, etc.)
  junit             Run JUnit unit tests
  cucumber          Run Cucumber integration tests (all profiles)
  cucumber-build    Build the Cucumber test container
  health_check      Run health checks on all services
  mobile_test       Run Playwright mobile E2E tests
  frontend_test     Run Playwright frontend E2E tests
  <job-name>        Run any specific job by name (use 'list' to see all)

Options:
  -Verbose          Enable verbose output
  -DryRun           Show what would be run without executing

Examples:
  .\run-cicd.ps1 list                    # See all available jobs
  .\run-cicd.ps1 junit                   # Run JUnit tests
  .\run-cicd.ps1 cucumber -Verbose       # Run Cucumber with verbose output
  .\run-cicd.ps1 java -DryRun            # See what Java build would do

Output:
  - Console output is also saved to: <repo>/act_output_<timestamp>.txt
  - Artifacts are saved to: %TEMP%\act-artifacts

Environment:
  DOCKER_BUILDKIT=1          Enable BuildKit (set automatically)
"@
}

function Invoke-Act {
    param(
        [string[]]$AdditionalArgs = @()
    )

    # Common act arguments
    # Note: Using 'act-latest' image instead of 'full-latest' for proper Docker socket permissions
    # See: https://github.com/nektos/act/issues/2616
    $actArgs = @(
        "--secret-file", (Join-Path $ScriptDir ".secrets"),
        "-C", $RepoRoot,
        "-P", "ubuntu-latest=ghcr.io/catthehacker/ubuntu:act-latest",
        "--artifact-server-path=$ArtifactPath",
        "--env", "GITHUB_RUN_NUMBER=1",
        "--var", "RETRY_ATTEMPTS=3",
        "--var", "RETRY_DELAY=5",
        "--var", "RETRY_TIMEOUT=10"
    )
    # Note: ACT environment variable is automatically set by 'act' when running locally
    # The workflow uses 'env.ACT != true' to skip Docker pushes when running locally

    # Add verbose flag if requested
    if ($VerbosePreference -eq 'Continue') {
        $actArgs += "-v"
    }

    # Add dry-run flag if requested
    if ($DryRun) {
        $actArgs += "--dryrun"
    }

    # Add any additional arguments
    $actArgs += $AdditionalArgs

    # Enable BuildKit
    $env:DOCKER_BUILDKIT = "1"

    Write-Verbose "Running: act $($actArgs -join ' ')"
    Write-Info "Output will be logged to: $LogFile"

    # Set UTF-8 encoding for proper emoji display
    $originalOutputEncoding = [Console]::OutputEncoding
    $originalPSOutputEncoding = $OutputEncoding
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
    $OutputEncoding = [System.Text.Encoding]::UTF8

    # Also set console code page to UTF-8
    $null = & chcp 65001

    # Build act arguments string for cmd.exe
    $actArgsString = ($actArgs | ForEach-Object {
        if ($_ -match '\s') { "`"$_`"" } else { $_ }
    }) -join ' '

    # Write command to log file
    "=== act run started at $(Get-Date) ===" | Out-File -FilePath $LogFile -Encoding utf8
    "Command: act $actArgsString" | Out-File -FilePath $LogFile -Append -Encoding utf8
    "Working directory: $RepoRoot" | Out-File -FilePath $LogFile -Append -Encoding utf8
    "=" * 60 | Out-File -FilePath $LogFile -Append -Encoding utf8
    "" | Out-File -FilePath $LogFile -Append -Encoding utf8

    # Run act with output to both console and file
    # Use cmd.exe with UTF-8 codepage for proper emoji display
    $oldErrorActionPreference = $ErrorActionPreference
    $ErrorActionPreference = 'SilentlyContinue'
    & cmd /c "chcp 65001 >nul & act $actArgsString" 2>&1 | ForEach-Object {
        $line = $_.ToString()
        Write-Host $line
        $line | Out-File -FilePath $LogFile -Append -Encoding utf8
    }
    $exitCode = $LASTEXITCODE
    $ErrorActionPreference = $oldErrorActionPreference

    # Restore original encoding
    [Console]::OutputEncoding = $originalOutputEncoding
    $OutputEncoding = $originalPSOutputEncoding

    # Append summary to log
    "" | Out-File -FilePath $LogFile -Append -Encoding utf8
    "=" * 60 | Out-File -FilePath $LogFile -Append -Encoding utf8
    "=== act run finished at $(Get-Date) with exit code $exitCode ===" | Out-File -FilePath $LogFile -Append -Encoding utf8

    if ($exitCode -ne 0) {
        Write-Err "act command failed with exit code $exitCode"
        Write-Info "Full output saved to: $LogFile"
        exit $exitCode
    }
}

# Main script logic
switch ($Command.ToLower()) {
    { $_ -in @("help", "-h", "--help", "?") } {
        Show-Help
        exit 0
    }

    "list" {
        Write-Info "Available jobs in cicd.yaml:"
        & act -l -C $RepoRoot
        exit 0
    }

    "full" {
        Test-Prerequisites
        Write-Info "Running full CI/CD pipeline..."
        Write-Warn "This may take 1-2 hours depending on caching"
        Invoke-Act
    }

    "java" {
        Test-Prerequisites
        Write-Info "Building Java/Maven artifacts..."
        Invoke-Act -AdditionalArgs @("-j", "java")
    }

    "frontend" {
        Test-Prerequisites
        Write-Info "Building frontend images..."
        Invoke-Act -AdditionalArgs @("-j", "frontend")
    }

    "backend" {
        Test-Prerequisites
        Write-Info "Building backend runtime images..."
        Invoke-Act -AdditionalArgs @("-j", "backend")
    }

    "junit" {
        Test-Prerequisites
        Write-Info "Running JUnit tests..."
        Invoke-Act -AdditionalArgs @("-j", "junit")
    }

    "cucumber" {
        Test-Prerequisites
        Write-Info "Running Cucumber integration tests..."
        Write-Warn "This runs all 10 profiles sequentially (may take several hours)"
        Invoke-Act -AdditionalArgs @("-j", "cucumber-run")
    }

    "cucumber-build" {
        Test-Prerequisites
        Write-Info "Building Cucumber test container..."
        Invoke-Act -AdditionalArgs @("-j", "cucumber-build")
    }

    "health_check" {
        Test-Prerequisites
        Write-Info "Running health checks..."
        Invoke-Act -AdditionalArgs @("-j", "health_check")
    }

    "mobile_test" {
        Test-Prerequisites
        Write-Info "Running Playwright mobile tests..."
        Invoke-Act -AdditionalArgs @("-j", "mobile_test")
    }

    "frontend_test" {
        Test-Prerequisites
        Write-Info "Running Playwright frontend tests..."
        Invoke-Act -AdditionalArgs @("-j", "frontend_webui_test")
    }

    default {
        # Try to run it as a job name
        Test-Prerequisites
        Write-Info "Running job: $Command"
        Invoke-Act -AdditionalArgs @("-j", $Command)
    }
}

Write-Info "Done!"
Write-Info "Artifacts available at: $ArtifactPath"
Write-Info "Full output saved to: $LogFile"
