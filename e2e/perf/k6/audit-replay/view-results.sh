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

#
# View k6 NDJSON results in a human-readable format
#

# Default to latest run if no argument provided
if [ -z "$1" ]; then
  if [ -L "./runs/latest" ]; then
    RESULTS_FILE="./runs/latest/audit-replay-results.json"
  elif [ -f "./results/audit-replay-results.json" ]; then
    RESULTS_FILE="./results/audit-replay-results.json"
  else
    echo "ERROR: No results file found."
    echo "Usage: $0 [results-file.json]"
    echo ""
    echo "Available runs:"
    ls -1d ./runs/*/ 2>/dev/null | head -10 || echo "  No runs found"
    exit 1
  fi
else
  RESULTS_FILE="$1"
fi

if [ ! -f "$RESULTS_FILE" ]; then
  echo "ERROR: Results file not found: $RESULTS_FILE"
  echo "Usage: $0 [results-file.json]"
  echo ""
  echo "Available runs:"
  ls -1d ./runs/*/ 2>/dev/null | head -10 || echo "  No runs found"
  exit 1
fi

echo "=========================================="
echo "K6 Test Results Summary"
echo "=========================================="
echo ""

# Extract HTTP requests
echo "HTTP Requests:"
grep '"type":"Point"' "$RESULTS_FILE" | \
  grep '"metric":"http_reqs"' | \
  jq -r '.data | "  [\(.tags.status)] \(.tags.method) \(.tags.url)"' | \
  head -20

echo ""
echo "Total HTTP Requests:"
grep '"type":"Point"' "$RESULTS_FILE" | \
  grep '"metric":"http_reqs"' | \
  wc -l

echo ""
echo "HTTP Status Codes:"
grep '"type":"Point"' "$RESULTS_FILE" | \
  grep '"metric":"http_reqs"' | \
  jq -r '.data.tags.status' | \
  sort | uniq -c

echo ""
echo "Response Times (http_req_duration):"
grep '"type":"Point"' "$RESULTS_FILE" | \
  grep '"metric":"http_req_duration"' | \
  jq -s '[.[].data.value] | {
    min: (min),
    max: (max),
    avg: (add / length)
  }' | \
  jq -r '"  Min: \(.min)ms\n  Max: \(.max)ms\n  Avg: \(.avg)ms"'

echo ""
echo "Custom Metrics:"
echo ""

echo "  Audit Replay Success Rate:"
grep '"type":"Point"' "$RESULTS_FILE" | \
  grep '"metric":"audit_replay_success"' | \
  jq -s '[.[].data.value] | add / length * 100' | \
  xargs printf "    %.1f%%\n"

echo ""
echo "  Audit Response Time Diff:"
grep '"type":"Point"' "$RESULTS_FILE" | \
  grep '"metric":"audit_response_time_diff"' | \
  jq -s 'if length > 0 then [.[].data.value] | {
    min: (min),
    max: (max),
    avg: (add / length)
  } else {min: 0, max: 0, avg: 0} end' | \
  jq -r '"    Min: \(.min)ms\n    Max: \(.max)ms\n    Avg: \(.avg)ms"' 2>/dev/null || echo "    No data"

echo ""
echo "  Slower vs Faster Requests:"
SLOWER=$(grep '"type":"Point"' "$RESULTS_FILE" | grep '"metric":"audit_slower_requests"' | jq -s '[.[].data.value] | add' 2>/dev/null || echo 0)
FASTER=$(grep '"type":"Point"' "$RESULTS_FILE" | grep '"metric":"audit_faster_requests"' | jq -s '[.[].data.value] | add' 2>/dev/null || echo 0)
echo "    Slower: $SLOWER"
echo "    Faster: $FASTER"

echo ""
echo "=========================================="
echo "Full results: $RESULTS_FILE"
echo "=========================================="
