#!/bin/bash

# Script: replay_api_calls.sh
# Purpose: Replay API calls from stored audit data to a different host

set -euo pipefail

SCRIPT_START_TIME=$(date '+%Y-%m-%d %H:%M:%S')
SCRIPT_START_EPOCH=$(date +%s)
LOG_FILE=""

# Function to display usage
usage() {
    cat << EOF
Usage: $0 -h TARGET_HOST -t AUTH_TOKEN -d DATA_FOLDER

Parameters:
  -h TARGET_HOST    Target hostname/URL for API calls
  -t AUTH_TOKEN     Authorization token for API calls
  -d DATA_FOLDER    Folder containing JSON data files from first script

Example:
  $0 -h https://api.example.com -t "Bearer your-token-here" -d /tmp/audit_data/api_request_audit_2024-01-01_00-00-00
EOF
    exit 1
}

# Function for logging
log() {
    local message="$1"
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    echo "[$timestamp] $message"
    if [[ -n "$LOG_FILE" && -f "$LOG_FILE" ]]; then
        echo "[$timestamp] $message" >> "$LOG_FILE"
    fi
}

# Parse command line arguments
while getopts "h:t:d:" opt; do
    case $opt in
        h) TARGET_HOST="$OPTARG" ;;
        t) AUTH_TOKEN="$OPTARG" ;;
        d) DATA_FOLDER="$OPTARG" ;;
        *) usage ;;
    esac
done

# Check required parameters
if [[ -z "${TARGET_HOST:-}" || -z "${AUTH_TOKEN:-}" || -z "${DATA_FOLDER:-}" ]]; then
    echo "Error: Missing required parameters"
    usage
fi

# Validate data folder exists
if [[ ! -d "$DATA_FOLDER" ]]; then
    echo "Error: Data folder does not exist: $DATA_FOLDER"
    exit 1
fi

# Set up log file
LOG_FILE="$DATA_FOLDER/replay_api_calls.log"
touch "$LOG_FILE"

log "Starting API call replay"
log "Target Host: $TARGET_HOST"
log "Data Folder: $DATA_FOLDER"
log "Auth Token: [HIDDEN]"

# Remove trailing slash from target host if present
TARGET_HOST=$(echo "$TARGET_HOST" | sed 's:/*$::')

# Counters
TOTAL_CALLS=0
SUCCESSFUL_CALLS=0
FAILED_CALLS=0

# Process each JSON file
for json_file in "$DATA_FOLDER"/*.json; do
    if [[ -f "$json_file" ]]; then
        TOTAL_CALLS=$((TOTAL_CALLS + 1))
        
        # Extract data from JSON file
        audit_id=$(jq -r '.api_request_audit_id' "$json_file")
        method=$(jq -r '.method' "$json_file")
        requesturi=$(jq -r '.requesturi' "$json_file")
        body=$(jq -r '.body' "$json_file")
        
        # Skip if any field is null
        if [[ "$method" == "null" || "$requesturi" == "null" ]]; then
            log "SKIP: Invalid data in file $(basename "$json_file")"
            continue
        fi
        
        # Construct full URL
        if [[ "$requesturi" == /* ]]; then
            # requesturi starts with /, use as-is
            full_url="${TARGET_HOST}${requesturi}"
        else
            # requesturi doesn't start with /, add /
            full_url="${TARGET_HOST}/${requesturi}"
        fi
        
        log "Processing audit ID: $audit_id - $method $requesturi"
        
        # Prepare curl command
        curl_args=(
            -X "$method"
            -H "Authorization: $AUTH_TOKEN"
            -H "Accept: application/json"
            -H "Content-Type: application/json"
            -w "status_code:%{http_code};time_total:%{time_total};time_connect:%{time_connect}"
            -o /dev/null
            -s
        )
        
        # Add body if not null and not empty
        if [[ "$body" != "null" && -n "$body" && "$body" != '""' ]]; then
            # Remove outer quotes if present and treat as raw JSON
            clean_body=$(echo "$body" | sed 's/^"//;s/"$//' | sed 's/\\"/"/g')
            curl_args+=(-d "$clean_body")
        fi
        
        # Add URL
        curl_args+=("$full_url")
        
        # Execute API call and capture timing
        call_start_time=$(date +%s.%N)
        response=$(curl "${curl_args[@]}" 2>&1 || true)
        call_end_time=$(date +%s.%N)
        
        # Parse response
        status_code=$(echo "$response" | grep -o 'status_code:[0-9]*' | cut -d: -f2 || echo "000")
        time_total=$(echo "$response" | grep -o 'time_total:[0-9.]*' | cut -d: -f2 || echo "0")
        time_connect=$(echo "$response" | grep -o 'time_connect:[0-9.]*' | cut -d: -f2 || echo "0")
        
        # Calculate call duration
        call_duration=$(echo "$call_end_time - $call_start_time" | bc -l 2>/dev/null || echo "0")
        
        # Log result
        if [[ "$status_code" =~ ^[2-3][0-9][0-9]$ ]]; then
            SUCCESSFUL_CALLS=$((SUCCESSFUL_CALLS + 1))
            log "SUCCESS: ID=$audit_id, Status=$status_code, Duration=${call_duration}s, Total=${time_total}s, Connect=${time_connect}s"
        else
            FAILED_CALLS=$((FAILED_CALLS + 1))
            log "FAILED: ID=$audit_id, Status=$status_code, Duration=${call_duration}s, URL=$full_url"
        fi
        
    fi
done

# Calculate total duration
SCRIPT_END_EPOCH=$(date +%s)
TOTAL_DURATION=$((SCRIPT_END_EPOCH - SCRIPT_START_EPOCH))

# Final summary
log "=================================="
log "API Replay Summary"
log "Started: $SCRIPT_START_TIME"
log "Total Duration: ${TOTAL_DURATION} seconds"
log "Total Calls: $TOTAL_CALLS"
log "Successful Calls: $SUCCESSFUL_CALLS"
log "Failed Calls: $FAILED_CALLS"
if [[ $TOTAL_CALLS -gt 0 ]]; then
    SUCCESS_RATE=$(echo "scale=2; $SUCCESSFUL_CALLS * 100 / $TOTAL_CALLS" | bc -l 2>/dev/null || echo "0")
    log "Success Rate: ${SUCCESS_RATE}%"
fi
log "Log file: $LOG_FILE"
log "=================================="

echo "Replay completed. Check log file for details: $LOG_FILE"