# k6 Load Testing for metasfresh

This folder contains k6 load testing tools for metasfresh APIs:

## Tools

### 1. BPartner Load Test
An extensible k6 setup to:
- Replay existing request bodies (e.g., extracted from another system)
- Generate realistic sample data on the fly
- Mix both strategies under configurable load scenarios

**Quick start:** `./run.sh`

### 2. API Audit Replay Tool (NEW)
A comprehensive tool that extracts recorded API calls from metasfresh's `API_Request_Audit` database and replays them for load testing.

**Features:**
- Extract real production API requests from the database
- Replay to the same or different metasfresh instance
- Compare original vs replay response times
- Validate API compatibility across environments

**Quick start:** `./run-audit-replay.sh`

**Documentation:** See [AUDIT_REPLAY_README.md](./AUDIT_REPLAY_README.md) for complete usage guide

## Prerequisites

- Install k6:
  - macOS: `brew install k6`
  - Linux/Windows: see https://k6.io/docs/get-started/installation/
- Node.js 18+ (for audit replay tool)

## Structure

### BPartner Load Test
- `bpartner/bpartner-load-test.js` — BPartner load test mixing PUT (upsert) and GET
- `framework/http-client.js` — Minimal HTTP client with base URL and auth support
- `framework/data-generators.js` — Sample data generators
- `framework/data-manager.js` — Loads existing payloads and ensures unique identifiers
- `test-data/extracted-bpartner-requests.json` — Example of existing request bodies
- `run.sh` — Convenience runner

### API Audit Replay Tool
- `extract-api-audit.js` — Extracts API audit data from PostgreSQL
- `replay-api-audit.js` — K6 script for replaying extracted requests
- `run-audit-replay.sh` — Orchestrates extraction and replay
- `audit-replay.env.example` — Configuration template
- `AUDIT_REPLAY_README.md` — Complete documentation
- `API_AUDIT_REPLAY_DESIGN.md` — Design document
