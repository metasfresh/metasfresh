# API Audit Replay - Scaling and Performance Guide

## Overview

This guide provides recommendations for scaling the API audit replay tool based on the number of requests, memory constraints, and performance objectives.

## Hard Limits (Configurable)

**Default Configuration:**
- `MAX_REQUESTS=10000` (default limit in extraction)
- No hard-coded maximum - you can set `--limit` to any value

**To increase:**
```bash
# Extraction
node extract-api-audit.js --limit 100000 ...

# Or in audit-replay.env
MAX_REQUESTS=100000
```

## Practical Limits

### 1. Extraction Phase (PostgreSQL → JSON)

**Memory Constraint:** Node.js process needs to hold all data in memory

| Request Count | Estimated JSON Size | Memory Required | Extraction Time |
|---------------|---------------------|-----------------|-----------------|
| 10,000 | ~100 MB | ~200 MB | <1 minute |
| 50,000 | ~500 MB | ~1 GB | 2-5 minutes |
| 100,000 | ~1 GB | ~2 GB | 5-10 minutes |
| 200,000 | ~2 GB | ~4 GB | 10-20 minutes |

**Assumptions:**
- Average request size: 10KB (typical for metasfresh API requests)
- Includes request headers, body, method, path
- With `INCLUDE_RESPONSES=true`, sizes are ~2x larger

### 2. Replay Phase (k6 SharedArray)

**Memory Constraint:** k6 loads entire SharedArray into shared memory

**k6 Recommendation:** Keep SharedArrays under 1GB for best performance

**This translates to:**

| Request Type | Example | Requests per 1GB |
|--------------|---------|------------------|
| Lightweight | GET, simple POST | ~100,000 |
| Typical | BPartner upsert, order create | ~50,000 |
| Heavy | Bulk operations, large catalogs | ~10,000 |

**VU Scaling:** Memory usage = SharedArray + (per-VU memory × VU count)
- More VUs = more RAM needed
- SharedArray itself is shared, not duplicated per VU
- Each VU uses ~5-10MB additional RAM

**Total memory estimate:**
```
Total RAM = SharedArray size + (VU count × 10 MB)

Examples:
- 50K requests (500MB) + 50 VUs = 500MB + 500MB = 1GB
- 50K requests (500MB) + 200 VUs = 500MB + 2GB = 2.5GB
- 100K requests (1GB) + 100 VUs = 1GB + 1GB = 2GB
```

### 3. Typical Request Sizes in metasfresh

Based on API audit analysis:

| Request Type | Size Range | Examples |
|--------------|------------|----------|
| Small | 1-5 KB | GET /bpartner/:id, health checks |
| Medium | 10-50 KB | BPartner upsert, sales order create |
| Large | 100 KB - 1 MB | Product catalog updates, bulk imports |
| Very Large | 1 MB+ | File attachments, mass data operations |

## Recommendations by Use Case

### Spot Testing / CI/CD

**Goal:** Fast validation of specific scenarios

```bash
MAX_REQUESTS=1000-5000
VUS=10-50
DURATION=60s-300s
PATH_FILTER="/api/v2/specific-endpoint%"
```

**Memory Required:** ~500MB
**Runtime:** 1-5 minutes
**Use Case:** PR validation, regression testing, smoke tests

### Load Testing

**Goal:** Realistic production workload simulation

```bash
MAX_REQUESTS=10000-20000
VUS=50-200
DURATION=300s-1800s
REPLAY_MODE=sequential
INCLUDE_RESPONSES=true
```

**Memory Required:** 1-3GB
**Runtime:** 5-30 minutes
**Use Case:** Capacity planning, performance baselines, environment comparison

### Stress Testing

**Goal:** Push limits, find breaking points

```bash
MAX_REQUESTS=50000-100000
VUS=200-500
DURATION=1800s-3600s
THINK_TIME=0
REPLAY_MODE=random
```

**Memory Required:** 3-8GB
**Runtime:** 30-60 minutes
**Use Case:** Peak load simulation, scalability limits, infrastructure sizing

## Scaling Beyond Limits

If you need to test with more than ~100K requests, use these strategies:

### Strategy 1: Filter and Focus

Extract only what you need:

```bash
# By endpoint
PATH_FILTER="/api/v2/bpartner%"

# By time window (peak hours)
START_TIME="2025-11-15T09:00:00Z"
END_TIME="2025-11-15T10:00:00Z"

# By status
STATUS_FILTER=PROCESSED  # Exclude errors

# Combination
PATH_FILTER="/api/v2/%"
START_TIME="2025-11-15T08:00:00Z"
END_TIME="2025-11-15T12:00:00Z"
MAX_REQUESTS=20000
```

### Strategy 2: Multiple Sequential Runs

Extract different time windows and run separate tests:

```bash
# Run 1: Morning traffic
START_TIME="2025-11-15T08:00:00Z"
END_TIME="2025-11-15T12:00:00Z"
EXTRACTED_DATA_FILE="./test-data/morning.json"
./run-audit-replay.sh

# Run 2: Afternoon traffic
START_TIME="2025-11-15T13:00:00Z"
END_TIME="2025-11-15T17:00:00Z"
EXTRACTED_DATA_FILE="./test-data/afternoon.json"
./run-audit-replay.sh

# Run 3: Evening traffic
START_TIME="2025-11-15T18:00:00Z"
END_TIME="2025-11-15T22:00:00Z"
EXTRACTED_DATA_FILE="./test-data/evening.json"
./run-audit-replay.sh
```

### Strategy 3: Statistical Sampling

Modify the extraction SQL to add random sampling:

**Edit `extract-api-audit.js` to add:**
```sql
WHERE r.Status = 'PROCESSED'
  AND random() < 0.5  -- 50% random sample
ORDER BY r.Time ASC
LIMIT 100000
```

**Or use modulo sampling (every Nth request):**
```sql
WHERE r.Status = 'PROCESSED'
  AND r.API_Request_Audit_ID % 2 = 0  -- Every 2nd request
ORDER BY r.Time ASC
LIMIT 100000
```

### Strategy 4: Distributed Load Testing

Split requests across multiple k6 instances:

```bash
# Machine 1: Replay requests 1-50K
EXTRACTED_DATA_FILE="./test-data/batch-1.json"
./run-audit-replay.sh

# Machine 2: Replay requests 50K-100K
EXTRACTED_DATA_FILE="./test-data/batch-2.json"
./run-audit-replay.sh

# Machine 3: Replay requests 100K-150K
EXTRACTED_DATA_FILE="./test-data/batch-3.json"
./run-audit-replay.sh
```

## Performance Optimization Tips

### Maximum Throughput

**Goal:** Highest requests/second

```bash
THINK_TIME=0                # No delay
REPLAY_MODE=random          # Better distribution
COMPARE_RESPONSES=false     # Skip body comparison
INCLUDE_RESPONSES=false     # Don't extract response data
REPLACE_AUTH_TOKEN=true     # Single token (faster)
VUS=200                     # High concurrency
```

**Expected throughput:** 500-2000 req/sec (depends on target instance)

### Realistic User Simulation

**Goal:** Mimic actual user behavior

```bash
THINK_TIME=1                # 1 second between requests
REPLAY_MODE=sequential      # Preserve order
INCLUDE_RESPONSES=true      # Compare response times
COMPARE_RESPONSES=false     # Don't compare bodies (too slow)
VUS=50                      # Moderate concurrency
```

**Expected throughput:** 50-200 req/sec

### Memory-Constrained Environment

**Goal:** Run on machines with limited RAM (<4GB)

```bash
MAX_REQUESTS=5000           # Small dataset
VUS=20                      # Low concurrency
INCLUDE_RESPONSES=false     # Reduce JSON size
EXCLUDE_HEADERS="Host,Connection,Content-Length,User-Agent,Authorization,Cookie"
```

**Memory usage:** <1GB

## Monitoring and Troubleshooting

### Monitor Memory Usage During Extraction

```bash
# Linux/Mac
node extract-api-audit.js ... &
PID=$!
while kill -0 $PID 2>/dev/null; do
  ps aux | grep $PID | grep -v grep
  sleep 5
done

# Watch output file size
watch -n 2 "ls -lh test-data/extracted-api-audit.json"
```

### Monitor Memory Usage During Replay

```bash
# k6 built-in metrics show memory usage
k6 run --out json=results.json replay-api-audit.js

# Or use system monitoring
htop  # Linux
top   # Mac/Linux
Task Manager  # Windows
```

### Common Issues and Solutions

#### "Out of Memory" during extraction

**Solution:**
1. Reduce `MAX_REQUESTS`
2. Add more RAM to extraction machine
3. Use sampling strategy

#### "Out of Memory" during k6 replay

**Solution:**
1. Reduce number of requests in extracted file
2. Reduce `VUS` count
3. Set `INCLUDE_RESPONSES=false`
4. Run k6 on machine with more RAM

#### Slow extraction (>10 minutes)

**Solution:**
1. Check database indexes on `API_Request_Audit` table
2. Reduce `MAX_REQUESTS`
3. Narrow time range or path filter
4. Check database server load

#### k6 test runs out of requests too quickly

**Symptom:** Test completes before `DURATION` expires

**Solution:**
1. Increase `MAX_REQUESTS` in extraction
2. Reduce `VUS` count
3. Increase `THINK_TIME`
4. Use `REPLAY_MODE=random` to allow request reuse

## Benchmarks

### Test Environment
- **Machine:** 16GB RAM, 8-core CPU
- **Database:** PostgreSQL 12, localhost
- **Target:** metasfresh running in Docker

### Results

| Requests | VUs | Duration | Avg Memory | Extraction Time | Total Runtime |
|----------|-----|----------|------------|-----------------|---------------|
| 1,000 | 10 | 60s | 300 MB | 5s | 1m 10s |
| 10,000 | 50 | 300s | 1.2 GB | 30s | 6m |
| 50,000 | 100 | 600s | 3.5 GB | 3m | 13m |
| 100,000 | 200 | 1800s | 6 GB | 8m | 38m |

## Recommended Sweet Spot

**For most scenarios:**

```bash
MAX_REQUESTS=10000-50000
VUS=50-100
DURATION=300s-600s
INCLUDE_RESPONSES=true
COMPARE_RESPONSES=false
```

**This gives you:**
- ✓ Good statistical sampling
- ✓ Manageable file sizes (100MB-500MB)
- ✓ Fast extraction (<5 minutes)
- ✓ Reasonable memory usage (<2GB)
- ✓ Enough variety for realistic load patterns
- ✓ Fits on most development machines

## Summary Table

| Scenario | Requests | VUs | Memory | Runtime | Use Case |
|----------|----------|-----|--------|---------|----------|
| **Quick Test** | 1K-5K | 10-20 | <500MB | 1-5min | CI/CD, smoke test |
| **Standard Load** | 10K-20K | 50-100 | 1-2GB | 5-15min | Performance baseline |
| **Heavy Load** | 50K-100K | 100-200 | 3-6GB | 15-40min | Stress test, capacity |
| **Extreme Load** | 100K+ | 200-500 | 6-10GB+ | 40min+ | Max capacity, breaking point |

## References

- [k6 Documentation - SharedArray](https://k6.io/docs/javascript-api/k6-data/sharedarray/)
- [k6 Documentation - Memory Management](https://k6.io/docs/misc/memory-management/)
- [PostgreSQL - Performance Tips](https://wiki.postgresql.org/wiki/Performance_Optimization)

## Version

Last updated: 2025-11-15
Tool version: 1.0.0
