# API Audit Replay Load Testing Tool

## Overview

This tool extracts recorded API calls from metasfresh's `API_Request_Audit` database table and replays them using k6 for load testing and performance analysis. It enables you to:

- Test how a metasfresh instance handles real production workloads
- Compare response times between different environments (prod vs staging)
- Validate that changes don't break existing API functionality
- Stress test with realistic request patterns

## Architecture

The tool consists of three main components:

1. **Data Extractor** (`extract-api-audit.js`) - Queries PostgreSQL and exports audit data to JSON
2. **K6 Replay Script** (`replay-api-audit.js`) - Loads and replays extracted requests
3. **Runner Script** (`run-audit-replay.sh`) - Orchestrates extraction and replay

## Prerequisites

- **Node.js 18+** - For running the extraction script
- **k6** - Load testing framework ([installation guide](https://k6.io/docs/getting-started/installation/))
- **PostgreSQL access** - To the source database containing API audit data
- **Network access** - To the target metasfresh instance

## Documentation

- **[SCALING_GUIDE.md](./SCALING_GUIDE.md)** - Performance limits, memory requirements, and scaling strategies
- **[API_AUDIT_REPLAY_DESIGN.md](./API_AUDIT_REPLAY_DESIGN.md)** - Technical design and architecture

## Quick Start

### 1. Install Dependencies

```bash
cd e2e/perf/k6
npm install
```

### 2. Configure

Copy the example configuration and customize it:

```bash
cp audit-replay.env.example audit-replay.env
nano audit-replay.env
```

Key settings to configure:
- `SOURCE_DB_HOST`, `SOURCE_DB_PORT`, etc. - Source database connection
- `START_TIME`, `END_TIME` - Time range for extraction
- `PATH_FILTER` - Which API endpoints to extract (e.g., `/api/v2/bpartner%`)
- `TARGET_BASE_URL` - Where to replay requests
- `TARGET_AUTH_TOKEN` - Authentication for target instance
- `VUS` - Number of concurrent virtual users
- `DURATION` - How long to run the test

### 3. Run the Full Pipeline

```bash
./run-audit-replay.sh
```

This will:
1. Extract API audit data from the source database
2. Replay the requests to the target instance
3. Generate results and reports

## Usage Scenarios

### Scenario 1: Extract and Replay Recent BPartner Requests

```bash
# Edit audit-replay.env
START_TIME=2025-11-15T00:00:00Z
END_TIME=2025-11-15T23:59:59Z
PATH_FILTER=/api/v2/bpartner%
TARGET_BASE_URL=http://localhost:8282/api/v2
VUS=10
DURATION=60s

# Run
./run-audit-replay.sh
```

### Scenario 2: Compare Production vs Staging Performance

```bash
# Extract from production database
SOURCE_DB_HOST=prod-db.example.com
PATH_FILTER=/api/v2/%
MAX_REQUESTS=1000
EXTRACTED_DATA_FILE=./test-data/prod-requests.json

# Replay to staging
TARGET_BASE_URL=https://staging.example.com/api/v2
TARGET_AUTH_TOKEN=your-staging-token
VUS=50
DURATION=300s

# Run
./run-audit-replay.sh
```

### Scenario 3: Extract Only (No Replay)

```bash
# Set SKIP_REPLAY to true
SKIP_REPLAY=true

# Run
./run-audit-replay.sh
```

### Scenario 4: Replay Only (Using Previously Extracted Data)

```bash
# Set SKIP_EXTRACTION to true
SKIP_EXTRACTION=true
EXTRACTED_DATA_FILE=./test-data/my-previous-extraction.json

# Run
./run-audit-replay.sh
```

### Scenario 5: Replay Specific Request ID Range

```bash
# Extract and replay a specific range of requests by ID
MIN_ID=1000000
MAX_ID=1000100
ITERATIONS=auto

# Run
./run-audit-replay.sh
```

This is useful when you:
- Know the exact request IDs that caused issues
- Want to replay a specific sequence of requests
- Need to debug a particular range of audit entries

## Manual Usage

### Extract Audit Data Manually

```bash
# Extract by time range
node extract-api-audit.js \
  --db-host localhost \
  --db-port 5432 \
  --db-name metasfresh \
  --db-user metasfresh \
  --db-password metasfresh \
  --start-time "2025-11-15T00:00:00Z" \
  --end-time "2025-11-15T23:59:59Z" \
  --path-filter "/api/v2/bpartner%" \
  --status PROCESSED \
  --output ./test-data/extracted-requests.json \
  --limit 5000

# Extract by ID range
node extract-api-audit.js \
  --db-host localhost \
  --db-port 5432 \
  --db-name metasfresh \
  --db-user metasfresh \
  --db-password metasfresh \
  --min-id 1000000 \
  --max-id 1000050 \
  --output ./test-data/specific-requests.json
```

### Run K6 Test Manually

```bash
BASE_URL="http://localhost:8282/api/v2" \
AUTH_TOKEN="your-token" \
AUDIT_DATA_FILE="./test-data/extracted-requests.json" \
REPLAY_MODE="sequential" \
VUS=50 \
DURATION=300s \
k6 run --out json=results.json replay-api-audit.js
```

## Configuration Options

### Extraction Options

| Option | Description | Default |
|--------|-------------|---------|
| `--db-host` | Database host | `localhost` |
| `--db-port` | Database port | `5432` |
| `--db-name` | Database name | `metasfresh` |
| `--db-user` | Database user | `metasfresh` |
| `--db-password` | Database password | `metasfresh` |
| `--min-id` | Minimum API_Request_Audit_ID | None |
| `--max-id` | Maximum API_Request_Audit_ID | None |
| `--start-time` | Start time (ISO 8601) | None (all time) |
| `--end-time` | End time (ISO 8601) | None (all time) |
| `--path-filter` | SQL LIKE pattern for paths | `%` (all paths) |
| `--status` | Filter by status | `PROCESSED` |
| `--output` | Output file path | `test-data/extracted-api-audit.json` |
| `--limit` | Max requests to extract | `10000` |
| `--exclude-headers` | Headers to exclude | `Host,Connection,Content-Length,User-Agent` |
| `--include-responses` | Include response data | `true` |

### K6 Replay Options

| Environment Variable | Description | Default |
|---------------------|-------------|---------|
| `BASE_URL` | Target base URL | `http://localhost:8282/api/v2` |
| `AUTH_TOKEN` | Authentication token | Empty |
| `AUDIT_DATA_FILE` | Path to extracted data | `./test-data/extracted-api-audit.json` |
| `REPLAY_MODE` | `sequential` or `random` | `sequential` |
| `THINK_TIME` | Delay between requests (seconds) | `1` |
| `VUS` | Virtual users (concurrency) | `10` |
| `DURATION` | Test duration | `60s` |
| `COMPARE_RESPONSES` | Compare response bodies | `false` |
| `REPLACE_AUTH_TOKEN` | Replace auth tokens | `true` |

## Output and Metrics

### Console Output

The tool provides real-time progress information:

```
==========================================
API Audit Replay Load Test
==========================================
Base URL: http://localhost:8282/api/v2
Audit Data: ./test-data/extracted-api-audit.json
Total Requests: 1234
Replay Mode: sequential
VUs: 50
Duration: 300s
Think Time: 1s
Replace Auth Token: true
Compare Responses: false
==========================================
```

### K6 Metrics

The replay script tracks these custom metrics:

- `audit_replay_duration` - Time to execute each replayed request
- `audit_replay_success` - Success rate (expected vs actual status codes)
- `audit_unexpected_status` - Count of status code mismatches
- `audit_response_time_diff` - Difference between original and replay response times
- `audit_slower_requests` - Count of requests slower than original
- `audit_faster_requests` - Count of requests faster than original

### Results File

Results are saved in JSON format (e.g., `results/audit-replay-results.json`) and include:

- All standard k6 metrics
- Custom audit replay metrics
- Per-request timings
- Errors and failures

### Run History Management

Each time you run `./run-audit-replay.sh`, all output files are stored in a timestamped directory under `./runs/`:

```
./runs/
├── 2025-11-18_14-30-45/
│   ├── extracted-api-audit.json
│   ├── audit-replay-results.json
│   └── audit-replay.env
├── 2025-11-18_15-15-22/
│   ├── extracted-api-audit.json
│   ├── audit-replay-results.json
│   └── audit-replay.env
└── latest -> 2025-11-18_15-15-22  (symlink)
```

This means:
- **Previous results are never lost** - Each run creates a new directory
- **Easy comparison** - Compare results from different runs
- **Reproducibility** - The exact configuration used is saved in each run folder
- **Quick access** - `./runs/latest` always points to the most recent run

To view results from the latest run:
```bash
./view-results.sh
# Or explicitly:
./view-results.sh ./runs/latest/audit-replay-results.json
```

To view results from a specific run:
```bash
./view-results.sh ./runs/2025-11-18_14-30-45/audit-replay-results.json
```

To clean up old runs:
```bash
# Remove all runs except the latest 5
ls -1dt ./runs/*/ | tail -n +6 | xargs rm -rf

# Remove all runs older than 7 days
find ./runs -type d -mtime +7 -exec rm -rf {} +

# Remove specific run
rm -rf ./runs/2025-11-18_14-30-45
```

## Extracted Data Format

The extraction produces a JSON file with this structure:

```json
{
  "metadata": {
    "extractedAt": "2025-11-15T10:30:00Z",
    "source": {
      "host": "localhost",
      "database": "metasfresh"
    },
    "filters": {
      "startTime": "2025-11-15T00:00:00Z",
      "endTime": "2025-11-15T23:59:59Z",
      "pathFilter": "/api/v2/%",
      "status": "PROCESSED",
      "limit": 10000
    },
    "count": 1234
  },
  "requests": [
    {
      "id": 12345,
      "method": "PUT",
      "path": "/api/v2/bpartner",
      "body": "{...}",
      "headers": {
        "Content-Type": "application/json",
        "X-Org-Code": "001"
      },
      "timestamp": "2025-11-15T08:15:30Z",
      "userId": 100,
      "orgId": 1000000,
      "status": "PROCESSED",
      "expectedHttpCode": 200,
      "expectedResponseBody": "{...}",
      "actualResponseTime": 234.5
    }
  ]
}
```

## Troubleshooting

### Error: "No audit requests loaded"

**Cause:** The extracted data file is empty or doesn't exist.

**Solution:**
1. Check that `AUDIT_DATA_FILE` points to a valid file
2. Verify the extraction step completed successfully
3. Check database connection settings
4. Verify the time range and filters match existing data

### Issue: Path filter not working on Windows (Git Bash)

**Symptom:** SQL shows path like `C:/Program Files/Git/api/v2/%` instead of `/api/v2/%`

**Cause:** Git Bash on Windows has a "POSIX path conversion" feature that automatically converts anything starting with `/` to a Windows path when passing to Windows executables (like node.exe). It thinks `/api/v2/%` is a Unix path, but it's just a string pattern!

**Solution:** This is now fixed in the script (uses `MSYS_NO_PATHCONV=1` to disable path conversion). Make sure you're using the latest version of `run-audit-replay.sh`.

**Alternative:** Run the extraction directly with Node.js and disable path conversion:
```bash
MSYS_NO_PATHCONV=1 node extract-api-audit.js --path-filter "/api/v2/%" ...
```

Or use PowerShell/CMD instead of Git Bash.

### Debugging: See the SQL queries

**Use `--show-sql` to see what queries will be executed:**

```bash
# Via script
SHOW_SQL=true ./run-audit-replay.sh

# Or directly
node extract-api-audit.js --show-sql \
  --start-time "2025-11-14T07:00:00Z" \
  --end-time "2025-11-14T22:00:00Z" \
  --path-filter "/api/v2/%"
```

This will show the SQL queries and exit without running them (and without attempting k6 replay).

### Error: "Database connection failed"

**Cause:** Cannot connect to the source database.

**Solution:**
1. Verify `SOURCE_DB_HOST`, `SOURCE_DB_PORT`, etc. are correct
2. Check network connectivity to the database
3. Verify credentials are correct
4. Check PostgreSQL is accepting connections

### Warning: "Status mismatch"

**Cause:** The replayed request returned a different status code than the original.

**Solution:**
- This is expected if the target instance has different data or configuration
- Review the specific request to understand why it behaves differently
- Consider filtering by specific endpoints that should be identical

### Warning: "Significantly slower"

**Cause:** A replayed request took >50% longer than the original.

**Solution:**
- This indicates potential performance regression
- Review the specific endpoint for performance issues
- Consider database indexing, resource constraints, or code changes

### Error: "Auth token required"

**Cause:** The target instance requires authentication but no token provided.

**Solution:**
1. Set `TARGET_AUTH_TOKEN` in your configuration
2. Or set `REPLACE_AUTH_TOKEN=false` to use original tokens (may fail if expired)

## Advanced Usage

### Filter by Specific Time Window

Extract only requests during peak hours:

```bash
node extract-api-audit.js \
  --start-time "2025-11-15T09:00:00Z" \
  --end-time "2025-11-15T10:00:00Z" \
  --output peak-hour-requests.json
```

### Extract Specific Endpoints

Extract only invoice-related endpoints:

```bash
node extract-api-audit.js \
  --path-filter "%invoice%" \
  --output invoice-requests.json
```

### High-Load Stress Test

Run with high concurrency:

```bash
VUS=200 \
DURATION=1800s \
THINK_TIME=0 \
./run-audit-replay.sh
```

### Random Replay Order

Test with randomized request order:

```bash
REPLAY_MODE=random \
./run-audit-replay.sh
```

## Integration with CI/CD

You can integrate this tool into your CI/CD pipeline to automatically test each deployment:

```yaml
# .github/workflows/performance-test.yml
- name: Run Audit Replay Load Test
  run: |
    cd e2e/perf/k6
    npm install

    # Extract recent production requests
    SOURCE_DB_HOST=${{ secrets.PROD_DB_HOST }} \
    SOURCE_DB_PASSWORD=${{ secrets.PROD_DB_PASSWORD }} \
    START_TIME=$(date -u -d '1 hour ago' +%Y-%m-%dT%H:%M:%SZ) \
    END_TIME=$(date -u +%Y-%m-%dT%H:%M:%SZ) \
    TARGET_BASE_URL=https://staging.example.com/api/v2 \
    TARGET_AUTH_TOKEN=${{ secrets.STAGING_TOKEN }} \
    VUS=50 \
    DURATION=300s \
    ./run-audit-replay.sh
```

## Tips and Best Practices

1. **Start Small** - Begin with a small number of requests (100-500) to validate setup
2. **Filter Wisely** - Use `PATH_FILTER` to focus on specific endpoints
3. **Time Windows** - Extract data from representative time periods (peak hours)
4. **Authentication** - Always use a dedicated test token for `TARGET_AUTH_TOKEN`
5. **Think Time** - Use realistic think times to simulate user behavior
6. **Concurrency** - Start with low VUs and gradually increase
7. **Monitor Target** - Watch the target instance's resource usage during replay
8. **Compare Apples to Apples** - Ensure target has similar data to source for valid comparisons

## Limitations

- **Stateful Requests** - Requests that depend on previous requests may fail in random mode
- **Unique Constraints** - Some requests may fail if they create records with unique identifiers
- **Expired Tokens** - Original auth tokens may be expired
- **Different Data** - Target instance may have different master data (orgs, users, products)
- **Time Sensitivity** - Requests with time-based logic may behave differently

## Support

For issues or questions:
1. Check this README for troubleshooting tips
2. Review the design document: `API_AUDIT_REPLAY_DESIGN.md`
3. Check k6 documentation: https://k6.io/docs/
4. Open an issue in the metasfresh repository

## License

This tool is part of metasfresh and is licensed under GPLv2+.
