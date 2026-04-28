# API Audit Replay Load Testing - Design Document

## Overview
This document describes the design for a k6-based load testing tool that retrieves recorded API calls from metasfresh's audit system and replays them against the same or different metasfresh instances.

## API Audit System Analysis

### Data Source: API_Request_Audit Table

**Location:** PostgreSQL database (metasfresh schema)

**Key Columns:**
- `API_Request_Audit_ID` - Primary key
- `Method` - HTTP method (GET, POST, PUT, DELETE, PATCH)
- `RequestURI` - **Actual URI path on server** (e.g., `/api/v2/orders/sales`) - **Used for replay**
- `Path` - Full original URL from client (may include original server name)
- `Body` - Complete request JSON/text payload
- `HttpHeaders` - JSON-serialized headers
- `Status` - RECEIVED, PROCESSED, ERROR, ACK_ERROR
- `Time` - Request timestamp
- `AD_User_ID`, `AD_Role_ID`, `AD_Org_ID` - User context
- `RemoteAddr`, `RemoteHost` - Client information
- `UI_Trace_ExternalId` - Correlation ID

**Important:** The tool uses `RequestURI` for replay, not `Path`. The `RequestURI` contains the actual URI path that was requested on the server (e.g., `/api/v2/orders/sales/candidates/bulk`), while `Path` may contain the full original URL including the source server name (e.g., `http://sourceserver.com/api/v2/orders/sales/candidates/bulk`).

**Related Table:** `API_Response_Audit`
- `HttpCode` - Response status code
- `Body` - Complete response JSON
- `HttpHeaders` - Response headers
- `Time` - Response timestamp
- Links to request via `API_Request_Audit_ID`

### Java Implementation

**ApiAuditService** (`de.metas.util.web.audit.ApiAuditService`)
- `logRequest()` - Records incoming requests
- `logResponse()` - Records responses
- `executeHttpCall()` - Replays requests via Spring WebClient

**ApiRequestAudit Model** (`de.metas.audit.apirequest.request.ApiRequestAudit`)
- Lombok-based domain model
- Includes builder pattern for construction
- JSON serialization for headers

**ApiRequestAuditRepository**
- `getByIdOrNull()` - Retrieves single audit record
- `retrieveBetween()` - Retrieves time-ranged audits
- `streamByFilter()` - Streams filtered audits

## Existing K6 Infrastructure

**Location:** `e2e/perf/k6/`

**Framework Components:**
1. **http-client.js** - HTTP client wrapper with metrics
2. **data-generators.js** - Synthetic data generation
3. **data-manager.js** - Data orchestration (generated/existing/mixed)
4. **test-data/extracted-*.json** - Real-world request samples

**Current Data Strategy:**
- `generated` - Always create synthetic data
- `existing` - Always replay extracted samples
- `mixed` - Probabilistic mix (EXISTING_RATIO configurable)

**Environment Variables:**
```bash
BASE_URL="http://localhost:8282/api/v2"
AUTH_TOKEN=""
DATA_STRATEGY="generated|existing|mixed"
EXISTING_RATIO="0.7"
VUS="100"
DURATION="500s"
```

## Proposed Load Testing Tool Architecture

### Component 1: Data Extractor (Node.js)

**Purpose:** Query API_Request_Audit and export to k6-compatible JSON

**Script:** `extract-api-audit.js`

**Features:**
- Connect to PostgreSQL using `pg` library
- Query API_Request_Audit with filters (time range, endpoints, status)
- Transform to k6 data format
- Output JSON file for k6 consumption

**SQL Query:**
```sql
SELECT
  r.API_Request_Audit_ID,
  r.Method,
  r.RequestURI,        -- Actual URI path (used for replay)
  r.Path,              -- Original full URL (for reference)
  r.Body as RequestBody,
  r.HttpHeaders as RequestHeaders,
  r.Time,
  resp.HttpCode as ExpectedHttpCode,
  resp.Body as ExpectedResponseBody,
  (EXTRACT(EPOCH FROM (resp.Time - r.Time)) * 1000) as ActualResponseTime_ms
FROM API_Request_Audit r
LEFT JOIN API_Response_Audit resp
  ON r.API_Request_Audit_ID = resp.API_Request_Audit_ID
WHERE r.Status = 'PROCESSED'
  AND r.Time BETWEEN $1 AND $2
  AND r.Path LIKE $3
ORDER BY r.Time ASC;
```

**Output Format:**
```json
{
  "requests": [
    {
      "id": 12345,
      "method": "PUT",
      "path": "/api/v2/bpartner",
      "originalPath": "http://sourceserver.com/api/v2/bpartner",
      "body": "{...}",
      "headers": {
        "Content-Type": "application/json",
        "X-Org-Code": "001"
      },
      "expectedHttpCode": 200,
      "expectedResponseBody": "{...}",
      "actualResponseTime": 234.5,
      "timestamp": "2025-11-15T10:30:00Z"
    }
  ]
}
```

**Note:** `path` contains the RequestURI (what gets replayed), `originalPath` contains the full original URL (for reference).

**CLI Options:**
```bash
node extract-api-audit.js \
  --db-host localhost \
  --db-port 5432 \
  --db-name metasfresh \
  --db-user metasfresh \
  --db-password metasfresh \
  --start-time "2025-11-15T00:00:00Z" \
  --end-time "2025-11-15T23:59:59Z" \
  --path-filter "/api/v2/%" \
  --status PROCESSED \
  --output extracted-requests.json \
  --limit 10000
```

### Component 2: K6 Replay Script

**Purpose:** Load and replay extracted API requests

**Script:** `replay-api-audit.js`

**Features:**
- Load extracted JSON file
- Replay requests in order or randomized
- Support concurrent users (VUs)
- Compare actual vs expected response codes
- Metric collection and reporting

**Integration with Existing Framework:**
- Reuse `http-client.js` for HTTP execution
- Extend `data-manager.js` to support audit replay mode
- Add new data strategy: `audit` (replay from API_Request_Audit)

**Environment Variables:**
```bash
BASE_URL="http://target-instance:8282/api/v2"  # Target instance
AUTH_TOKEN=""                                   # New auth token
DATA_STRATEGY="audit"                           # Enable audit replay
AUDIT_DATA_FILE="../test-data/extracted-requests.json"
REPLAY_MODE="sequential|random"                # Request order
VUS="50"
DURATION="300s"
THINK_TIME="1s"                                # Delay between requests
```

**K6 Script Structure:**
```javascript
import { check, sleep } from 'k6';
import http from 'k6/http';
import { Trend, Rate, Counter } from 'k6/metrics';

// Load extracted audit data
const auditData = JSON.parse(open(AUDIT_DATA_FILE));

// Custom metrics
const replayDuration = new Trend('audit_replay_duration');
const replaySuccess = new Rate('audit_replay_success');
const unexpectedStatusCode = new Counter('audit_unexpected_status');

export const options = {
  vus: __ENV.VUS || 10,
  duration: __ENV.DURATION || '60s',
};

export default function () {
  const request = getNextRequest(); // Sequential or random

  const response = http.request(
    request.method,
    `${BASE_URL}${request.path}`,
    request.body,
    {
      headers: prepareHeaders(request.headers),
    }
  );

  // Validation
  const statusMatches = response.status === request.expectedHttpCode;
  check(response, {
    'status matches expected': (r) => statusMatches,
    'response time acceptable': (r) => r.timings.duration < 5000,
  });

  // Metrics
  replayDuration.add(response.timings.duration);
  replaySuccess.add(statusMatches ? 1 : 0);
  if (!statusMatches) {
    unexpectedStatusCode.add(1);
  }

  sleep(__ENV.THINK_TIME || 1);
}
```

### Component 3: Runner Script

**Purpose:** Orchestrate extraction and replay

**Script:** `run-audit-replay.sh`

**Workflow:**
1. Extract API audit data from source database
2. Optionally sanitize/filter data
3. Run k6 load test against target instance
4. Generate HTML report

```bash
#!/usr/bin/env bash

# Extract from source
node extract-api-audit.js \
  --db-host $SOURCE_DB_HOST \
  --start-time "$START_TIME" \
  --end-time "$END_TIME" \
  --output test-data/audit-replay.json

# Replay to target
BASE_URL="$TARGET_BASE_URL" \
AUTH_TOKEN="$TARGET_AUTH_TOKEN" \
DATA_STRATEGY="audit" \
AUDIT_DATA_FILE="test-data/audit-replay.json" \
VUS="$VUS" \
DURATION="$DURATION" \
k6 run --out json=results.json replay-api-audit.js

# Generate report
k6 report results.json --export results.html
```

## Data Transformation Considerations

### Header Filtering
- **Remove:** `Host`, `Content-Length`, `Connection` (auto-generated)
- **Preserve:** `Content-Type`, `X-Org-Code`, `Authorization`
- **Replace:** `Authorization` with target instance token

### Body Sanitization
- **IDs:** Consider replacing hard-coded IDs with dynamic lookups
- **Timestamps:** May need adjustment for replay
- **Unique Constraints:** Add uniqueness suffix (e.g., `-replay-{timestamp}`)

### Path Transformation
- **Base URL:** Strip source base URL, use target BASE_URL
- **Query Parameters:** Preserve as-is or parameterize

## Metrics and Reporting

### K6 Metrics
- `audit_replay_duration` - Time to execute replayed request
- `audit_replay_success` - % requests with expected status code
- `audit_unexpected_status` - Count of status code mismatches
- `http_req_failed` - Overall failure rate
- `http_req_duration` - Standard k6 HTTP duration

### Comparison Report
- Original response time vs replay response time
- Original status code vs replay status code
- Error rate comparison
- Throughput comparison (requests/sec)

### HTML Report
- Summary statistics
- Request-by-request comparison table
- Charts: response time distribution, error rate over time
- Outlier identification (requests slower in replay)

## Implementation Plan

### Phase 1: Data Extractor
1. Create `extract-api-audit.js` with PostgreSQL query
2. Implement CLI argument parsing
3. Add JSON output formatting
4. Test with sample data

### Phase 2: K6 Integration
1. Extend `data-manager.js` to support `audit` strategy
2. Create `replay-api-audit.js` k6 script
3. Implement header/body sanitization
4. Add custom metrics

### Phase 3: Runner and Reporting
1. Create `run-audit-replay.sh` orchestration script
2. Implement comparison logic
3. Generate HTML report
4. Add documentation and examples

### Phase 4: Advanced Features
1. Support for request filtering (by endpoint, user, org)
2. Request sequencing (maintain order dependencies)
3. Parameterization (dynamic ID replacement)
4. Multi-instance replay (compare prod vs staging)

## Dependencies

### Node.js Packages
- `pg` - PostgreSQL client
- `yargs` - CLI argument parsing
- `k6` - Load testing framework

### Environment Requirements
- Node.js 18+
- PostgreSQL access to source database
- Network access to target metasfresh instance
- k6 installed (v0.45+)

## Configuration Example

**config.env:**
```bash
# Source Database
SOURCE_DB_HOST=localhost
SOURCE_DB_PORT=5432
SOURCE_DB_NAME=metasfresh
SOURCE_DB_USER=metasfresh
SOURCE_DB_PASSWORD=metasfresh

# Extraction Filters
START_TIME="2025-11-15T00:00:00Z"
END_TIME="2025-11-15T23:59:59Z"
PATH_FILTER="/api/v2/%"
STATUS_FILTER=PROCESSED
MAX_REQUESTS=10000

# Target Instance
TARGET_BASE_URL=http://app:8282/api/v2
TARGET_AUTH_TOKEN=your-token-here

# Load Test Configuration
VUS=50
DURATION=300s
THINK_TIME=1s
REPLAY_MODE=sequential
```

## Security Considerations

1. **Credentials:** Never commit credentials; use environment variables
2. **Sensitive Data:** Sanitize PII from extracted data
3. **Authorization:** Ensure target instance auth token has appropriate permissions
4. **Rate Limiting:** Respect target instance rate limits (use VUs wisely)

## Future Enhancements

1. **Real-time Capture:** Stream API audit data directly to k6 (no extraction step)
2. **Distributed Load:** Multi-region k6 execution
3. **Chaos Engineering:** Inject failures during replay
4. **ML-based Analysis:** Identify performance regressions automatically
5. **API Fuzzing:** Mutate extracted requests for security testing
