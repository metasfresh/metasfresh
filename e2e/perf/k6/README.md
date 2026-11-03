# k6 Load Testing for metasfresh

This folder contains k6 load testing tools for metasfresh APIs.

## Directory Structure

```
k6/
├── bpartner/              # BPartner-specific load test
│   └── bpartner-load-test.js
├── framework/             # Shared framework (used by all tests)
│   ├── data-generators.js
│   ├── data-manager.js
│   └── http-client.js
├── audit-replay/          # API Audit Replay Tool (complete standalone tool)
│   ├── extract-api-audit.js
│   ├── replay-api-audit.js
│   ├── run-audit-replay.sh
│   ├── view-results.sh
│   ├── audit-replay.env.example
│   ├── README.md          # Complete usage guide
│   ├── DESIGN.md          # Technical design
│   └── SCALING_GUIDE.md   # Performance limits
├── test-data/             # Shared test data
├── results/               # Shared results output
├── run.sh                 # BPartner test runner
└── package.json           # Node.js dependencies (for audit-replay)
```

## Tools

### 1. BPartner Load Test

**Location:** `./bpartner/` (subdirectory)

**Purpose:** Simple load test for BPartner API endpoints using generated data

**Features:**
- Generates synthetic BPartner data (upsert + get)
- Flexible execution modes (VUs + duration, iterations, or ramping)
- Custom metrics for PUT and GET operations

**Quick start:**
```bash
./run.sh
```

**Use when:** You want to quickly load-test BPartner endpoints with synthetic data.

---

### 2. API Audit Replay Tool

**Location:** `./audit-replay/` (subdirectory)

**Purpose:** Extract and replay ANY API requests from metasfresh's audit database

**Features:**
- Extract real production API requests from `API_Request_Audit` table
- Replay to the same or different metasfresh instance
- Filter by time range, ID range, endpoint, or status
- Compare original vs replay response times
- Exact replay (run each request once) or load testing (continuous loop)

**Quick start:**
```bash
cd audit-replay
./run-audit-replay.sh
```

**Documentation:** See [audit-replay/README.md](./audit-replay/README.md)

**Use when:**
- You want to replay real production traffic
- You need to test any API endpoint (not just BPartner)
- You want to compare performance across environments
- You need to debug specific request sequences

## Prerequisites

- **k6**: Load testing framework
  - macOS: `brew install k6`
  - Linux/Windows: https://k6.io/docs/get-started/installation/
- **Node.js 18+**: For audit replay tool (database extraction)
  - Install dependencies: `npm install`

## Which Tool Should I Use?

| Scenario | Tool |
|----------|------|
| Quick BPartner load test with synthetic data | BPartner Load Test (`./run.sh`) |
| Replay real production traffic | **API Audit Replay** (`cd audit-replay`) |
| Test any API endpoint (not just BPartner) | **API Audit Replay** |
| Compare environments | **API Audit Replay** |
| Debug specific request sequences | **API Audit Replay** |
| Replay by ID range or time range | **API Audit Replay** |

## Getting Started

### BPartner Load Test
```bash
# Just run it
./run.sh
```

### API Audit Replay Tool
```bash
# Go to the audit-replay directory
cd audit-replay

# Read the README
cat README.md

# Configure (copy and edit the example)
cp audit-replay.env.example audit-replay.env
nano audit-replay.env

# Run
./run-audit-replay.sh
```
