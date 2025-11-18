#!/usr/bin/env bash

#
# %L
# master
# %%
# Copyright (C) 2025 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

set -e

# ==================== Configuration ====================

# Create timestamped run folder
RUN_TIMESTAMP=$(date +%Y-%m-%d_%H-%M-%S)
RUN_DIR="./runs/${RUN_TIMESTAMP}"
mkdir -p "$RUN_DIR"

# Load configuration from file if it exists
CONFIG_FILE="${CONFIG_FILE:-./audit-replay.env}"
if [ -f "$CONFIG_FILE" ]; then
  echo "Loading configuration from $CONFIG_FILE"
  set -a
  source "$CONFIG_FILE"
  set +a

  # Copy the config file to the run directory for reference
  cp "$CONFIG_FILE" "$RUN_DIR/audit-replay.env"
  echo "✓ Configuration saved to: $RUN_DIR/audit-replay.env"
else
  echo "No configuration file found at $CONFIG_FILE"
  echo "Using environment variables or defaults"
fi

# Source Database Configuration
SOURCE_DB_HOST="${SOURCE_DB_HOST:-localhost}"
SOURCE_DB_PORT="${SOURCE_DB_PORT:-5432}"
SOURCE_DB_NAME="${SOURCE_DB_NAME:-metasfresh}"
SOURCE_DB_USER="${SOURCE_DB_USER:-metasfresh}"
SOURCE_DB_PASSWORD="${SOURCE_DB_PASSWORD:-metasfresh}"

# Extraction Filters
MIN_ID="${MIN_ID:-}"
MAX_ID="${MAX_ID:-}"
START_TIME="${START_TIME:-}"
END_TIME="${END_TIME:-}"
PATH_FILTER="${PATH_FILTER:-/api/v2/%}"
STATUS_FILTER="${STATUS_FILTER:-PROCESSED}"
MAX_REQUESTS="${MAX_REQUESTS:-10000}"
EXCLUDE_HEADERS="${EXCLUDE_HEADERS:-Host,Connection,Content-Length,User-Agent}"
INCLUDE_RESPONSES="${INCLUDE_RESPONSES:-true}"

# Output - Override old default paths to use run directory
# If user has custom paths, keep them; otherwise use run-specific paths
if [ -z "$EXTRACTED_DATA_FILE" ] || [ "$EXTRACTED_DATA_FILE" = "./test-data/extracted-api-audit.json" ]; then
  EXTRACTED_DATA_FILE="$RUN_DIR/extracted-api-audit.json"
fi
if [ -z "$RESULTS_FILE" ] || [ "$RESULTS_FILE" = "./results/audit-replay-results.json" ]; then
  RESULTS_FILE="$RUN_DIR/audit-replay-results.json"
fi
if [ -z "$HTML_REPORT" ] || [ "$HTML_REPORT" = "./results/audit-replay-report.html" ]; then
  HTML_REPORT="$RUN_DIR/audit-replay-report.html"
fi

# Target Instance Configuration
TARGET_BASE_URL="${TARGET_BASE_URL:-http://localhost:8282/api/v2}"
TARGET_AUTH_TOKEN="${TARGET_AUTH_TOKEN:-}"

# Load Test Configuration
VUS="${VUS:-50}"
DURATION="${DURATION:-300s}"
ITERATIONS="${ITERATIONS:-0}"  # 0=duration mode, >0=iterations, 'auto'=match request count
THINK_TIME="${THINK_TIME:-1}"
REPLAY_MODE="${REPLAY_MODE:-sequential}"
COMPARE_RESPONSES="${COMPARE_RESPONSES:-false}"
REPLACE_AUTH_TOKEN="${REPLACE_AUTH_TOKEN:-true}"

# Operational Flags
SKIP_EXTRACTION="${SKIP_EXTRACTION:-false}"
SKIP_REPLAY="${SKIP_REPLAY:-false}"

# Debug Flags
DEBUG="${DEBUG:-false}"
SHOW_SQL="${SHOW_SQL:-false}"

# ==================== Functions ====================

print_banner() {
  echo ""
  echo "=========================================="
  echo "$1"
  echo "=========================================="
  echo ""
}

print_config() {
  print_banner "Configuration"
  echo "Run Directory: ${RUN_DIR}"
  echo ""
  echo "Source Database:"
  echo "  Host: ${SOURCE_DB_HOST}:${SOURCE_DB_PORT}"
  echo "  Database: ${SOURCE_DB_NAME}"
  echo "  User: ${SOURCE_DB_USER}"
  echo ""
  echo "Extraction Filters:"
  if [ -n "$MIN_ID" ] || [ -n "$MAX_ID" ]; then
    echo "  ID Range: ${MIN_ID:-No min} - ${MAX_ID:-No max}"
  fi
  echo "  Start Time: ${START_TIME:-Not set}"
  echo "  End Time: ${END_TIME:-Not set}"
  echo "  Path Filter: ${PATH_FILTER}"
  echo "  Status: ${STATUS_FILTER}"
  echo "  Max Requests: ${MAX_REQUESTS}"
  echo "  Include Responses: ${INCLUDE_RESPONSES}"
  echo ""
  echo "Target Instance:"
  echo "  Base URL: ${TARGET_BASE_URL}"
  if [ -n "$TARGET_AUTH_TOKEN" ]; then
    echo "  Auth Token: Set (hidden)"
  else
    echo "  Auth Token: Not set"
  fi
  echo ""
  echo "Load Test:"
  echo "  VUs: ${VUS}"
  if [ "$ITERATIONS" != "0" ]; then
    echo "  Mode: ITERATIONS (${ITERATIONS})"
  else
    echo "  Mode: DURATION (${DURATION})"
  fi
  echo "  Think Time: ${THINK_TIME}s"
  echo "  Replay Mode: ${REPLAY_MODE}"
  echo "  Compare Responses: ${COMPARE_RESPONSES}"
  echo ""
  echo "Output:"
  echo "  Extracted Data: ${EXTRACTED_DATA_FILE}"
  echo "  Results File: ${RESULTS_FILE}"
  echo "  HTML Report: ${HTML_REPORT}"
  echo ""
}

extract_audit_data() {
  print_banner "Step 1: Extracting API Audit Data"

  if [ "$SKIP_EXTRACTION" = "true" ]; then
    echo "Skipping extraction (SKIP_EXTRACTION=true)"
    if [ ! -f "$EXTRACTED_DATA_FILE" ]; then
      echo "ERROR: Extracted data file not found: $EXTRACTED_DATA_FILE"
      exit 1
    fi
    return
  fi

  # Ensure output directory exists
  mkdir -p "$(dirname "$EXTRACTED_DATA_FILE")"

  # Build extraction command using arrays to avoid path conversion issues
  EXTRACT_ARGS=(
    "--db-host" "$SOURCE_DB_HOST"
    "--db-port" "$SOURCE_DB_PORT"
    "--db-name" "$SOURCE_DB_NAME"
    "--db-user" "$SOURCE_DB_USER"
    "--db-password" "$SOURCE_DB_PASSWORD"
    "--path-filter" "$PATH_FILTER"
    "--status" "$STATUS_FILTER"
    "--output" "$EXTRACTED_DATA_FILE"
    "--limit" "$MAX_REQUESTS"
    "--exclude-headers" "$EXCLUDE_HEADERS"
  )

  if [ -n "$MIN_ID" ]; then
    EXTRACT_ARGS+=("--min-id" "$MIN_ID")
  fi

  if [ -n "$MAX_ID" ]; then
    EXTRACT_ARGS+=("--max-id" "$MAX_ID")
  fi

  if [ -n "$START_TIME" ]; then
    EXTRACT_ARGS+=("--start-time" "$START_TIME")
  fi

  if [ -n "$END_TIME" ]; then
    EXTRACT_ARGS+=("--end-time" "$END_TIME")
  fi

  if [ "$INCLUDE_RESPONSES" = "true" ]; then
    EXTRACT_ARGS+=("--include-responses")
  fi

  if [ "$DEBUG" = "true" ]; then
    EXTRACT_ARGS+=("--debug")
  fi

  if [ "$SHOW_SQL" = "true" ]; then
    EXTRACT_ARGS+=("--show-sql")
  fi

  echo "Running extraction..."

  # Disable Git Bash path conversion on Windows (prevents /api/v2/% from becoming C:/Program Files/Git/api/v2/%)
  MSYS_NO_PATHCONV=1 node extract-api-audit.js "${EXTRACT_ARGS[@]}"

  if [ $? -ne 0 ]; then
    echo "ERROR: Extraction failed"
    exit 1
  fi

  echo ""
  echo "Extraction complete!"
}

run_load_test() {
  print_banner "Step 2: Running K6 Load Test"

  if [ "$SKIP_REPLAY" = "true" ]; then
    echo "Skipping replay (SKIP_REPLAY=true)"
    return
  fi

  if [ "$SHOW_SQL" = "true" ]; then
    echo "Skipping replay (SHOW_SQL=true - debug mode)"
    return
  fi

  # Ensure results directory exists
  mkdir -p "$(dirname "$RESULTS_FILE")"

  echo "Starting k6 load test..."
  echo ""

  BASE_URL="$TARGET_BASE_URL" \
  AUTH_TOKEN="$TARGET_AUTH_TOKEN" \
  AUDIT_DATA_FILE="$EXTRACTED_DATA_FILE" \
  REPLAY_MODE="$REPLAY_MODE" \
  THINK_TIME="$THINK_TIME" \
  VUS="$VUS" \
  DURATION="$DURATION" \
  ITERATIONS="$ITERATIONS" \
  COMPARE_RESPONSES="$COMPARE_RESPONSES" \
  REPLACE_AUTH_TOKEN="$REPLACE_AUTH_TOKEN" \
  k6 run \
    --out "json=$RESULTS_FILE" \
    replay-api-audit.js

  if [ $? -ne 0 ]; then
    echo "ERROR: Load test failed"
    exit 1
  fi

  echo ""
  echo "Load test complete!"
}

generate_report() {
  print_banner "Step 3: Results Summary"

  if [ ! -f "$RESULTS_FILE" ]; then
    echo "WARNING: Results file not found: $RESULTS_FILE"
    return
  fi

  echo "✓ Results saved to: $RESULTS_FILE"
  echo ""
  echo "View results summary:"
  echo "  ./view-results.sh $RESULTS_FILE"
  echo ""
  echo "Or generate an HTML report with:"
  echo "  1. k6-reporter: https://github.com/benc-uk/k6-reporter"
  echo "  2. k6-html-reporter: npm install -g k6-html-reporter"
  echo "  3. k6 Cloud: k6 cloud login && k6 cloud $RESULTS_FILE"
}

print_summary() {
  print_banner "Summary"

  echo "Audit replay load test complete!"
  echo ""
  echo "Run directory: ${RUN_DIR}"
  echo ""
  echo "Files generated:"
  if [ -f "$EXTRACTED_DATA_FILE" ]; then
    echo "  ✓ Extracted data: $EXTRACTED_DATA_FILE"
  fi
  if [ -f "$RESULTS_FILE" ]; then
    echo "  ✓ Results (JSON): $RESULTS_FILE"
  fi
  if [ -f "$RUN_DIR/audit-replay.env" ]; then
    echo "  ✓ Configuration: $RUN_DIR/audit-replay.env"
  fi
  echo ""

  # Create "latest" symlink to this run
  LATEST_LINK="./runs/latest"
  if [ -L "$LATEST_LINK" ]; then
    rm "$LATEST_LINK"
  fi
  ln -s "$RUN_TIMESTAMP" "$LATEST_LINK" 2>/dev/null || echo "Note: Could not create 'latest' symlink (may not be supported on this system)"

  echo "Quick access:"
  echo "  Latest run: ./runs/latest"
  echo "  View results: ./view-results.sh $RESULTS_FILE"
  echo "  All runs: ls -la ./runs/"
  echo ""
}

# ==================== Main Execution ====================

main() {
  print_banner "Metasfresh API Audit Replay Load Test"

  # Check dependencies
  if ! command -v node &> /dev/null; then
    echo "ERROR: node command not found. Please install Node.js 18+."
    exit 1
  fi

  if ! command -v k6 &> /dev/null; then
    echo "ERROR: k6 command not found. Please install k6."
    exit 1
  fi

  # Ensure we're in the right directory
  cd "$(dirname "$0")"

  # Check if package.json exists and node_modules is installed
  if [ -f "package.json" ] && [ ! -d "node_modules" ]; then
    echo "Installing Node.js dependencies..."
    npm install
    echo ""
  fi

  # Print configuration
  print_config

  # Run pipeline
  extract_audit_data
  run_load_test
  generate_report
  print_summary
}

# Run main function
main "$@"
