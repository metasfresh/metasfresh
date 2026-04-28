#!/bin/bash

# Script: read_api_audit.sh
# Purpose: Read API request audit data from PostgreSQL and store to JSON files

set -euo pipefail

# Default values
DEFAULT_PORT=5432
LOG_DIR=""
SCRIPT_START_TIME=$(date '+%Y-%m-%d %H:%M:%S')
SCRIPT_START_EPOCH=$(date +%s)

# Function to display usage
usage() {
    cat << EOF
Usage: $0 -h HOST -d DATABASE -u USERNAME -s START_TIME -e END_TIME -f FOLDER_LOCATION [-p PORT]

Parameters:
  -h HOST           Database host/IP address
  -d DATABASE       Database name
  -u USERNAME       Database username
  -s START_TIME     Start time (format: 'YYYY-MM-DD HH:MM:SS')
  -e END_TIME       End time (format: 'YYYY-MM-DD HH:MM:SS')
  -f FOLDER_LOCATION Root folder for output
  -p PORT           Database port (default: 5432)

Environment Variables:
  DB_PASSWORD       Database password (if not set, will be prompted)

Example:
  $0 -h localhost -d metasfresh -u postgres -s '2024-01-01 00:00:00' -e '2024-01-02 00:00:00' -f /tmp/audit_data
EOF
    exit 1
}

# Function for logging
log() {
    local message="$1"
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    echo "[$timestamp] $message"
    if [[ -n "$LOG_DIR" && -f "$LOG_DIR/read_api_audit.log" ]]; then
        echo "[$timestamp] $message" >> "$LOG_DIR/read_api_audit.log"
    fi
}

# Parse command line arguments
while getopts "h:d:u:s:e:f:p:" opt; do
    case $opt in
        h) HOST="$OPTARG" ;;
        d) DATABASE="$OPTARG" ;;
        u) USERNAME="$OPTARG" ;;
        s) START_TIME="$OPTARG" ;;
        e) END_TIME="$OPTARG" ;;
        f) FOLDER_LOCATION="$OPTARG" ;;
        p) PORT="$OPTARG" ;;
        *) usage ;;
    esac
done

# Check required parameters
if [[ -z "${HOST:-}" || -z "${DATABASE:-}" || -z "${USERNAME:-}" || -z "${START_TIME:-}" || -z "${END_TIME:-}" || -z "${FOLDER_LOCATION:-}" ]]; then
    echo "Error: Missing required parameters"
    usage
fi

# Set default port if not provided
PORT=${PORT:-$DEFAULT_PORT}

# Get password from environment or prompt
if [[ -z "${DB_PASSWORD:-}" ]]; then
    echo -n "Enter database password: "
    read -s DB_PASSWORD
    echo
fi

# Validate time format and create folder name
START_TIME_FORMATTED=$(echo "$START_TIME" | tr ' ' '_' | tr ':' '-')
OUTPUT_DIR="$FOLDER_LOCATION/api_request_audit_$START_TIME_FORMATTED"
LOG_DIR="$OUTPUT_DIR"

# Create output directory
mkdir -p "$OUTPUT_DIR"

# Initialize log file
touch "$LOG_DIR/read_api_audit.log"

log "Starting API request audit data extraction"
log "Parameters: Host=$HOST, Port=$PORT, Database=$DATABASE, Username=$USERNAME"
log "Time range: $START_TIME to $END_TIME"
log "Output directory: $OUTPUT_DIR"

# Construct PostgreSQL connection string
PGPASSWORD="$DB_PASSWORD"
export PGPASSWORD

# SQL query to extract data
SQL_QUERY="
SELECT 
    api_request_audit_id,
    method,
    requesturi,
    body
FROM public.api_request_audit 
WHERE time >= '$START_TIME' 
  AND time <= '$END_TIME'
ORDER BY api_request_audit_id;
"

log "Executing database query..."

# Execute query and process results
psql -h "$HOST" -p "$PORT" -d "$DATABASE" -U "$USERNAME" -t -A -F $'\t' -c "$SQL_QUERY" | while IFS=$'\t' read -r audit_id method requesturi body; do
    if [[ -n "$audit_id" ]]; then
        # Create JSON file for each record
        json_file="$OUTPUT_DIR/${audit_id}.json"
        
        # Create JSON object
        cat > "$json_file" << EOF
{
  "api_request_audit_id": "$audit_id",
  "method": "$method",
  "requesturi": "$requesturi",
  "body": $(echo "$body" | jq -R .)
}
EOF
        
        log "Created JSON file: ${audit_id}.json"
    fi
done

# Check if psql command was successful
if [[ $? -eq 0 ]]; then
    log "Database query completed successfully"
else
    log "ERROR: Database query failed"
    exit 1
fi

# Calculate duration
SCRIPT_END_EPOCH=$(date +%s)
DURATION=$((SCRIPT_END_EPOCH - SCRIPT_START_EPOCH))

# Create README file
README_FILE="$OUTPUT_DIR/README.md"
cat > "$README_FILE" << EOF
# API Request Audit Data Export

## Execution Details
- **Invocation Time**: $SCRIPT_START_TIME
- **Duration**: ${DURATION} seconds
- **Host**: $HOST
- **Port**: $PORT
- **Database**: $DATABASE
- **Username**: $USERNAME
- **Start Time**: $START_TIME
- **End Time**: $END_TIME
- **Output Directory**: $OUTPUT_DIR

## Data Format
Each API request audit record is stored as a separate JSON file named with the \`api_request_audit_id\`.

## File Structure
- Individual JSON files: \`{api_request_audit_id}.json\`
- Log file: \`read_api_audit.log\`
- This README: \`README.md\`

## JSON Schema
Each JSON file contains:
- \`api_request_audit_id\`: Primary key
- \`method\`: HTTP method
- \`requesturi\`: Request URI/endpoint path
- \`body\`: Request body (JSON string)
EOF

log "Created README file: $README_FILE"
log "Script completed successfully in ${DURATION} seconds"

# Count files created
FILE_COUNT=$(find "$OUTPUT_DIR" -name "*.json" -type f | wc -l)
log "Total JSON files created: $FILE_COUNT"

# Cleanup
unset PGPASSWORD