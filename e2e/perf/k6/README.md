# k6 Load Testing for BPartner API

This folder contains an extensible k6 setup to:
- Replay existing request bodies (e.g., extracted from another system)
- Generate realistic sample data on the fly
- Mix both strategies under configurable load scenarios

## Prerequisites

- Install k6:
  - macOS: `brew install k6`
  - Linux/Windows: see https://k6.io/docs/get-started/installation/

## Structure

- `bpartner/bpartner-load-test.js` — BPartner load test mixing PUT (upsert) and GET
- `framework/http-client.js` — Minimal HTTP client with base URL and auth support
- `framework/data-generators.js` — Sample data generators
- `framework/data-manager.js` — Loads existing payloads and ensures unique identifiers
- `test-data/extracted-bpartner-requests.json` — Example of existing request bodies
- `run.sh` — Convenience runner

## Run

To run it, execute `run.sh` from this folder.
