#!/bin/bash

# Script: replay_api_calls.sh
# Purpose: Replay API calls from stored audit data to a different host

set -euo pipefail

SCRIPT_START_TIME=$(date '+%Y-%m-%d %H:%M:%S')
SCRIPT_START_EPOCH=$(date +%s)
LOG_FILE=""
DEFAULT_THREADS=1

# Function to display usage
usage() {
    cat << EOF
Usage: $0 -h TARGET_HOST -t AUTH_TOKEN -d DATA_FOLDER [-n THREADS]

Parameters:
  -h TARGET_HOST    Target hostname/URL for API calls
  -t AUTH_TOKEN     Authorization token for API calls
  -d DATA_FOLDER    Folder containing JSON data files from first script
  -n THREADS        Number of concurrent threads (default: 1)

Example:
  $0 -h https://api.example.com -t "Bearer your-token-here" -d /tmp/audit_data/api_request_audit_2024-01-01_00-00-00 -n 4
EOF
    exit 1
}

# Function for thread-safe logging using atomic operations
log() {
    local message="$1"
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    local thread_id="${2:-main}"
    local log_entry="[$timestamp] [Thread-$thread_id] $message"
    
    echo "$log_entry"
    if [[ -n "$LOG_FILE" && -f "$LOG_FILE" ]]; then
        # Use atomic write with temp file
        local temp_log="/tmp/log_$$_${thread_id}_$(date +%s%N)"
        echo "$log_entry" > "$temp_log"
        cat "$temp_log" >> "$LOG_FILE"
        rm -f "$temp_log"
    fi
}

# Thread-safe counter increment using atomic file operations
increment_counter() {
    local counter_file="$1"
    local temp_file="/tmp/counter_$$_$(date +%s%N)"
    
    # Create counter file if it doesn't exist
    if [[ ! -f "$counter_file" ]]; then
        echo "0" > "$counter_file"
    fi
    
    # Atomic increment using temporary file and mv
    local current_count=$(cat "$counter_file" 2>/dev/null || echo "0")
    local new_count=$((current_count + 1))
    echo "$new_count" > "$temp_file"
    mv "$temp_file" "$counter_file"
}

# Function to process a single API call
process_api_call() {
    local json_file="$1"
    local thread_id="$2"
    local temp_file="/tmp/headers_${thread_id}_$$_$(date +%s%N)"
    
    # Extract data from JSON file
    local audit_id=$(jq -r '.api_request_audit_id' "$json_file")
    local method=$(jq -r '.method' "$json_file")
    local requesturi=$(jq -r '.requesturi' "$json_file")
    local body=$(jq -r '.body' "$json_file")
    
    # Skip if any field is null
    if [[ "$method" == "null" || "$requesturi" == "null" ]]; then
        log "SKIP: Invalid data in file $(basename "$json_file")" "$thread_id"
        return 1
    fi
    
    # Construct full URL
    local full_url
    if [[ "$requesturi" == /* ]]; then
        # requesturi starts with /, use as-is
        full_url="${TARGET_HOST}${requesturi}"
    else
        # requesturi doesn't start with /, add /
        full_url="${TARGET_HOST}/${requesturi}"
    fi
    
    log "Processing audit ID: $audit_id - $method $requesturi" "$thread_id"
    
    # Prepare curl command
    local curl_args=(
        -X "$method"
        -H "Authorization: $AUTH_TOKEN"
        -H "Accept: application/json"
        -H "Content-Type: application/json"
        -w "status_code:%{http_code};time_total:%{time_total};time_connect:%{time_connect}"
        -D "$temp_file"  # Dump headers to temporary file
        -o /dev/null
        -s
    )
    
    # Add body if not null and not empty
    if [[ "$body" != "null" && -n "$body" && "$body" != '""' ]]; then
        # Remove outer quotes if present and treat as raw JSON
        local clean_body=$(echo "$body" | sed 's/^"//;s/"$//' | sed 's/\\"/"/g')
        curl_args+=(-d "$clean_body")
    fi
    
    # Add URL
    curl_args+=("$full_url")
    
    # Execute API call and capture timing
    local call_start_time=$(date +%s.%N)
    local response=$(curl "${curl_args[@]}" 2>&1 || true)
    local call_end_time=$(date +%s.%N)
    
    # Parse response
    local status_code=$(echo "$response" | grep -o 'status_code:[0-9]*' | cut -d: -f2 || echo "000")
    local time_total=$(echo "$response" | grep -o 'time_total:[0-9.]*' | cut -d: -f2 || echo "0")
    local time_connect=$(echo "$response" | grep -o 'time_connect:[0-9.]*' | cut -d: -f2 || echo "0")
    
    # Extract X-Api-Request-Audit-ID from response headers
    local api_audit_id_header=""
    if [[ -f "$temp_file" ]]; then
        api_audit_id_header=$(grep -i "^X-Api-Request-Audit-ID:" "$temp_file" 2>/dev/null | cut -d: -f2- | tr -d '\r\n' | sed 's/^ *//' || echo "")
        rm -f "$temp_file"
    fi
    
    # Calculate call duration
    local call_duration=$(echo "$call_end_time - $call_start_time" | bc -l 2>/dev/null || echo "0")
    
    # Prepare audit ID header info for logging
    local audit_header_info=""
    if [[ -n "$api_audit_id_header" ]]; then
        audit_header_info=", ResponseAuditID=$api_audit_id_header"
    fi
    
    # Log result and update counters atomically
    if [[ "$status_code" =~ ^[2-3][0-9][0-9]$ ]]; then
        increment_counter "$DATA_FOLDER/.success_count"
        log "SUCCESS: ID=$audit_id, Status=$status_code, Duration=${call_duration}s, Total=${time_total}s, Connect=${time_connect}s${audit_header_info}" "$thread_id"
        return 0
    else
        increment_counter "$DATA_FOLDER/.failed_count"
        log "FAILED: ID=$audit_id, Status=$status_code, Duration=${call_duration}s, URL=$full_url${audit_header_info}" "$thread_id"
        return 1
    fi
}

# Parse command line arguments
while getopts "h:t:d:n:" opt; do
    case $opt in
        h) TARGET_HOST="$OPTARG" ;;
        t) AUTH_TOKEN="$OPTARG" ;;
        d) DATA_FOLDER="$OPTARG" ;;
        n) THREADS="$OPTARG" ;;
        *) usage ;;
    esac
done

# Set default threads if not provided
THREADS=${THREADS:-$DEFAULT_THREADS}

# Validate threads parameter
if ! [[ "$THREADS" =~ ^[1-9][0-9]*$ ]]; then
    echo "Error: THREADS must be a positive integer"
    exit 1
fi

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

# Set up log file and counter files
LOG_FILE="$DATA_FOLDER/replay_api_calls.log"
touch "$LOG_FILE"
echo "0" > "$DATA_FOLDER/.success_count"
echo "0" > "$DATA_FOLDER/.failed_count"

log "Starting API call replay" "main"
log "Target Host: $TARGET_HOST" "main"
log "Data Folder: $DATA_FOLDER" "main"
log "Threads: $THREADS" "main"
log "Auth Token: [HIDDEN]" "main"

# Remove trailing slash from target host if present
TARGET_HOST=$(echo "$TARGET_HOST" | sed 's:/*$::')

# Count total JSON files
TOTAL_CALLS=$(find "$DATA_FOLDER" -name "*.json" -type f | wc -l)
log "Total JSON files found: $TOTAL_CALLS" "main"

if [[ $TOTAL_CALLS -eq 0 ]]; then
    log "No JSON files found in data folder" "main"
    exit 0
fi

# Create array of JSON files
mapfile -t json_files < <(find "$DATA_FOLDER" -name "*.json" -type f)

# Function to run API calls in parallel using xargs
run_parallel_calls() {
    local files=("$@")
    
    # Create a temporary file list
    local temp_file_list="/tmp/json_files_$$"
    printf '%s\n' "${files[@]}" > "$temp_file_list"
    
    # Export necessary variables for the subprocess
    export TARGET_HOST
    export AUTH_TOKEN
    export DATA_FOLDER
    export LOG_FILE
    
    # Export the function to be used by xargs
    export -f process_api_call log increment_counter
    
    # Use xargs to process files in parallel
    cat "$temp_file_list" | xargs -I {} -P "$THREADS" bash -c '
        thread_id=$((RANDOM % 1000))
        process_api_call "$1" "$thread_id"
    ' _ {}
    
    # Cleanup
    rm -f "$temp_file_list"
}

# Alternative function for systems without proper xargs -P support
run_parallel_calls_alternative() {
    local files=("$@")
    local pids=()
    local current_thread=1
    
    for json_file in "${files[@]}"; do
        # Wait if we've reached the thread limit
        while [[ ${#pids[@]} -ge $THREADS ]]; do
            # Check for completed processes and remove them
            local new_pids=()
            for pid in "${pids[@]}"; do
                if kill -0 "$pid" 2>/dev/null; then
                    new_pids+=("$pid")
                fi
            done
            pids=("${new_pids[@]}")
            
            # Small sleep to prevent busy waiting
            sleep 0.1
        done
        
        # Start new process
        (
            process_api_call "$json_file" "$current_thread"
        ) &
        
        pids+=($!)
        current_thread=$((current_thread % THREADS + 1))
    done
    
    # Wait for all remaining processes to complete
    for pid in "${pids[@]}"; do
        wait "$pid" 2>/dev/null || true
    done
}

# Execute all API calls in parallel
log "Starting parallel execution with $THREADS threads" "main"

# Try to use xargs first, fall back to alternative method if it fails
if command -v xargs >/dev/null 2>&1 && xargs --help 2>&1 | grep -q "\-P"; then
    log "Using xargs for parallel execution" "main"
    run_parallel_calls "${json_files[@]}"
else
    log "Using alternative method for parallel execution" "main"
    run_parallel_calls_alternative "${json_files[@]}"
fi

# Read final counters
SUCCESSFUL_CALLS=$(cat "$DATA_FOLDER/.success_count" 2>/dev/null || echo "0")
FAILED_CALLS=$(cat "$DATA_FOLDER/.failed_count" 2>/dev/null || echo "0")

# Cleanup counter files
rm -f "$DATA_FOLDER/.success_count" "$DATA_FOLDER/.failed_count"

# Calculate total duration
SCRIPT_END_EPOCH=$(date +%s)
TOTAL_DURATION=$((SCRIPT_END_EPOCH - SCRIPT_START_EPOCH))

# Final summary
log "==================================" "main"
log "API Replay Summary" "main"
log "Started: $SCRIPT_START_TIME" "main"
log "Total Duration: ${TOTAL_DURATION} seconds" "main"
log "Threads Used: $THREADS" "main"
log "Total Calls: $TOTAL_CALLS" "main"
log "Successful Calls: $SUCCESSFUL_CALLS" "main"
log "Failed Calls: $FAILED_CALLS" "main"
if [[ $TOTAL_CALLS -gt 0 ]]; then
    SUCCESS_RATE=$(echo "scale=2; $SUCCESSFUL_CALLS * 100 / $TOTAL_CALLS" | bc -l 2>/dev/null || echo "0")
    log "Success Rate: ${SUCCESS_RATE}%" "main"
fi
log "Log file: $LOG_FILE" "main"
log "==================================" "main"

echo "Replay completed. Check log file for details: $LOG_FILE"
